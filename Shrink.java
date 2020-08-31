package q1;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Shrink extends JPanel {
	private final int startXMat = 10; // the x start of the matrix
	private final int startYMat = 15; // the y start of the matrix
	private final int frameWidth = 500; // size of matrix
	private final int frameHeight = 500; // size of matrix
	private int size, numberOfTransitions, numberOfThreads;
	private int boxWidth, boxHeight; // sizes of every cell in matrix
	private boolean[][] mat, copy; 
	private JButton go, clear;
	private boolean matLock; // if true the matrix is lock for edit(after click go)
	
	
	// Shtink constructor
	public Shrink(int n, int m, int t) {
		setLayout(null);
		
		matLock = false; // at the beginning the matrix isnt lock
		size = n; // the matrix size that the user decide
		numberOfThreads = m;
		numberOfTransitions = t;
		boxWidth = (frameWidth-25)/size; // size of every cell in matrix
		boxHeight = (frameHeight-25)/size; // size of every cell in matrix
		mat = new boolean[n][n];
		copy = new boolean[n][n];
		handleButtons();
		
		this.addMouseListener(new Listener());
	}
	
	private void handleButtons() {
		go = new JButton("go");
		clear = new JButton("clear");
		go.setBounds(120, 500, 100, 40);
		clear.setBounds(270, 500, 100, 40);
		add(go);
		add(clear);
		
		go.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				matLock = true; // lock the matrix for edit
				startShrink(); // start the shrinking in the matrix
			}
		});
		
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				matLock = false; // unlock the matrix for edit
				for (int i=0; i<mat.length; i++)  // at the beginnig all the matrix cells are white.
			        for (int j=0; j<mat[i].length; j++)
			        		mat[i][j] = false;       			
				repaint();
			}
		});
	}
	
	// the method shrink by one pixel for the number of Transitions the user decide, and sleep between Transitions.
	private void startShrink() {
		for( int i=0; i<numberOfTransitions; i++ ) {
			shrinkTransition();
			try {
				TimeUnit.MILLISECONDS.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// method make one Transition of shrinking
	private void shrinkTransition() {
		ExecutorService e=Executors.newFixedThreadPool(numberOfThreads); // manage the threads
		copyMat(copy, mat); // copy the original matrix to the work matrix.
		int numberOfRows=0; // number of row to send the thread to work on.
		int startRow=0; // the index of the first row to send the thread to work on.
		int threadLeft = numberOfThreads; // how many threads left to run.
		
		// if the dividing left remainder.
		if( size % threadLeft > 0 ) {
			numberOfRows = (size % numberOfThreads) + 1; // send the first thread the remainder + 1 rows.
			e.execute(new ShrinkThread(this ,size ,startRow ,numberOfRows));
			startRow += numberOfRows; // now the first row of the next thread will be the last of this thread.
			threadLeft--; 
		}
		numberOfRows = (size - startRow)/threadLeft; // now there is no remainder so we split equally the rows.
		
		for( int i=0 ; i<threadLeft; i++) {
			e.execute(new ShrinkThread(this,size,startRow,numberOfRows));
			startRow += numberOfRows;
		}
			
		e.shutdown();

		try {
			e.awaitTermination(100, TimeUnit.MILLISECONDS );
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		copyMat(mat,copy);
		repaint();

		
	}
	
	private void copyMat(boolean[][] mat1, boolean[][] mat2) {
		for (int i=0; i<mat1.length; i++) 
			for (int j=0; j<mat1[i].length; j++) 
				mat1[i][j] = mat2[i][j];
	}
	
	// the method paint white rectangle if the match cell in the matrix is false, and black rectangle if the match cell in the matrix is true.
	public void paintComponent(Graphics g) {
		super.paintComponent(g);  

	    for (int i=0; i<mat.length; i++) 
	        for (int j=0; j<mat[i].length; j++) {
	        	if( mat[i][j] )
	        		g.fillRect(startXMat+boxWidth*j, startYMat+boxHeight*i, boxWidth, boxHeight);
	        	else
	        		g.drawRect(startXMat+boxWidth*j, startYMat+boxHeight*i, boxWidth, boxHeight);
	        }
	     
    }
	
	// recognize by the x and y of the mouse click which cell in the matrix the user click on.
	private class Listener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if( !matLock ) { // only if the matrix isnt lock
				if( e.getX()< 10 && e.getX()> frameWidth-15 && e.getY() < 15 && e.getY()> frameHeight-10 );  // click outside the matrix.
		  
				else { // click inside the matrix.
					for (int i=0; i<mat.length; i++) 
						for (int j=0; j<mat[i].length; j++) {
							if( e.getY() > startYMat+boxHeight*i && e.getY() < startYMat+boxHeight+boxHeight*i && 
									e.getX() > startXMat+boxWidth*j && e.getX() < startXMat+boxWidth+boxWidth*j )
								mat[i][j] = (mat[i][j]) ? false : true;	   // if the cell is white, turn it black. if the cell is black, turn it white.    		
						}	
					repaint();
				}
			}
		}
	}
	
	public boolean getMatCell(int i, int j) {
		return mat[i][j];
	}
	public boolean getCopyCell(int i, int j) {
		return copy[i][j];
	}
	
	public void setMatCell(int i, int j, boolean v) {
		mat[i][j] = v;
	}
	public void setCopyCell(int i, int j, boolean v) {
		copy[i][j] = v;
	}
}
