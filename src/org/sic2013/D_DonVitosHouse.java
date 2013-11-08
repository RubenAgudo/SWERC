package org.sic2013;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class D_DonVitosHouse {

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
	
	
	private static PriorityQueue<Integer> neighbours;
	private static int numNeighbours;
	
	public static void main(String[] args) {
		
		Reader.init(System.in);
		
		try {
			int cases = Reader.nextInt();
			
			for(int x = 0; x < cases; x++) {
				numNeighbours = 0;
				load();
				System.out.println(process());
				
			}
			
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void load() throws NullPointerException, IOException {
		neighbours = new PriorityQueue<Integer>(10000000);
		
		Scanner sc = new Scanner(Reader.reader.readLine());
		
		while(sc.hasNext()) {
			int neighbour = sc.nextInt();
			
			//adding only the distinct streets numbers
			if(!neighbours.contains(neighbour)) {
				neighbours.add(neighbour);
				numNeighbours++;
			}
			
		}
		
		sc.close();
		
	}

	private static int distance(int a, int b) {
		return Math.abs(a-b);
	}
	
	private static long process() {
		
		Integer[] dummy = new Integer[numNeighbours];
		int x = 0;
		while(!neighbours.isEmpty()) {
			dummy[x] = neighbours.poll();
			x++;
		}
		
		int median = dummy.length / 2;
		
		long distance = Math.min(calculateDistance(median-1, dummy), calculateDistance(median, dummy));
//		System.out.println(calculateDistance2(dummy));
		return distance;
	}

	private static long calculateDistance(int i, Integer[] dummy) {
		
		long sum = 0;
		
		for(int x = 0; x < numNeighbours; x++) {
			sum += distance(dummy[i], dummy[x]);
		}
		return sum;
	}
	
//	private static long calculateDistance2(Integer[] dummy) {
//		
//		long sum = 0;
//		long min = 1000000000;
//		for(int x = 0; x < dummy.length; x++) {
//			sum = 0;
//			for(int i = 0; i < dummy.length; i++) {
//				sum += distance(dummy[i], dummy[x]);				
//			}
//			if(min>sum) {
//				min = sum;
//			}
//		}
//		return min;
//	}
}
