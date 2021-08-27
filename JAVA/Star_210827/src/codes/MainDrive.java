package codes;

public class MainDrive {
	public static void main(String args[]) {
		System.out.println("#1");
		
		for(int i = 0; i < 5; i++) System.out.println("*****");
		
		System.out.println();
		System.out.println("#2");
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < i+1; j++) System.out.print("*");
			System.out.println();
		}
		
		System.out.println();
		System.out.println("#3");
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5-i; j++) System.out.print("*");
			System.out.println();
		}
		
		System.out.println();
		System.out.println("#4");
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j <= 5; j++) {
				if(j < 5-i) System.out.print(" ");
				else System.out.print("*");
			}
			System.out.println();
		}
	}
}