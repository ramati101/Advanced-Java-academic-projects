package q1;

public class PersonTest {

	public static void main(String[] args) {
		Person[] pArray = new Person[5];
		pArray[0] = new Person(111, "Matan", "Ramati", 1234);
		pArray[1] = new Person(222, "Moshe", "Cohen", 5678);
		pArray[2] = new Person(333, "Rami", "Levi", 9101112);
		pArray[3] = new Person(444, "Yosi", "Adam", 13141516);
		pArray[4] = new Person(555, "Ofer", "Davidi", 17181920);
		Set<Person> pSet = new Set<Person>(pArray);
		System.out.println("The Set of Persons: "+pSet);
		
		System.out.println("The Minimum Person in the Set is: " + MinSet.Min(pSet));
		

	}

}
