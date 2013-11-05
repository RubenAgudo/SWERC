package org.sic2013;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class A_BoiledEggs {

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
	
	
	private static int[] W;
	private static int[][] val;
	
	public static void main(String[] args) {
		Reader.init(System.in);
		
		try {
			int numCasos = Reader.nextInt();
			for(int i=0;i<numCasos;i++)
			{
				int n = Reader.nextInt();
				int p = Reader.nextInt();
				int q = Reader.nextInt();
				
				W = new int[n];
				val = new int[n+1][q+1];
				for(int j = 0;j<n;j++)
				{
					W[j] = Reader.nextInt();
				}
				
				int result = knapsack(q, p);
				
				System.out.println(result);
			}

			
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	private static int knapsack(int pQ, int pEggs) {
		
		int remW = pQ; //remaining weight that can be put in the bowl
		return val(0, remW, pEggs);
	}
	
	private static int val(int id, int remW, int iterations) {
		int result = 0;
		
		
		if(iterations > 0) {
			
			if(remW == 0) {
				return 0;
			}
			
			if(id == W.length) {
				return 0;
			}
			
			if(W[id] > remW) {
				
				if(val[id+1][remW] == 0) {
					val[id][remW] = val(id+1, remW, iterations);
				} else {
					val[id][remW] = val[id+1][remW];
				}
			}
			
			if(W[id] <= remW) {
//				int x = val(id+1, remW, iterations);
//				int y = W[id] + val(id+1, remW - W[id], iterations -1);
				
				if(val[id+1][remW] == 0) {
					val[id][remW] = val(id+1, remW, iterations);
				}
				
				
				if(val[id+1][remW - W[id]] == 0) {
					val[id][remW - W[id]] = W[id] + val(id+1, remW - W[id], iterations -1);
				} else {
					iterations--;
				}
				return Math.max(val[id][remW], val[id][remW - W[id]]);
			}
		}
		
		return result;
	}

}
