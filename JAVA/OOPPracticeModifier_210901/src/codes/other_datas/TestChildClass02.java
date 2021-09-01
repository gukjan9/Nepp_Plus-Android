package codes.other_datas;

import codes.datas.TestClass;

public class TestChildClass02 extends TestClass{
	void test() {
		TestClass tc = new TestClass();
		
		// public
		tc.num1 = 10;
		
		// protected - 상속을 받아와서 내 자료로 사용 o
		this.num2 = 20;
		
		// default - 다른 집에 살면, 자녀여도 변수 접근 x
		// tc.num3
		
		// default - 다른 집에 살면, 자녀여도 물려주지도 않는다.
		// this.num3
	}
}
