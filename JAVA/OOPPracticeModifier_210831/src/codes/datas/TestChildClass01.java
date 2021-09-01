package codes.datas;

// TestClass의 자녀1
// 상속관계 o + 같은 집 o

public class TestChildClass01 extends TestClass{
	void test() {
		TestClass tc = new TestClass();
		
		// public 으로 아무나 가능
		tc.num1 = 10;
		
		// 자식이라서 사용 가능한거 x
		// 같은 집에 살아서 변수를 통해 접근 가능
		tc.num2 = 20;
		
		// default - 같은 집에 살면 변수를 통해 접근 o
		tc.num3 = 30;
		
		
	}
}
