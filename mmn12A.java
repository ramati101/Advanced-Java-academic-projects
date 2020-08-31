package q1;
import java.util.Scanner;

public class mmn12A {
	public static void main(String[] args) {
		Polynom poly = new Polynom();
		Polynom poly2 = new Polynom();
		
		double co;
		int po;
		
		Scanner scan = new Scanner(System.in);
		
		int getPolynom1 = 0;
		int getPolynom2 = 0;
		
		System.out.println(" lets start with the first polynom, enter the elements one by one ");
		
		while( getPolynom2 == 0 ) {
			System.out.println(" please enter the coefficient and his power with space between then. if you done, just type \"0\" ");
			
			if( getPolynom1 == 0 ) {
				co = scan.nextDouble();
				if( co == 0 ) {
					getPolynom1 = 1;
					System.out.println(" ok! done with the first polynom, now lets start with the second polynom. ");
				}
				else {
					po = scan.nextInt();
					poly.add(co, po);
				}
			}
			else {
				co = scan.nextDouble();
				if( co == 0 )
					getPolynom2 = 1;
				else {
					po = scan.nextInt();
					poly2.add(co, po);
				}
			}
		}
		poly.sort();
		poly2.sort();
		
		System.out.println(poly);
		System.out.println(poly2);
	}
}
