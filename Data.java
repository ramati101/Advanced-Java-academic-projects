package q1;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

// the game object
public class Data implements Serializable {
	
	private int[][] gameMat; // matrix that represents the game board
	private int size; // the number of row and column of the game matrix
	private int turn; // which player turn is it
	private int card1Row; // represents the first card row number in matrix
	private int card1Col; // represents the first card column number in matrix
	private int card2Row; // represents the second card row number in matrix
	private int card2Col; // represents the second card column number in matrix
	private boolean beginning; // if its the beginning of the game
	private boolean give3SecPeek; // if to give 3 seconds peek on the cards
	private boolean endGame; // if the game ends
	private int winner; // who win, player1 - 1, player2 - 2, tie - 3
	private int player1Score;
	private int player2Score;
	
	public Data(int n) {
		turn = 1;
		beginning = true;
		endGame = false;
		give3SecPeek = false;
		size = n;
		gameMat = new int[size][size];
		
		fillMat();
	}
	
	// fill the game matrix with pair of cards
	private void fillMat() {
		Random rand = new Random();
		ArrayList<Integer> a = new ArrayList<Integer>(size*size);
		for(int i=0; i<(size*size)/2; i++) {  // fill array list with pairs of numbers
			a.add(i);
			a.add(i);
		}
		for(int row=0; row<size; row++)  // fill the game mat cards from random indexs in the array list
			for(int col=0; col<size; col++)
				gameMat[row][col] = a.remove(rand.nextInt(a.size()));
	}
	
	public int getCard(int row, int col) {
		return gameMat[row][col];
	}
	
	public int getSize() {
		return size;
	}
	
	public int isTurn() {
		return turn;
	}
	
	public void setTurn(int t) {
		turn = t;
	}
	
	public void switchTurn() {
		turn = (turn==1)? 2 : 1;
	}
	
	public void setBeginning(boolean t) {
		beginning = t;
	}
	
	public boolean isBeginning() {
		return beginning;
	}
	
	public void cardClicked(int numOfCard, int row, int col) {
		if(numOfCard==0) {
			card1Row = row;
			card1Col = col;
		}
		else {
			card2Row = row;
			card2Col = col;
		}
			
	}
	
	public int getClickCardRow(int whichCard) {
		if(whichCard==1) 
			return card1Row;
		return card2Row;
	}
	
	public int getClickCardCol(int whichCard) {
		if(whichCard==1) 
			return card1Col;
		return card2Col;
	}
	
	public boolean clickedCardEquals() {
		if( gameMat[card1Row][card1Col] == gameMat[card2Row][card2Col] )
			return true;
		return false;
	}
	
	public boolean toPeek() {
		return give3SecPeek;
	}
	
	public void setPeek(boolean b) {
		give3SecPeek = b;
	}
	
	public boolean isEnd() {
		return endGame;
	}
	
	public void setEnd(boolean b) {
		endGame = b;
	}
	
	public void setWinner(int w) {
		winner = w;
	}
	
	public int isWinner() {
		return winner;
	}
	
	public int getPlayerScore(int playerNum) {
		return (playerNum == 1)? player1Score:player2Score;
	}
	
	public void setPlayerScore(int playerNum, int score) {
		if(playerNum==1)
			player1Score = score;
		else
			player2Score = score;
	}
}
