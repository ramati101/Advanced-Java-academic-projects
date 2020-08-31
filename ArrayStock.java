package q2;

import java.util.ArrayList;

public class ArrayStock {
	
	// arraylist of int arrays.
	private ArrayList<int[]> stock; 

	// constructor build arraylist of int arrays from int array.
	public ArrayStock(int[] arr) {
		stock = new ArrayList<int[]>(arr.length);
		
		for(int i=0 ; i<arr.length ; i++) 
			stock.add(new int[]{arr[i]});
		
	}
	
	// pop element from the top of the arraylist
	public synchronized int[] pop() {
		while( this.size()==0 ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return stock.remove(0);	
	}
	
	// add int array to the end of the arraylist
	public synchronized void add(int[] a) {
		stock.add(a);
		notifyAll();
	}
	
	public synchronized int size() {
		return stock.size();
	}
	
	public synchronized String toString() {
		String str = "";
		
		if(stock.isEmpty()) {
			try {
				wait(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int arr[] = stock.get(0);
		for( int i = 0; i < arr.length; i++ )
			str = str.concat(arr[i] + ", ");
		
		return str;
	}
}
