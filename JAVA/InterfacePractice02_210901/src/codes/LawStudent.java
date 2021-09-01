package codes;

// 아마추어 변호사

public class LawStudent implements Lawyer {

	@Override
	public void defense() {
		System.out.println("아마추어 법대생이 변호를 합니다.");
		System.out.println("법정에서 무승부로 판결이 났습니다.");
	}
	
}
