package org.DP.dividingcoins;

import java.util.Scanner;

public class Main {

	private Scanner sc;
	private int[][] memo;
	private int sumCoins;
	
	public static void main(String[] args) {
		new Main().run();
	}

	private Main() {
		sc = new Scanner(System.in);
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
			
			memo = new int[sumCoins + 1][coinNumber + 1];
			initialize();
			
			System.out.println(change(bag, 0, 0));
			
		}
		
	}
	
	private void initialize() {
		for(int i = 0; i < memo.length; i++) {
			for(int j = 0; j < memo[i].length; j++) {
				memo[i][j] = Integer.MAX_VALUE;
			}
		}
	}

	private int change(int[] bag, int sum, int ind) {
		
		int take, noTake;
		
		if(ind >= bag.length) {
			memo[sum][ind] = Math.abs(sumCoins - 2*sum);
			return memo[sum][ind];
		}
		
		if(sumCoins / 2.0 == sum) {
			return 0;
		}
		
		if(memo[sum + bag[ind]][ind + 1] != Integer.MAX_VALUE) {
			take = memo[sum + bag[ind]][ind + 1];
		} else {
			take = change(bag, sum + bag[ind], ind + 1);
			memo[sum][ind] = take;
		}
		
		if(memo[sum][ind + 1] != Integer.MAX_VALUE) {
			noTake = memo[sum][ind+1];
		} else {
			memo[sum][ind+1] = noTake = change(bag, sum, ind + 1);
		}
			
		return Math.min(take, noTake);
	}

}
