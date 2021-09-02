package codes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import codes.datas.UserData;

public class MainDrive {
	public static void main(String[] args) {
		
		printMenu();
		
	}
	
	static void printMenu() {
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("======= 전화번호부 =======");
			System.out.println("1. 전화번호 추가 등록");
			System.out.println("2. 전화번호 목록 조회");
			System.out.println("0. 프로그램 종료");
			System.out.println("=======================");
			System.out.println("메뉴 입력 : ");
			
			int inputMenu = scanner.nextInt();
			
			if(inputMenu == 0) break;
			else if(inputMenu == 1){
				addPhoneNum();
			}
			else if(inputMenu == 2) {
				showAllPhoneNum();
			}
			else {
				System.out.println("잘못된 입력입니다. 메뉴로 돌아갑니다...");
				
				// 2초 정도 프로그램 정지 -> 그 후에 메뉴로
				try {
					Thread.sleep(2000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("프로그램을 종료합니다...");
	}
	
	static void addPhoneNum() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("이름 입력 : ");
		String name = scanner.next();
		
		System.out.println("전화번호 입력 : ");
		String phoneNum = scanner.next();
		
		System.out.println("출생년도 입력 : ");
		int birthYear = scanner.nextInt();
		
		String content = String.format("%s,%s,%d", name, phoneNum, birthYear);
		// System.out.println(content);
		
		savePhoneNumToFile(content);
	}
	
	// 가공된 한 줄을 파일 (myPhoneBook.csv) 에 추가해주는 함수
	static void savePhoneNumToFile(String content) {
		File myFile = new File("myPhoneBook.csv");
		
		// 지정된 파일에, 데이터 작성을 해주는 클래스
		try {
			FileWriter fw = new FileWriter(myFile, true);		// 기존 내용에 추가
			
			// FileWriter 는 2byte씩 데이터 처리 -> 한 글자씩 적는다.
			// BufferedWriter 사용
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.append(content);
			bw.newLine();
			
			// 다른 경우에도 파일에 접근할 수 있게 사용이 끝나면 파일을 닫자
			bw.close();
			fw.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void showAllPhoneNum() {
		// ArrayList 로 UserData 목록에 담기
		List<UserData> userList = new ArrayList<>();
		
		addUsersByFile(userList);
		
		for(UserData user : userList) {
			System.out.println(user);
		}
	}
	
	static void addUsersByFile(List<UserData> list) {
		File myFile = new File("myPhoneBook.csv");
		
		try {
			FileReader fr = new FileReader(myFile);
			
			BufferedReader br = new BufferedReader(fr);
			
			// br 이 한 줄씩 계속 내려오게
			while(true) {
				String line = br.readLine();
				
				if(line == null) break;
				
				String[] userInfo = line.split(",");
				
				// UserData 객체를 만들어서 -> 생성자의 파라미터로 대입 -> list 에 추가
				list.add(new UserData(userInfo[0], userInfo[1], Integer.parseInt(userInfo[2])));
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("아직 저장된 번호가 없습니다.");
			System.out.println("전화번호부 파일이 만들어지지 않았습니다.");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
