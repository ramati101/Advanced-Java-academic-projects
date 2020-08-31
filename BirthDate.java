package q2;

public class BirthDate {
	
	private int day;
	private int month;
	private int year;
	
	public BirthDate(int d, int m, int y) {
		day = d;
		month = m;
		year = y;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	public String toString() {
		return day + "." + month + "." + year;
	}

}
