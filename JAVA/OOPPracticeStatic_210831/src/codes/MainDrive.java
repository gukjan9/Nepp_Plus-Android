package codes;

public class MainDrive {
	public static void main(String[] args) {
		Card c1 = new Card();
		c1.pattern = "스페이드";
		c1.number = 5;
		
		Card c2 = new Card();
		c2.pattern = "하트";
		c2.number = 7;
		
		c1.width = 5.8;
		c1.height = 12.7;
		
		System.out.println(Card.width);			// 공통 속성 - 카드 전체 (종족 특성)
		System.out.println(Card.height);
		
		c1.printCardInfo();
		c2.printCardInfo();
		
		Card.printCardClassInfo();			// static 붙이면 에러 x
	}
}
