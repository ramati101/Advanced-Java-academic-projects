package q2;

public class HourlyEmployee extends Employee 
{
   private double wage; 
   private double hours; 

   public HourlyEmployee( String first, String last, String ssn, int d, int m, int y, 
      double hourlyWage, double hoursWorked )
   {
      super( first, last, ssn, d, m, y );
      setWage( hourlyWage ); // validate and store hourly wage
      setHours( hoursWorked ); // validate and store hours worked
   } 

   public void setWage( double hourlyWage )
   {
      if ( hourlyWage >= 0.0 )
         wage = hourlyWage;
      else
         throw new IllegalArgumentException( 
            "Hourly wage must be >= 0.0" );
   } 

   public double getWage()
   {
      return wage;
   } 

   public void setHours( double hoursWorked )
   {
      if ( ( hoursWorked >= 0.0 ) && ( hoursWorked <= 168.0 ) )
         hours = hoursWorked;
      else
         throw new IllegalArgumentException( 
            "Hours worked must be >= 0.0 and <= 168.0" );
   } 

   public double getHours()
   {
      return hours;
   }
   
   // calculate earnings; override abstract method earnings in Employee
   @Override
   public double earnings()
   {
      if ( getHours() <= 40 ) // no overtime
         return getWage() * getHours();
      else
         return 40 * getWage() + ( getHours() - 40 ) * getWage() * 1.5;
   } 

   // return String representation of HourlyEmployee object
   @Override
   public String toString()
   {
      return String.format( "hourly employee: %s\n%s: $%,.2f; %s: %,.2f", 
         super.toString(), "hourly wage", getWage(), 
         "hours worked", getHours() );
   } 
} 

