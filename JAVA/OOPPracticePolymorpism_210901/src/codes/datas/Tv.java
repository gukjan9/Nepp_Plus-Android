package codes.datas;

public class Tv {
	public boolean isTurnOn;
	public int channelNum;
	
	public void turnOnTv() {
		isTurnOn = true;
		
		System.out.println("Tv가 켜집니다.");
		System.out.println();
	}
}
