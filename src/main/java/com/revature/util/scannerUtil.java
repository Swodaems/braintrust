package com.revature.util;

import java.util.Scanner;

public class scannerUtil {
	static Scanner scanner = new Scanner(System.in);
	
	public static int getInput() {
		int input = -1;
		
		while(input < 0 ) {
			System.out.println("Please insert an integer value greater than or equal to 0:");
			if(!scanner.hasNextInt()) {
				scanner.nextLine();
				continue;
			}
			input = scanner.nextInt();			
		}
		
		return input;
	}
	public static int getInputMax(int max) {
		int input = -1;
		
		while(input < 0 || input > max) {
			System.out.println("Please insert an integer value between 0 and " + max);
			if(!scanner.hasNextInt()) {
				scanner.nextLine();
				continue;
			}
			input = scanner.nextInt();			
		}
		
		return input;
	}

	public static String getStringInput() {
		String input = "";
		while(input.isEmpty()) {
			input = scanner.nextLine();
//			scanner.nextLine();
		}
		return input;
	}
	
	
}
