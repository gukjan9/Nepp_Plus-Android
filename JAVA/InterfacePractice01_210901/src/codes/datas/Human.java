package codes.datas;

// 인터페이스 implements 하면 밑에 구현해줘야함
public class Human extends Animal implements Student, TaxPayer, Worker {

	@Override
	public void doStudy() {
		System.out.println("사람이 학생으로써 공부를 합니다.");
	}

	@Override
	public void goToSchool() {
		System.out.println("사람이 학생으로써 학교에 갑니다.");
	}

	@Override
	public void payTax() {
		System.out.println("사람이 납세자로써 세금을 냅니다.");
	}

	@Override
	public void goToWork() {
		System.out.println("사람이 근로자로써 출근합니다.");
		
	}

	@Override
	public void workHard() {
		System.out.println("사람이 근로자로써 열심히 일을 합니다.");
		
	}
	
}
