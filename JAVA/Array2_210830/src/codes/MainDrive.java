package codes;

public class MainDrive {
	public static void main(String[] args) {
		int[] oddNum = new int[50];
		
		// for 로 데이터 입력
		for(int i = 0; i < oddNum.length; i++) oddNum[i] = 2*i+1;
		
		// for - each 로 출력
		for(int num : oddNum) System.out.println(oddNum[num]);
	}
}
