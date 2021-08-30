package codes;

public class MainDrive {
	public static void main(String[] args) {
		int[] numbers = new int[100];
		
		String[] studentNames = new String[12];
		
		numbers[0] = 10;
		numbers[99] = 50;
		
		
		
		int[] oneToTwoHundred = new int[200];
		
		for(int i = 0; i < oneToTwoHundred.length; i++) {
			oneToTwoHundred[i] = i+1;
			System.out.println(oneToTwoHundred[i]);
		}
		
		// for - each 문 (배열의 내용물을 조회할 때 for문 보다 for-each)
		for(int num : oneToTwoHundred) System.out.println(oneToTwoHundred[num]);
	}
}
