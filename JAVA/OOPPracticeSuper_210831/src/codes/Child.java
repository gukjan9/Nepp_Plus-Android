package codes;

public class Child extends Parent {
	
	// 자녀한테 재료를 받아서 부모한테 보내주자
	public Child(String name, int number) {				// 부모 클래스가 기본 생성자 지원 x (super 안됨)
		super(name, number);
		// TODO Auto-generated constructor stub
	}
}
