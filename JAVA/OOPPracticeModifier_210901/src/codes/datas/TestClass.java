package codes.datas;

public class TestClass {
	public int num1;
	protected int num2;
	int num3;
	private int num4;
	
	static TestClass tc = null;
	
//	// 참고 - 생성자를 private
//	private TestClass() {
//		
//	}
//	
//	// SingleTon 패턴 -> 객체를 딱 하나만으로 한정 짓고 싶을 때
//	
//	// 객체를 달라고 하는 함수
//	// 생성자를 private 으로 한정 시키고 쓰고 싶으면 getInstance 함수로 가져다 써
//	public static TestClass getInstance() {
//		if(tc == null) tc = new TestClass();
//		
//		return tc;
//	}
}
