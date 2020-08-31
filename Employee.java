// Fig. 10.4: Employee.java
// Employee abstract superclass.
package q2;

public abstract class Employee 
{
   private String firstName;
   private String lastName;
   private String socialSecurityNumber;
   private BirthDate birthDate;

   // four-argument constructor
   public Employee( String first, String last, String ssn, int d, int m, int y )
   {
      firstName = first;
      lastName = last;
      socialSecurityNumber = ssn;
      birthDate = new BirthDate(d,m,y);
      
   } 

   public void setFirstName( String first )
   {
      firstName = first; // should validate
   } 

   public String getFirstName()
   {
      return firstName;
   }

   public void setLastName( String last )
   {
      lastName = last; // should validate
   } 

   public String getLastName()
   {
      return lastName;
   } 
 
   public void setSocialSecurityNumber( String ssn )
   {
      socialSecurityNumber = ssn; 
   } 

   public String getSocialSecurityNumber()
   {
      return socialSecurityNumber;
   } 
   
   public int getBirthDay() {
	   return birthDate.getDay();
   }
   
   public int getBirthMonth() {
	   return birthDate.getMonth();
   }
   
   public int getBirthYear() {
	   return birthDate.getYear();
   }

   // return String representation of Employee object
   @Override
   public String toString()
   {
      return String.format( "%s %s\nsocial security number: %s \ndate of birth: %s", 
         getFirstName(), getLastName(), getSocialSecurityNumber(), birthDate.toString() );
   } 

   // abstract method overridden by concrete subclasses
   public abstract double earnings(); 
} 

