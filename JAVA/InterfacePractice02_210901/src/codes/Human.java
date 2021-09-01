package codes;

public class Human {
	// 멤버변수로 변호사(역할 - 인터페이스)를 둔다.
	Lawyer myLawyer;
	
	// 변호사가 누구인지 지정하는 메쏘드
	public void setMyLawyer(Lawyer l) {
		// 인터페이스 변수 : 해당 역할이 수행 가능한 모든 클래스
		this.myLawyer = l;
	}
	
	// 고소를 당하는 함수
	void goso() {
		if(this.myLawyer != null) this.myLawyer.defense();
		else{
			System.out.println("평범한 사람이 직접 고소 대응");
			System.out.println("법정에서 패배");
		}
	}
}
