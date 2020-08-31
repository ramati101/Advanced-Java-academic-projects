package q1;
import java.util.ArrayList;
import java.util.Comparator;

public class Polynom {

	private ArrayList<Element> polynom;
	
	public Polynom(double[] coArr, int[] poArr) throws IllegalArgumentException{
		if(coArr.length != poArr.length)
			throw new IllegalArgumentException();
		
		polynom = new ArrayList<Element>();
		
		for(int i=0; i < coArr.length; i++)
			polynom.add(new Element(coArr[i], poArr[i]));
		this.sort();	
		
	}
	
	public Polynom() {
		polynom = new ArrayList<Element>();
	}
	
	public void add(double co, int po) {
		this.polynom.add(new Element(co,po));
	}
	
	public void sort() {
		polynom.sort(Comparator.comparing(Element::getPo).reversed());
	}
	
	public Polynom plus(Polynom polynom2) {
		return this.calc(polynom2, 1);
	}
	
	public Polynom minus(Polynom polynom2) {
		return this.calc(polynom2, -1);
	}
	
	
	private Polynom calc(Polynom polynom2, int sign) {
		Polynom result = new Polynom();
		
		int i1 = 0;
		int i2 = 0;
		
		while( i1 < this.polynom.size() || i2 < polynom2.polynom.size() ) {
			if( i2 >= polynom2.polynom.size() || (this.getPo(i1) > polynom2.getPo(i2))) {
				result.polynom.add(new Element(this.getCo(i1),this.getPo(i1)));
				i1++;
			}
			else if( i1 >= this.polynom.size() || (this.getPo(i1) < polynom2.getPo(i2))) {
				if(sign == 1)
					result.polynom.add(new Element(polynom2.getCo(i2),polynom2.getPo(i2)));
				else
					result.polynom.add(new Element(-polynom2.getCo(i2),polynom2.getPo(i2)));
				i2++;
			}
			else if(this.getPo(i1) == polynom2.getPo(i2)) {
				if(sign == 1)
					result.polynom.add(new Element((this.getCo(i1)+polynom2.getCo(i2)),this.getPo(i1)));
				else
					result.polynom.add(new Element((this.getCo(i1)-polynom2.getCo(i2)),this.getPo(i1)));
				i1++;
				i2++;
			}		
		}
		return result;
	}
	
	public Polynom diff() {
		Polynom result = new Polynom();
		
		for( int i = 0; i < this.polynom.size(); i++) {
			if( this.getPo(i) == 1 )
				result.polynom.add(new Element(this.getCo(i),0));
			else if( this.getPo(i) > 1 )
				result.polynom.add(new Element((this.getCo(i)*this.getPo(i)),this.getPo(i)-1));
		}
		
		return result;
	}
	
	public String toString() {
		String str = "";
		
		for( int i = 0; i < this.polynom.size(); i++ )
			str = str.concat(polynom.get(i).toString());
		
		return str;
	}
	
	public boolean equals(Polynom polynom2) {
		if( this.polynom.size() != polynom2.polynom.size() )
			return false;
		
		for(int i=0; i < polynom.size(); i++) {
			if( !(this.polynom.get(i).equals(polynom2.polynom.get(i))) )
				return false;
		}
		return true;
	}
	
	private double getCo(int ind) {
		return this.polynom.get(ind).getCo();
	}
	
	private int getPo(int ind) {
		return this.polynom.get(ind).getPo();
	}
	
	
}
