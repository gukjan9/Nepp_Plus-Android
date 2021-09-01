package codes;

public class MainDrive {
	public static void main(String[] args) {
		Human h1 = new Human();
		SmartHuman sh1 = new SmartHuman();
		
		h1.setMyLawyer(sh1);		// 변호사 지정해주냐 안해주냐에 따라 결과가 다름
		h1.goso();
		
		Human h2 = new Human();
		LawStudent ls1 = new LawStudent();
		
		h2.setMyLawyer(ls1);
		h2.goso();
		
		Human h3 = new Human();
		
		// 직접 override
		h3.setMyLawyer(new Lawyer() {
			
			@Override
			public void defense() {
				System.out.println("h3이 스스로 변호합니다.");
				
			}
		});
		h3.goso();
	}
}
