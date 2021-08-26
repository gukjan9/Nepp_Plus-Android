package codes;

import java.util.Scanner;

public class MainDrive {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter your weight : ");
		double weight = scanner.nextInt();
		System.out.println("Enter your height : ");
		double height = scanner.nextInt();
		height /= 100;
		
		double BMI = weight / ((height) * (height));
		
		System.out.println("BMI is " + BMI + " ");
		if(BMI >= 30) System.out.println("고도비만");
		else if(BMI >= 25) System.out.println("비만");
		else if(BMI >= 23) System.out.println("과체중");
		else if(BMI >= 18.5) System.out.println("정상");
		else System.out.println("저체중");
	}
}
