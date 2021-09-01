package codes;

import codes.datas.SmartTv;
import codes.datas.Tv;
import codes.datas.VideoTv;

public class MainDrive {
	public static void main(String[] args) {
		// 다형성 - 같은 종류의 변수, 다른 종류의 인스턴스
		
		Tv tv1 = new Tv();
		
		// Tv 변수에 VideoTv 객체를 담아보자
		Tv tv2 = new VideoTv();
		
		tv1.turnOnTv();
		tv2.turnOnTv();
		// tv2.record(); - 불가능
		
		Tv tv3 = new SmartTv();
		tv3.turnOnTv();
		
		// method 의 종류 - 변수 타입에 맞는 method 만 실행
		
		// Casting 후 사용가능
		VideoTv temp = (VideoTv) tv2;
		temp.recordVideo();
	}
}
