package codes;

import java.util.Scanner;

public class MainDrive {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("나이를 입력 : ");
		
		int userAge = scanner.nextInt();
		
		if(userAge >= 20) System.out.println("성인");
		else if(userAge >= 17) System.out.println("고등학생");
		else if(userAge >= 14) System.out.println("중학생");
		else if(userAge >= 8) System.out.println("초등학생");
		else System.out.println("미취학");
	}
}
