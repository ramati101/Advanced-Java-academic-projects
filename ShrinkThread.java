package q1;

public class ShrinkThread extends Thread{
	
	private Shrink _shrink; 
	private int _size;
	private int _firstRow;
	private int _numOfRows;
	
	// constructor for thread that shrink.
	public ShrinkThread(Shrink s, int size, int firstRow, int numOfRows ) {
		_shrink = s;
		_size = size;
		_firstRow = firstRow;
		_numOfRows = numOfRows;
	}
	
	// check for the cells in the matrix if one of the cells around him is white, so make the cell himself white.
	public void run() {
		super.run();
		for( int i = _firstRow ; i < _firstRow + _numOfRows && i < _size ; i++ )  // run on the row from the first one and the number of rows after.
			for( int j = 0 ; j < _size ; j++ ) { 
				if(i>0) // if there is cell from the left
					if( !_shrink.getMatCell(i-1,j) )  
						_shrink.setCopyCell(i, j, false);
				if(j>0) // if there is cell above
					if( !_shrink.getMatCell(i,j-1) )
						_shrink.setCopyCell(i, j, false);
				if(i<_size-1) // if there is cell below
					if( !_shrink.getMatCell(i+1,j) )
						_shrink.setCopyCell(i, j, false);
				if(j<_size-1) // if there is cell from the right
					if( !_shrink.getMatCell(i,j+1) )
						_shrink.setCopyCell(i, j, false);
			}
		
	}
}
