package codes;

import java.util.Scanner;

public class MainDrive {
	public static void main(String[] args) {
		int[] inputNumbers = new int[6];
		
		// for 문 밖에서 Scanner 한 대만 생성
		Scanner scanner = new Scanner(System.in);
		
		for(int i = 0; i < inputNumbers.length; i++) {
			while(true) {
				System.out.println((i+1) + "번째 숫자 입력 : ");
				int num = scanner.nextInt();
				
				boolean isRangeOk = (1 <= num) && (num <= 45);		// 범위 검사
				boolean isDuplOk = true;							// 중복 검사
				
				// 중복 검사
				for(int input : inputNumbers) {
					if(num == input) {
						isDuplOk = false;
						break;
					}
				}
				
				if(isRangeOk && isDuplOk) {
					inputNumbers[i] = num;
					break;
				}
				
				else {
					if(!isRangeOk) System.out.print("1 ~ 45의 숫자만 입력 가능합니다.");
					else System.out.print("중복된 숫자입니다.");
					System.out.println(" 다시 입력하세요.");
				}	
			}
		}
		
		int[] winNumbers = new int[6];
		
		for(int i = 0; i < winNumbers.length; i++) {
			while(true) {
				int randomNum = (int)(Math.random()*45) + 1;
				
				boolean isDuplOk = true;
				
				for(int winNum : winNumbers) {
					if(randomNum == winNum) {
						isDuplOk = false;
						break;
					}
				}
				
				if(isDuplOk) {
					winNumbers[i] = randomNum;
					break;
				}
			}
		}
		
		for(int winNum : winNumbers) System.out.println(winNum);
		
		int correctNumCount = 0;
		
		for(int myNum : inputNumbers) {
			for(int winNum : winNumbers) {
				if (myNum == winNum) correctNumCount++;
			}
		}
		
		if (correctNumCount == 6) {
			System.out.println("1등 - 10억");
		}
		else if (correctNumCount == 5) {
			System.out.println("3등 - 2백만원");
		}
		else if (correctNumCount == 4) { 
			System.out.println("4등 - 5만원");
		}
		else if (correctNumCount == 3) {
			System.out.println("5등 - 5천원");
		}
		else {
			System.out.println("낙첨");
		}
		
	}
}
