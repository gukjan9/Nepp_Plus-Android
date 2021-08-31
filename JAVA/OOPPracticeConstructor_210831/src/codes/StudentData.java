package codes;

public class StudentData {
	// 멤버변수
	String name;
	int birthYear;
	String address;
	
	// 생성자
	public StudentData(String inputName, int inputBirthYear, String inputAddress) {
		this.name = inputName;
		this.birthYear = inputBirthYear;
		this.address = inputAddress;
	}
	
	// 아무 일도 하지 않는 생성자 - overloading
	// 이름 모름, 거주지 불명 으로 기본값 세팅
	public StudentData() {
		this("이름 모름", -1, "거주지 불명");
	}

	public StudentData(String name) {
		super();
		this.name = name;
	}
	
	void printStudentInfo() {
		System.out.println("이름 : " + this.name);
	}
}
