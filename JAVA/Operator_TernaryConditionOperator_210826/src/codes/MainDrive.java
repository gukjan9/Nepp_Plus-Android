package codes;

public class MainDrive {
	public static void main(String[] args) {
		
		int salary = 2500;
		int workHour = 20;
		
		String result = salary >= 2800? "취업 ok" : workHour < 30? "워라벨좋아서 ok" : "취업 no";
		
		System.out.println(result);
	}
}
