package q1;
import java.util.*;


public class test {

	public static void main(String[] args) {
		
		Random rand = new Random(); 
		Scanner sc = new Scanner(System.in);
		
		Integer[] intArr = new Integer[10];
		Integer[] intArr2 = new Integer[10];
		Integer[] intArr3 = new Integer[10];
		Integer[] userInts = new Integer[2];
		
		for(int i=0;i<10;i++)
			intArr[i] = rand.nextInt(101);
		for(int i=0;i<10;i++)
			intArr2[i] = rand.nextInt(101);
		for(int i=0;i<10;i++)
			intArr3[i] = rand.nextInt(101);
		
		Set<Integer> s1 = new Set<Integer>(intArr);
		Set<Integer> s2 = new Set<Integer>(intArr2);
		Set<Integer> s3 = new Set<Integer>(intArr3);
		
		System.out.println("Set 1: "+s1);
		System.out.println("Set 2: "+s2);
		System.out.println("Set 3: "+s3);
		
		s1.union(s2);
		System.out.println("result of union between Set 1 and Set 2: "+s1);
		s1.intersect(s3);
		System.out.println("result of intersect between Set 1 and Set 3: "+s1);
		
		System.out.println("please enter two numbers:");
		try {
			userInts[0] = sc.nextInt();
		}catch(InputMismatchException exception) {
			System.out.println("wrong input");
			System.exit(0);
		}
		try {
			userInts[1] = sc.nextInt();
		}catch(InputMismatchException exception) {
			System.out.println("wrong input");
			System.exit(0);
		}
				
		Set<Integer> userSet = new Set<Integer>(userInts);
		if(s1.isSubset(userSet)) System.out.println("the input Set is subset of Set 1");
		if(s2.isSubset(userSet)) System.out.println("the input Set is subset of Set 2");
		if(s3.isSubset(userSet)) System.out.println("the input Set is subset of Set 3");
		
		System.out.println("please enter a number:");
		int userInt = -1;
		try {
			userInt = sc.nextInt();
		}catch(InputMismatchException exception) {
			System.out.println("wrong input");
			System.exit(0);
		}
		
		System.out.println("it will be "+s1.isMember(userInt)+" to say that your number is a member in Set 1");
		s2.insert(userInt);
		System.out.println("this is Set 2 after adding your number: "+s2);
		s3.delete(userInt);
		System.out.println("this is Set 3 after delete your number from it: "+s3);
		
		
		
	}

}
