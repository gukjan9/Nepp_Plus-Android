package codes;

import codes.datas.TestClass;

// ctrl + shift + o - 필요없는 패키지 다 지움 (import)
// Ctrl + alt = o - 안드로이드 스튜디오

// 상속 여부
// 어느 패키지에 속해있는가
public class MainDrive {
	public static void main(String[] args) {
		TestClass tc1 = new TestClass();
		
		// public 변수 접근 가능
		tc1.num1 = 10;
		
		// 남에게 허용 x
		// tc1.num2
		
		// default는 남에게 허용 x, num3 접근 불가
		// tc1.num3
		
		
	}
}
