package codes;

public class MainDrive {
	public static void main(String[] args) {
		String name = "국장";
		int height = 165;
		
		System.out.println("My name is " + name + ". My height is " + height + ".");
		
		int num1 = 50;
		int num2 = 20;
		
		System.out.println("Before swap - num1:" + num1 + " num2:" + num2);
		
		int temp = num1;
		num1 = num2;
		num2 = temp;
		
		System.out.println("After swap - num1:" + num1 + " num2:" + num2);
	}
}
