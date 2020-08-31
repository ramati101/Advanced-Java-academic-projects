package q2;

public class MergeThread extends Thread {
	
	private ArrayStock stock;
	
	public MergeThread(ArrayStock s) {
		stock = s;
	}

	public void run() {
		while( stock.size() > 1 ) { // while the arraylist stock isnt empty
			int a[] = stock.pop();  // pop one int array
			int b[] = stock.pop();  // pop another int array
			int c[] = new int[a.length+b.length]; // create int array in the size of both of them
			
			int aI=0,bI=0,cI=0;     // create indexes for the arrays, start at 0
			while(aI<a.length || bI<b.length) {  // while both indexes didnt get to the end of the array, continue
				if(aI>=a.length)                // if the index of a get to the end
					c[cI++] = b[bI++];          // so add the element from b
				else if(bI>=b.length)          // if the index of b get to the end
						c[cI++] = a[aI++];     // so add the element from a
					else if( a[aI] > b[bI] )   // if the a element bigger the the b element 
							c[cI++] = b[bI++];  // add the element from b
						else
							c[cI++] = a[aI++];
			}
			
			stock.add(c);
		}
	}
}
