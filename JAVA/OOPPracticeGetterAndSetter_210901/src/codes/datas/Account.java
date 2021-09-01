package codes.datas;

public class Account {
	public String accountNumber;
	private long balance;		// 잔고
	
	public User owner;
	
	// 잔고를 세팅하는 method 를 별도로 추가 -> 어디서든 접근 가능하도록
	public void setBalance(long balance) {
		this.balance = balance;
	}
	
	public long getBalance() {
		return this.balance;
	}
}
