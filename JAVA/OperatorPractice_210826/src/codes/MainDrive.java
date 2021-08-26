package codes;

public class MainDrive {
	public static void main(String[] args) {
		int myBirthYear = 1997;
		int thisYear = 2021;
		int myKoreanAge = thisYear - myBirthYear + 1;
		
		System.out.println(myBirthYear + "년생-" + myKoreanAge + "세");
		
		double startMoney = 100000;
		double nowMoney = 115000;
		double profit = (nowMoney / startMoney) * 100;
		
		System.out.println("Profit is : " + profit + "%");
	}
}
