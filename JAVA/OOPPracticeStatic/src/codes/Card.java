package codes;

public class Card {
	String pattern;			// 포커 카드 문양
	int number;				// k-13, q-12, j-11
	
	static double width;
	static double height;
	
	void printCardInfo() {
		System.out.println("무늬 : " + this.pattern);
		System.out.println("숫자 : " + this.number);
	}
	
	void printCardClassInfo() {
		System.out.println("포커 카드에 대한 묘사를 하는 클래스입니다.");
	}
}
