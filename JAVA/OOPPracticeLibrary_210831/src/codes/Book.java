package codes;

public class Book {
	String title;
	int limitAge;
	int rentFee;
	
	User rentUser;		// 기본으로 null 값
	
	void printBookInfo() {
		System.out.println("제목 : " + this.title);
		System.out.println("연령 제한 : " + this.limitAge + "세 이용가");
		System.out.println("대여료 : " + this.rentFee + "원");
		
		if(this.rentUser != null) {
			System.out.println("상태 : 대여 불가");
			System.out.println(this.rentUser.name + "이(가) 빌려간 상태입니다.");
		}
		else System.out.println("상태 : 대여 가능");
	}
}
