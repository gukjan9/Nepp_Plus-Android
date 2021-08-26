package codes;

import java.util.Scanner;

public class MainDrive {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter Korean Score : ");
		int koreanScore = scanner.nextInt();
		System.out.println("Enter Math Score : ");
		int mathScore = scanner.nextInt();
		System.out.println("Enter English Score : ");
		int englishScore = scanner.nextInt();
		
		if((koreanScore + mathScore + englishScore) / 3 >= 60) {
			if(koreanScore < 40 || mathScore < 40 || englishScore < 40) System.out.println("과락으로 불합격");
			else System.out.println("합격");
		}
		else System.out.println("불합격");
	}
}
