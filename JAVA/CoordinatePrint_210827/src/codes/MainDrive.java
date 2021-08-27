package codes;

public class MainDrive {
	public static void main(String[] args) {
		
		System.out.println("#1");
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				System.out.print("[" + i + "," + j + "]");
				// System.out.print(" ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("#2");
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(j == 3) System.out.print("[" + i + "," + j + "]");
				else System.out.print("     ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("#3");
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(j == 4-i) System.out.print("[" + i + "," + j + "]");
				else System.out.print("     ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("#4");
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(j == 4-i) System.out.print("[" + i + "," + j + "]");
				else if(j == i) System.out.print("[" + i + "," + j + "]");
				else System.out.print("     ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("#5");
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(i == 0 || i == 4) System.out.print("[" + i + "," + j + "]");
				else {
					if(j == 0 || j == 4) System.out.print("[" + i + "," + j + "]");
					else System.out.print("     ");
				}
			}
			System.out.println();
		}
	}
}
