package codes;

public class MainDrive {
	public static void main(String[] args) {
		Dog dog1 = new Dog();
		
		dog1.birthYear = 2015;
		dog1.play();
		
		dog1.keepHome();
		
		System.out.println(dog1.toString());		// "패키지.클래스@메모리주소"
		
		
		// Dog2, Dog3 가 같은지 다른지
		Dog dog2 = new Dog();
		dog2.name = "바둑이";
		dog2.birthYear = 2014;
		dog2.isMale = true;
		
		Dog dog3 = new Dog();
		dog3.name = "바둑이";
		dog3.birthYear = 2014;
		dog3.isMale = true;
		
//		if(dog1.equals(dog2)) System.out.println("같은 강아지");
//		else System.out.println("다른 강아지");
		
		dog1.bark();
		
		Cat cat1 = new Cat();
		cat1.name = "마루";
		cat1.birthYear = 2014;
		cat1.isMale = true;
		
		cat1.bark();
	}
}
