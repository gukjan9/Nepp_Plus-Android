package codes;

public class MainDrive {
	public static void main(String[] args) {
		Book b1 = new Book();
		b1.title = "자바의 정석";
		b1.rentFee = 3000;
		b1.limitAge = 12;
		
		Book b2 = new Book();
		b2.title = "타짜";
		b2.rentFee = 1000;
		b2.limitAge = 19;
		
		Book b3 = new Book();
		b3.title = "걸리버 여행기";
		b3.rentFee = 1500;
		b3.limitAge = 0;		// 전체이용가
		
		User u1 = new User();
		u1.name = "김아동";
		u1.birthYear = 2015;
		u1.point = 10000;
		
		User u2 = new User();
		u2.name = "이중딩";
		u2.birthYear = 2007;
		u2.point = 1500;
		
		User u3 = new User();
		u3.name = "박성인";
		u3.birthYear = 1995;
		u3.point = 5000;
		
		b1.printBookInfo();
		b2.printBookInfo();
		b3.printBookInfo();
	}
}
