package codes;

import java.util.Scanner;

public class MainDrive {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter year : ");
		int year = scanner.nextInt();
		
		if(year % 4 == 0) {
			if(year % 100 == 0) {
				if(year % 400 == 0) System.out.println("윤년입니다.");
				else System.out.println("윤년이 아닙니다.");
			}
			else System.out.println("윤년입니다.");
		}
		else System.out.println("윤년이 아닙니다.");
	}
}
