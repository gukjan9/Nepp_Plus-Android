package codes;

import java.util.Scanner;

public class MainDrive {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("정수를 입력하시오 : ");
		int num = scanner.nextInt();
		
		boolean isPrimeNum = true;
		int divideNum;
		
		for(int i = 2; i < num / 2; i++) {				// primeNum 의 반이 넘어가면 어차피 의미가 없음 * 2
			if(num % i == 0) {
				isPrimeNum = false;
				divideNum = i;
				break;
			}
		}
		
		if(isPrimeNum) System.out.println("소수 맞음");
		else System.out.println("소수 아님");
	}
}
