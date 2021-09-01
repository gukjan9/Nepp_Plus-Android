package codes;

public class MainDrive {
	public static void main(String[] args) {
		String url = "https://naver.com";
		
		// contains & startsWith
		if(url.contains(".com")) System.out.println("URL 입니다.");
		else System.out.println("URL 이 아닙니다.");
		
		if(url.startsWith("https://")) System.out.println("보안 처리 된 인터넷 연결");
		else System.out.println("평문 통신");
		
		// math
		System.out.println(Math.pow(3,  6));
		// sqrt 제곱근
	}
}
