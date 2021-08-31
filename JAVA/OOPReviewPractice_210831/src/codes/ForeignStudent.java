package codes;

public class ForeignStudent extends Student{
	int englishScore;
	
	// super 에서 생성자 끌어오기
	public ForeignStudent() {
		super();
	}

	public ForeignStudent(String name, int birthYear, boolean isMale, String parentPhoneNum, int koreanScore,
			int mathScore, int englishScore) {
		super(name, birthYear, isMale, parentPhoneNum, koreanScore, mathScore);
		
		this.englishScore = englishScore;
	}


	@Override
	int getAverageScore() {
		int avg = (this.koreanScore + this.mathScore + this.englishScore) / 3;
		
		return avg;
	}
	
	@Override
	public String toString() {
		return this.name + "-" + this.birthYear + "년생/평균" + this.getAverageScore() + "점";
	}

}
