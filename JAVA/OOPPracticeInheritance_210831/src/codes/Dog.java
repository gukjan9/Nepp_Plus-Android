package codes;

public class Dog extends Pet {
//	int birthYear;
//	boolean isMale;
//	
//	void run() {
//		System.out.println("달린다.");
//	}
	
	void keepHome() {
		System.out.println("집을 지킨다.");
	}
	
	@Override
	void bark() {
		// super.bark();
		System.out.println("왈왈");
	}
	
	@Override
		public String toString() {
			// TODO Auto-generated method stub
			String content;
			
			if(this.isMale) content = this.name + "-" + this.birthYear+ "년생, 수컷";
			else content = this.name + "-" + this.birthYear+ "년생, 암컷";
			
			return content;
		}
	
	public boolean equals(Object obj) {
		Dog otherDog = (Dog) obj;
		
		if(this.name == otherDog.name && this.birthYear == otherDog.birthYear) return true;
		else return false;
	}
}
