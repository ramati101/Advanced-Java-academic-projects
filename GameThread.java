package q1;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class GameThread extends Thread {
	
	private Socket player1 = null;
	private Socket player2 = null;
	private int matSize;
	private Data data;
	private int player1Score,player2Score;
	private int turn;

	public GameThread(Socket socketPlayer1, Socket socketPlayer2, int size) {
		player1 = socketPlayer1;
		player2 = socketPlayer2;
		turn = 1;
		matSize = size;
		data = new Data(matSize);
		player1Score = 0;
		player2Score = 0;
	}
	
	public void run() {
		super.run();
		try {
			handleGame();
		}
		catch(Exception e) {
			System.out.println("could not run handlegame");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void handleGame() throws Exception {
		
		// all the streaming connections
		OutputStream outputStream1 = player1.getOutputStream();
		ObjectOutputStream objOutputStream1 = new ObjectOutputStream(outputStream1);
		InputStream inputStream1 = player1.getInputStream();
		ObjectInputStream objInputStream1 = new ObjectInputStream(inputStream1);
		OutputStream outputStream2 = player2.getOutputStream();
		ObjectOutputStream objOutputStream2 = new ObjectOutputStream(outputStream2);
		InputStream inputStream2 = player2.getInputStream();
		ObjectInputStream objInputStream2 = new ObjectInputStream(inputStream2);
		
		// first data objects the server thread receive from player
		Data connectionRequest = null;
		connectionRequest = (Data)objInputStream1.readObject();
		connectionRequest = (Data)objInputStream2.readObject();
		int tempInt;
		
		// send to players the data object with the game matrix to start playing, and manage the turns.
		data.setTurn(1);
		objOutputStream1.writeObject(data);
		data.setTurn(2);
		objOutputStream2.writeObject(data);
		data.setTurn(1);
		
		// the game loop, ends when the game is end
		while(!data.isEnd()) {
			// reads from user that finish his turn
			if(turn == 1) 
				data = (Data)objInputStream1.readObject();
			else 
				data = (Data)objInputStream2.readObject();
			
			// if the chosen pair cards is equals
			if(data.clickedCardEquals()) {
				tempInt =(turn==1)? player1Score++ : player2Score++; // give point to the player who played
				
				if(player1Score+player2Score >= ((matSize*matSize)/2)) { // if the players sum points is equal or bigger than the number of card pairs
					data.setEnd(true); // the game end
					int winner = (player1Score==player2Score) ? 3 :((player1Score>player2Score)? 1:2); // which player win
					data.setWinner(winner); // set winner in the data object
					data.setPlayerScore(1, player1Score);
					data.setPlayerScore(2, player2Score);
				}
			}
			
			// if the chosen card pair isnt equal
			else
				data.setPeek(true);  // just give a 3 seconds peek
			
			// switch turns for local var and for object turn var.
			turn = switchTurns(turn);
			data.switchTurn();
			
			// send object to players
			objOutputStream1.writeObject(data);  
			objOutputStream2.writeObject(data);
		}
	}
	
	private static int switchTurns(int t) {
		return (t==1)? 2 : 1;
	}

}
