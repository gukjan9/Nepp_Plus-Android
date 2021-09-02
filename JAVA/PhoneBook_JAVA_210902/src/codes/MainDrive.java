package codes;

import java.util.Scanner;

public class MainDrive {
	public static void main(String[] args) {
		
		printMenu();
		
	}
	
	static void printMenu() {
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("======= 전화번호부 =======");
			System.out.println("1. 전화번호 추가 등록");
			System.out.println("2. 전화번호 목록 조회");
			System.out.println("0. 프로그램 종료");
			System.out.println("=======================");
			System.out.println("메뉴 입력 : ");
			
			int inputMenu = scanner.nextInt();
			
			if(inputMenu == 0) break;
		}
		System.out.println("프로그램을 종료합니다...");
	}
}
