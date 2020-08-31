package q1;

public class Element {
	
	private double coefficient;
	private int power;
	
	public Element(double co, int po) {
		coefficient = co;
		power = po;
	}
	
	public double getCo() {
		return coefficient;
	}
	
	public int getPo() {
		return power;
	}
	
	public void setCo(double co) {
		coefficient = co;
	}
	
	public void setPo(int po) {
		power = po;
	}
	
	public String toString() {		
		//return "coefficient: " + String.valueOf(coefficient) + " power: " + String.valueOf(power);
		String x,po,power1,co;

		
		co = ( coefficient > 0 ) ?  "+" + String.valueOf(coefficient) : String.valueOf(coefficient) ;
		x = ( power == 0 ) ? "" : "x";
		po = ( power > 1 ) ? "^" : "";
		power1 = ( power > 1 ) ? String.valueOf(power) : "";
		
		return co + x + po + power1 ;
	}
	
	public boolean equals(Element element2) {
		return (this.coefficient == element2.coefficient && this.power == element2.power) ? true : false ;
	}
	

}
