package org.DP.coinchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	private int[][] memo;
	private BufferedReader br;
	private int[] types;
	
	public static void main(String[] args) {
		new Main().run();
	}
	
	private Main() {
		br = new BufferedReader(new InputStreamReader(System.in));
		types = new int[] {1, 5, 10, 25, 50};
		memo = new int[7489 + 1][types.length];
		
		for(int i = 0; i < memo.length; i++) {
			for(int j = 0; j < memo[0].length; j++) {
				memo[i][j] = -1;
			}
		}
	}

	private void run() {
		
		try {
			String line;
			while((line = br.readLine()) != null) {
				int number = Integer.parseInt(line);
				
				System.out.println(change(number, 0));
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

	private int change(int number, int ind) {
		
		if(ind >= types.length || number < 0) {
			return 0;
		}
		
		if(number == 0) {
			return 1;
		}
		
		if(memo[number][ind] == -1) {
			return memo[number][ind] = 
					change(number - types[ind], ind) + 
					change(number, ind + 1); 
		} else {
			return memo[number][ind];
		}
	}

}
