package codes;

public class MainDrive {
	public static void main(String[] args) {
		MyTestClass mtc = new MyTestClass();
		
		mtc.doTest(20);
	}
}

// overriding : 상속의 개념에서, 부모가 물려준 method 의 실행 내용을 재정의 (커스터마이징) 하여 사용
// overloading : 상속과 무관. 하나의 클래스 안에서, 같은 이름의 method 가 중복이 아닌 걸로 인정되는 방법