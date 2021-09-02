package codes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainDrive {
	public static void main(String[] args) {
		Calendar myBirthDay = Calendar.getInstance();
		
		myBirthDay.set(1997, Calendar.APRIL, 14, 16, 24);
		myBirthDay.set(Calendar.MINUTE, 21);
		
		System.out.println("요일 : " + myBirthDay.get(Calendar.DAY_OF_WEEK));
		
		Calendar friendBirthDay = Calendar.getInstance();
		friendBirthDay.set(1998, Calendar.AUGUST, 8);
		
		if(myBirthDay.getTimeInMillis() >= friendBirthDay.getTimeInMillis()) System.out.println("내가 더 늦게 태어났다.");
		else System.out.println("친구가 더 늦게 태어났다.");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월 d일 a h시 m분");
		System.out.println("");
	}
}
