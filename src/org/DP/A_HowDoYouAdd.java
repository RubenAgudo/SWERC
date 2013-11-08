package org.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class A_HowDoYouAdd {

	/**
	 * Example of dynamic programming.
	 * Given an integer n hoy many ways can K non´-negative
	 * integers less thar or equal to n add up to n?
	 * 
	 * The completely recursive solution is not feasible for numbers
	 * up to 20. The dynamic programming version can compute numbers up to 
	 * n = 100 and k = 100
	 * 
	 * Example: n = 20 and K = 2. There are 21 ways:
	 * 20 + 0, 1 + 19, 2 + 18...20 + 0.
	 * 
	 * Problem extracted from COmpetitive Programming 3
	 * Copyright Steven Halim, Felix Halim
	 * @author Rubén
	 *
	 */
	private static class Reader {
	    static BufferedReader reader;
	    static StringTokenizer tokenizer;

	    /** call this method to initialize reader for InputStream */
	    static void init(InputStream input) {
	        reader = new BufferedReader(
	                     new InputStreamReader(input) );
	        tokenizer = new StringTokenizer("");
	    }

	    /** get next word */
	    static String next() throws IOException,NullPointerException {
	        while ( ! tokenizer.hasMoreTokens() ) {
	            //TODO add check for eof if necessary
	            tokenizer = new StringTokenizer(reader.readLine() );
	        }
	        return tokenizer.nextToken();
	    }

	    static int nextInt() throws IOException,NullPointerException {
	        return Integer.parseInt( next() );
	    }
		
	    static double nextDouble() throws IOException,NullPointerException {
	        return Double.parseDouble( next() );
	    }
	    
	    static long nextLong() throws IOException,NullPointerException {
	        return Long.parseLong( next() );
	    }
	}
	
	private static double[][] memo;
	
	public static void main(String[] args) {
		
		Reader.init(System.in);
		
		try {
			int cases = Reader.nextInt();
			
			for(int x = 0; x < cases; x++) {
				
				int n = Reader.nextInt();
				int k = Reader.nextInt();
				
				initialize(n, k);
				
				System.out.println(ways(n, k));
				System.out.println(ways2(n, k));
			}
			
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static void initialize(int n, int k) {
		memo = new double[n+1][k+1];
		
//		for(int x = 0; x < n+1; x++) {
//			for(int y = 0; y < k+1; y++) {
//				memo[x][y] = -1;
//			}
//		}
	}

	public static double ways(int n, int k) {
		
		int result = 0;
		
		if(k == 1) {
			return 1;
		}
		
		if(k < 1) {
			return 0;
		}
		
		for(int x = 0; x <= n; x++) {
			
			if(memo[n-x][k-1] == 0) {			
				memo[n-x][k-1] = ways(n-x, k-1);
			}
			memo[n][k] += memo[n-x][k-1];
		}

		return memo[n][k];
	}
	
	public static int ways2(int n, int k) {
		
		int result = 0;
		
		if(k == 1) {
			return 1;
		}
		
		for(int x = 0; x <= n; x++) {
			
			result += ways2(n-x, k-1);
		}
		return result;
	}
	
}
