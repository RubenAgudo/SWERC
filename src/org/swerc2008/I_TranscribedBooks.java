package org.swerc2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is quite simple. You only have to sum the 9 numbers, then subtract the 10th number
 * for every book, and then find calculate the GCD of every pair. if the result is 0 or 1 print impossible
 * else print the GCD.
 * 
 * Cause the GCD is associative GCD(10,20,30,40) is equal to GCD(GCD(GCD(10,20),30),40) We constantly save
 * the GCD in the first position of the array, and we calculate always the first one with each other.
 * 
 * @author Rubén Agudo Santos
 *
 */

public class I_TranscribedBooks {

	private static Scanner sc;
	
	public static void main(String[] args) {
		
		try {
			doTheProcessing(args[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static void doTheProcessing(String path) throws FileNotFoundException {
		
		sc = new Scanner(new File(path));
		int cases = sc.nextInt();
		int result;
		
		for(int x = 0; x < cases; x++) {
			
			result = processBooks();
			if(result <= 1) {
				System.out.println("Impossible");
			} else {
				System.out.println(result);
			}
		}
		
	}

	private static int processBooks() {
		
		int books = sc.nextInt();
		int[] sum = new int[books];
		
		for(int x = 0; x < books; x++) {
			
			for(int y = 0; y < 9; y++) {
				
				sum[x] += sc.nextInt();
				
			}
			sum[x] -= sc.nextInt();
		}
		
		return gcd(sum);
	}

	private static int gcd(int[] sum) {
		
		int result = 0;
		int x = 0;
		boolean exit = false;
		
		while(x < sum.length && !exit) {
			
			result = gcdRecursive(sum[0], sum[x]);
		
			if(result == 1) {
				exit = true;
			} else {
				sum[0] = result;
			}
			
			x++;
			
		}
		
		return result;
	}

	private static int gcdRecursive(int a, int b) {
		
		if(b == 0) {
			return a;
		} else {
			return gcdRecursive(b, a % b);
		}
	}

}