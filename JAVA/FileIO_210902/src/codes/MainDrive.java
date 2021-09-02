package codes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.processing.FilerException;

// Main Method 에는 최대한 적은 코드만 남겨둠
// 별개 method 로 기능 분리

public class MainDrive {
	public static void main(String[] args) {
		
		
		writeFile();
		
		readFile();
	}
	
	static void writeFile() {
		// 파일 경로 설정
		File myFile = new File("myPhoneBook.txt");
		
		try {
			// 파일에 실제로 기록 담당 클래스 (true 파라미터 추가하면 파일을 덮어쓰는게 아닌 이미 만들어진거에 내용 추가)
			FileWriter fw = new FileWriter(myFile, true);
			// 편하게 한번에 여러 글자를 처리하게 도와주는 클래스
			 BufferedWriter bw = new BufferedWriter(fw);
			 
			// bw 를 이용해서 파일에 내용 기록
			 bw.append("김국장,010-1111-2222,1997");
			 bw.newLine();
			 
			 // 기록이 끝나면 파일을 닫아주자
			 bw.close();
			 fw.close();
			 
			 System.out.println("파일 작성 완료");
		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	static void readFile() {
		File myFile = new File("myPhoneBook.txt");
		
		try {
			FileReader fr = new FileReader(myFile);
			
			// fr 을 한 번에 한 문장씩 String 으로 불러내게 도와주는 클래스
			BufferedReader br = new BufferedReader(fr);
			
			// 몇 줄 들어있을지 불명확 -> 무한 반복 / 조건 맞으면 break;
			while (true) {
				String line = br.readLine();
				
				if(line == null) {
					break;
				}
				
				// 읽어올 내용이 있으면 내용 출력
				
				String[] userInfo = line.split(",");
				
				String name = userInfo[0];
				String phoneNum = userInfo[1];
				
				// "1997" String -> int 로 변환 -> Wrapper 클래스 기능 활용
				int birthYear = Integer.parseInt(userInfo[2]);				
				
				System.out.println(String.format("%s(%d년생) : %s", name, birthYear, phoneNum));
			}
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
