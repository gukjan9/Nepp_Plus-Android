package codes.datas;

public class StudentData {
	String name;
	int birthYear;
	
	public StudentData(String name, int birthYear) {
		super();
		this.name = name;
		this.birthYear = birthYear;
	}
	
	@Override
	public String toString() {
		String message = String.format("%s(%d년생)", this.name, this.birthYear);
		
		return message;
	}

	
	@Override
	public boolean equals(Object obj) {
		// 일단 형태가 맞는지부터
		if(!(obj instanceof StudentData)) return false;
		
		if(otherStd.name == this.name && otherStd.birthYear == this.birthYear) return true;
		else return false;
	}
}
