package org.DP;

import java.util.LinkedList;
import java.util.Scanner;

public class DividingCoins {

	private Scanner sc;
	private int[] memo;
	private int sumCoins;
	
	public static void main(String[] args) {
		new DividingCoins().run();
	}

	private DividingCoins() {
		sc = new Scanner(System.in);
		//memo = new int[Integer.MAX_VALUE];
	}
	
	private void run() {
		
		int cases = sc.nextInt();
		
		for(int i = 0; i < cases; i++) {
			
			int coinNumber = sc.nextInt();
			
			int[] bag = new int[coinNumber];
			sumCoins = 0;
			
			for(int j = 0; j < coinNumber; j++) {
				
				int coin = sc.nextInt();
				bag[j] = coin;
				
				sumCoins += coin;
				
			}
			
			System.out.println(change(bag, 0, 0));
			
		}
		
	}
	
	private double change(int[] bag, int sum, int ind) {
		
		if(ind >= bag.length) {
			if(sumCoins % 2 != 0) {
				return sum;
			} else {				
				return Math.abs((sumCoins / 2) - sum);
			}
		}
		
		if(sumCoins / 2.0 == sum) {
			return 0;
		}
		
		return Math.min(change(bag, sum + bag[ind], ind + 1), change(bag, sum, ind + 1));
	}

}
