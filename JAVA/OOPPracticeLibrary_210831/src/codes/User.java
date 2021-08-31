package codes;

import java.util.Calendar;

public class User {
	String name;
	int birthYear;
	int point;
	
	boolean rentBook(Book book) {
		boolean isPointOk = this.point >= book.rentFee;
		boolean isAgeOk = this.getKoreanAge() >= book.limitAge;
		boolean isBookStatusOk = book.rentUser == null;
		
		if(isPointOk && isAgeOk && isBookStatusOk) return true;
		else{
			if(!isPointOk) System.out.println("포인트 부족");
			else if(!isAgeOk) System.out.println("연령 제한");
			else System.out.println("누가 빌려감");
			
			return false;
		}
	}
	
	int getKoreanAge() {
		// Calendar cal = Calendar.getInstance();
		
		int age = 2021 - this.birthYear + 1;
		
		return age;
	}
}
