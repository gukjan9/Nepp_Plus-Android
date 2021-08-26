package codes;

import java.util.Scanner;

public class MainDrive {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter consumed money : ");
		int money = scanner.nextInt();
		
		// ~10k - 0%
		// ~50k - 5%
		// ~ 100k - 10%
		// 100k ~ - 20%
		
		int discountMoney;
		int k5 = 2000;				// 5k 할인율
		int k10 = 5000;				// 10k 할인율
		
		if(money > 100000) discountMoney = k5 + k10 + (int)((money-100000)*0.2);
		else if(money > 50000) discountMoney = k5 + (int)((money-50000)*0.1);
		else if(money > 10000) discountMoney = (int)((money-10000)*0.05);
		else discountMoney = 0;
		
		System.out.println(discountMoney);
	}
}
