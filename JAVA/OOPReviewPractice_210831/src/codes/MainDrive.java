package codes;

public class MainDrive {
	public static void main(String[] args) {
		Student std1 = new Student("A학생", 2005, true, "010-1111-2222", 80, 90);
		
		System.out.println(std1.getAverageScore());
		
		ForeignStudent std2 = new ForeignStudent("외국준비학생", 2004, false, "010-3333-4444", 80, 90, 100);
		
		System.out.println(std2.toString());
	}
}
