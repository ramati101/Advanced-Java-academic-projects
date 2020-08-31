package q2;

public class SalariedEmployee extends Employee 
{
   private double weeklySalary;

   public SalariedEmployee( String first, String last, String ssn, int d, int m, int y, 
      double salary )
   {
      super( first, last, ssn, d, m, y ); 
      setWeeklySalary( salary ); 
   } 

   public void setWeeklySalary( double salary )
   {
      if ( salary >= 0.0 )
         weeklySalary = salary;
      else
         throw new IllegalArgumentException( 
            "Weekly salary must be >= 0.0" );
   }

   public double getWeeklySalary()
   {
      return weeklySalary;
   } 

   // calculate earnings; override abstract method earnings in Employee
   @Override
   public double earnings()
   {
      return getWeeklySalary();
   } 

   // return String representation of SalariedEmployee object
   @Override
   public String toString()
   {
      return String.format( "salaried employee: %s\n%s: $%,.2f", 
         super.toString(), "weekly salary", getWeeklySalary() );
   } 
} 
