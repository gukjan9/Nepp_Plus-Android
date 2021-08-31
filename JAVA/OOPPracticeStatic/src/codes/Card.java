package codes;

public class Card {
	String pattern;			// 포커 카드 문양
	int number;				// k-13, q-12, j-11
	
	// 모든 객체가 다 공유
	static double width;
	static double height;
	
	void printCardInfo() {
		System.out.println("무늬 : " + this.pattern);
		System.out.println("숫자 : " + this.number);
	}
	
	// 카드 클래스 자체에 대한 설명 - 단순 기능 수행
	static void printCardClassInfo() {
		System.out.println("포커 카드에 대한 묘사를 하는 클래스입니다.");
		// System.out.println(this.pattern); 	// static보다 후에 만들어지므로 사용할 수 없음
		System.out.println(width); 				// 같은 static 이므로 사용 가능
	}
}
