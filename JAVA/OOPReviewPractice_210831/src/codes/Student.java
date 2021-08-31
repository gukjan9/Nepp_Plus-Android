package codes;

public class Student {
	String name;
	int birthYear;
	boolean isMale;
	String parentPhoneNum;
	int koreanScore;
	int mathScore;
	
	int getAverageScore() {
		int result = (koreanScore + mathScore) / 2;
		
		return result;
	}

	public Student(String name, int birthYear, boolean isMale, String parentPhoneNum, int koreanScore, int mathScore) {
		super();
		this.name = name;
		this.birthYear = birthYear;
		this.isMale = isMale;
		this.parentPhoneNum = parentPhoneNum;
		this.koreanScore = koreanScore;
		this.mathScore = mathScore;
	}

	public Student() {
		super();
	}
}
