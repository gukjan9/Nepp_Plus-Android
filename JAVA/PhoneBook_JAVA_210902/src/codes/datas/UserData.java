package codes.datas;

import java.util.Calendar;

public class UserData {
	private String name;
	private String phoneNum;
	private int birthYear;
	
	// 모든 데이터를 한 번에 세팅하기 위해 생성자 생성
	public UserData(String name, String phoneNum, int birthYear) {
		super();
		this.name = name;
		this.phoneNum = phoneNum;
		this.birthYear = birthYear;
	}
	
	// getter, setter 를 활용한 정보 은닉
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public int getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
	
	public int getKoreanAge() {
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		
		return thisYear - this.birthYear + 1;
	}
	
	@Override
	public String toString() {
		return String.format("%s(%d세) - %s",  this.name, this.getKoreanAge(), this.phoneNum);
	}
}
