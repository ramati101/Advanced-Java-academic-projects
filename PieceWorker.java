package q2;

public class PieceWorker extends Employee {
	
	private double wagePerPiece;
	private double pieces;

	public PieceWorker(String first, String last, String ssn, int d, int m, int y, 
						double WagePerPiece, double Pieces ){
		super( first, last, ssn, d, m, y);
		wagePerPiece = WagePerPiece;
		pieces = Pieces;
		
	}
	
	public double earnings() {
		return wagePerPiece * pieces;
	}
	
	public String toString()
	   {
	      return String.format( "piece worker: %s\n%s: $%,.2f; %s: %,.2f", 
	         super.toString(), "wage per piece", wagePerPiece, 
	         "pieces produced", pieces );
	   } 
	
}
