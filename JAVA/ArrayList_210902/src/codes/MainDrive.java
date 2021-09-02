package codes;

import java.util.ArrayList;
import java.util.List;

import codes.datas.StudentData;

public class MainDrive {
	public static void main(String[] args) {
		List<String> studentNames = new ArrayList<>();
		
		studentNames.add("김국장");
		studentNames.add("이국장");
		
		// 3번째 학생 -> 맨 앞에 추가
		studentNames.add(0, "박국장");
		
		// 모든 학생 출력
		for(int i = 0; i < studentNames.size(); i++) {
			System.out.println(studentNames.get(i));
		}
//		for(String name : studentNames) {
//			System.out.println(name);
//		}
		
		// 학생 찾기
		if(studentNames.contains("김국장")) System.out.println("있다.");
		else System.out.println("없다.");
		
		
		
		List<StudentData> studentList = new ArrayList<>();
		
		// 학생 추가
		studentList.add(new StudentData("김국장", 1997));
		studentList.add(new StudentData("이국장", 1998));
		
		for(int i = 0; i < studentList.size(); i++) System.out.println();
		
		// 학생 찾기
		if(studentList.contains(new StudentData("김국장", 1997))) System.out.println("있다.");
		else System.out.println("없다.");
		
		System.out.println(studentList.indexOf(new StudentData("김국장", 1997)));
		
		boolean removeResult = studentList.remove(new StudentData("김국장", 1995));
		System.out.println(removeResult);
		
		// 맨 앞 학생 삭제
		StudentData removedStd = studentList.remove(0);
		System.out.println("삭제된 학생 : " + removedStd);
		
		studentList.clear();
		
		
		List<Integer> selectedWeekdays = new ArrayList<>();
		selectedWeekdays.add(1);
		String thisYear = "2021";
		
	}
}
