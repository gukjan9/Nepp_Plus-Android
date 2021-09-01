package codes;

// 머리가 좋아서 변호사 역할 수행 가능
public class SmartHuman implements Lawyer{

	@Override
	public void defense() {
		System.out.println("머리 좋은 사람이 변호를 합니다.");
		System.out.println("법정에서 승리했습니다.");
	}
	
}
