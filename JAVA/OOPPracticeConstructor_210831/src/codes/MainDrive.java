package codes;

public class MainDrive {
	public static void main(String[] args) {
		StudentData std1 = new StudentData("국장", 1997, "경기도");
		StudentData std2 = new StudentData();		// 이름모름
		
		System.out.println(std1.name);
	}
}
