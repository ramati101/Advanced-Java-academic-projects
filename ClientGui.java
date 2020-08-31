package q1;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGui extends JFrame {
	
	private int matSize; // game matrix size
	private static Data data; // the data game object
	private static int numOfClicks; // counts how many clicks on cards the user make
	private static int turnNum; // represents if the player turn is first or second
	private static JButton[][] btnMat; // the buttons matrix that represents game cards.

	public ClientGui(int size) {
		numOfClicks = 0;
		matSize = size;
		btnMat = new JButton[size][size];
		
		handleGui(size);
	}
	
	public void handleGui(int size) {
		setLayout(new GridLayout(size,size));
		handleMat();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,400);
		setVisible(true);
		if(data.isBeginning()) {
			if(data.isTurn()==1) {
				JOptionPane.showMessageDialog(this,"you play first!");	
				turnNum = 1;
			}
			else {
				JOptionPane.showMessageDialog(this,"you play second!");
				turnNum = 2;
			}
		}
	}
	
	private void handleMat() {
		
		// ActionListener the save the card(button) that clicked and count the click
		ActionListener listener = new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            if (e.getSource() instanceof JButton) {
	            	JButton btn = (JButton)e.getSource();
	            	for(int i=0; i<matSize; i++)
	            		for(int j=0; j<matSize; j++) {
	            			if(btn.equals(btnMat[i][j])){
	            				data.cardClicked(numOfClicks++, i, j);
	            			}
	            		}  
	            }
	        }
	    };
	    
	    // fill the button matrix with buttons and connect ActionListener for every button
		for(int i=0; i<matSize;i++)
			for(int j=0;j<matSize;j++) {
				add(btnMat[i][j] = new JButton());
				btnMat[i][j].addActionListener(listener);
			}
	}
	
	public static void main(String[] args) {
		
		// gets the host computer name and the port which the server listen.
		String host;
		int port;
		Scanner sc = new Scanner(System.in);
		System.out.println("please enter computer name:");
		host = sc.next();
		System.out.println("please enter port number:");
		port = sc.nextInt();
		
		// all the streaming connections through socket
		Socket s = null;
		OutputStream outputStream = null;
		ObjectOutputStream objOutputStream = null;
		InputStream inputStream = null;
		ObjectInputStream objInputStream = null;
		try {
			s = new Socket(host,port);
			outputStream = s.getOutputStream();
			objOutputStream = new ObjectOutputStream(outputStream);
			inputStream = s.getInputStream();
			objInputStream = new ObjectInputStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		// player send first data object to server to connect
		int ds = 6;
		Data connectionRequest = new Data(ds);
		sendToServer(objOutputStream,connectionRequest);
		
		// player gets the data object with the data of the game from server
		data = readFromServer(objInputStream);
		
		// creates the frame to start playing
		ClientGui frame = new ClientGui(data.getSize());
	
		
	    // the player game loop, ends when the game is end
		while(!data.isEnd()) {
			if(!data.isBeginning()) { // if its the Beginning of the game - no cards to show, else - show cards that chosen
				showCard(data.getClickCardRow(1),data.getClickCardCol(1));
				showCard(data.getClickCardRow(2),data.getClickCardCol(2));
			}
			if(data.toPeek()) {  // if the cards arent equals, just peek for 3 seconds and then hide the cards
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				hideCard(data.getClickCardRow(1),data.getClickCardCol(1));
				hideCard(data.getClickCardRow(2),data.getClickCardCol(2));	
				data.setPeek(false);
			}
			
			// if it is the beginning of the game and its turn of player1(play first) or its not the Beginning and its my turn - play.
			if( data.isBeginning() && data.isTurn()==1 || !data.isBeginning() && data.isTurn()== turnNum ) {
				data.setBeginning(false); // after player1 plays first its no more the Beginning of the game.
				numOfClicks = 0;
				while(numOfClicks<2); // wait for the player to choose two cards
				sendToServer(objOutputStream,data); // send to server the cards i choose
				numOfClicks = 0;
			}	
			data = readFromServer(objInputStream);	// read from server 
		}
		
		// after the game end
		showCard(data.getClickCardRow(1),data.getClickCardCol(1)); // show the last pair of cards
		showCard(data.getClickCardRow(2),data.getClickCardCol(2)); // show the last pair of cards
		String winnerNote = (data.isWinner()==3)? "the score is a tie" :((data.isWinner()==turnNum)? "you are the Winner!" : "you lose!");
		int n = JOptionPane.showConfirmDialog(frame, "player1 score: "+Integer.toString(data.getPlayerScore(1))+
											"\nplayer2 score: "+Integer.toString(data.getPlayerScore(2))+
											"\n\nAnother game?",
											winnerNote,
											JOptionPane.YES_NO_OPTION);
		
		// if the player want another game
		if( JOptionPane.YES_OPTION == n )
			main(new String[] {"a","b","c"});
		
		try {
			outputStream.close();
			objOutputStream.close();
			inputStream.close();
			objInputStream.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	// sent data object to server 
	private static void sendToServer(ObjectOutputStream server, Data d) {
		try {
			server.writeObject(d);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// read data object from server
	private static Data readFromServer(ObjectInputStream server) {
		Data dataRecived = null;
		try {
			dataRecived = (Data)server.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataRecived;
	}
	
	private static void showCard(int row, int col) {
		btnMat[row][col].setText(Integer.toString(data.getCard(row, col)));		
	}
	
	private static void hideCard(int row, int col) {
		btnMat[row][col].setText("");
	}	
}
