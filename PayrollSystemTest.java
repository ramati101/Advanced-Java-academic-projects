package q2;
import java.util.Calendar;

public class PayrollSystemTest 
{
   public static void main( String[] args ) 
   {
	   Calendar now = Calendar.getInstance();
	   
      SalariedEmployee salariedEmployee = 
         new SalariedEmployee( "John", "Smith", "111-11-1111", 8, 2, 1990, 800.00 );
      HourlyEmployee hourlyEmployee = 
         new HourlyEmployee( "Karen", "Price", "222-22-2222", 12, 12, 1992, 16.75, 40 );
      CommissionEmployee commissionEmployee = 
         new CommissionEmployee( 
         "Sue", "Jones", "333-33-3333", 29, 5, 1979, 10000, .06 );
      BasePlusCommissionEmployee basePlusCommissionEmployee = 
         new BasePlusCommissionEmployee( 
         "Bob", "Lewis", "444-44-4444", 23, 6, 1981, 5000, .04, 300 );
      PieceWorker pieceWorker = 
    	 new PieceWorker( "Moshe", "Cohen", "555-55-5555", 15, 3, 1954, 15, 4 );

      Employee[] employees = new Employee[ 5 ]; 

      employees[ 0 ] = salariedEmployee;
      employees[ 1 ] = hourlyEmployee;
      employees[ 2 ] = commissionEmployee; 
      employees[ 3 ] = basePlusCommissionEmployee;
      employees[ 4 ] = pieceWorker;

      System.out.println( "Employees processed polymorphically:\n" );
      
      // generically process each element in array employees
      for ( Employee currentEmployee : employees ) 
      {
         System.out.println( currentEmployee ); // invokes toString

         if ( currentEmployee.getBirthMonth() == (now.get(Calendar.MONTH) + 1) ) 
        	 System.out.printf("earned $%,.2f\n\n", currentEmployee.earnings() + 200 );
         else
        	 System.out.printf("earned $%,.2f\n\n", currentEmployee.earnings() );
      } 
   } 
}


