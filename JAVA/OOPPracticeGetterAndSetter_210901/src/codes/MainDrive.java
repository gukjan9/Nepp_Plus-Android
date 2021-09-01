package codes;

import codes.datas.Account;
import codes.datas.User;

public class MainDrive {
	public static void main(String[] args) {
		User u1 = new User();
		// u1.name = setName("GukJang");
		
		Account ac1 = new Account();
		ac1.accountNumber = "123-456";
		// ac1.balance = 500000000;		직접 대입 x
		ac1.setBalance(500000000);
		
		// 클래스의 멤버 변수에는 대부분 직접 대입하는 행위를 허용하지 않는다.
		// 변경 되었을 때 왜 그렇게 됐는지 추적 불가능
		
		// 해결 : 멤버 변수 앞에 private 을 붙여서 변수는 해당 클래스 내에서만 사용하도록 - 정보의 은닉
		ac1.owner = u1;
		
		// getter - 저장된 값 확인, setter - 값 변경용
		ac1.setBalance(-5000000);
		ac1.getBalance();
	}
}
