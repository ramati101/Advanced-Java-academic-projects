package q1;

import java.util.Iterator;

public class MinSet {

	// generic method, gets Set of comarable items and return the minimum item.
	public static <E extends Comparable<E>> E Min(Set<E> set) {
		Iterator<E> it = set.iterator();
		E min,temp;
		
		min = it.next();
		while( it.hasNext() ) {
			temp = it.next();
			if( min.compareTo(temp) == 1 )
				min = temp;
		}
		
		return min;
	}
}
