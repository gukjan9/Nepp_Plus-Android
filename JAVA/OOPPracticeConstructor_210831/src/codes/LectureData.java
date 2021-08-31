package codes;

public class LectureData {
	String title;
	String startDate;
	String endDate;
	int maxPeopleCount;		// 최대 정원
	String campusName;
	String teacherName;
	
	
	// 우클릭 -> source -> Generate Constructor using fields (=멤버변수)
	public LectureData(String title, String startDate, String endDate, int maxPeopleCount, String campusName,
			String teacherName) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.maxPeopleCount = maxPeopleCount;
		this.campusName = campusName;
		this.teacherName = teacherName;
	}
}
