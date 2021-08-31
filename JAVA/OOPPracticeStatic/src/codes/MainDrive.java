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
		
		System.out.println(Card.width);
		System.out.println(Card.height);
		
		c1.printCardInfo();
		c2.printCardInfo();
		
		Card.printCardClassInfo();
	}
}
