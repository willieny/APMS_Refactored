package views;

import java.util.Scanner;

public class Utility {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void clearScreen() {
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}
	
	public static void enter() {
		System.out.println("Pressione ENTER para continuar.");
		sc.nextLine();
	}
}
