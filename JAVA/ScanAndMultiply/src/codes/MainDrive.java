package codes;

import java.util.Scanner;

public class MainDrive {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("정수를 입력하시오 : ");
		int num = scanner.nextInt();
		
		System.out.println("횟수를 입력하시오 : ");
		int cnt = scanner.nextInt();
		
		int result = 1;
		
		for(int i = 0; i < cnt; i++) {
			result *= num;
		}
		
		System.out.println("Result is : " + result);
		
	}
}