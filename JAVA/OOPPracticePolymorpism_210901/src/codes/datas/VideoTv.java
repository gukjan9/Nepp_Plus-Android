package codes.datas;

public class VideoTv extends Tv{
	public void recordVideo() {
		System.out.println("비디오로 화면 녹화합니다.");
	}
	
	@Override
	public void turnOnTv() {
		System.out.println("비디오를 세팅합니다.");
		System.out.println("세팅이 끝나면 Tv를 켭니다.");
		System.out.println();
	}
}
