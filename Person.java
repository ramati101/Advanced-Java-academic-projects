package q1;

public class Person implements Comparable<Person> {

	private int id;
	private String fName;
	private String lName;
	private int birthYear;
	
	public Person(int PId, String PFName, String PLName, int PBirthYear) {
		id = PId;
		fName = PFName;
		lName = PLName;
		birthYear = PBirthYear;
	}
	
	@Override
	public int compareTo(Person p2) {
		if( lName.compareTo(p2.lName) > 0 )
			return 1;
		if( lName.compareTo(p2.lName) < 0 )
			return -1;
		if( fName.compareTo(p2.fName) > 0 )
			return 1;
		if( fName.compareTo(p2.fName) < 0 )
			return -1;
		
		return 0;
	}
	
	public String toString() {
		return id + " " + fName + " " + lName + " " + birthYear;
	}
	
	
}
