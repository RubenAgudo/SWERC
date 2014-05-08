package org.training;

import java.util.Scanner;

public class Defense {

	private static boolean[] x, y;
	private static Scanner sc;
	private static int h,w,n;
	
	public static void main(String[] args) {
		
		sc = new Scanner(System.in);
		
		w = sc.nextInt();
		h = sc.nextInt();
		n = sc.nextInt();

		x = new boolean[w+1];
		y = new boolean[h+1];
		
		for(int ind = 0; ind < n; ind++) {
			
			x[sc.nextInt()] = true;
			y[sc.nextInt()] = true;
			
		}
		int xMax = obtenerMax(x);
		int yMax = obtenerMax(y);
		
		System.out.println(xMax * yMax);		
		
	}
	
	private static int obtenerMax(boolean[] data) {
		int max = 0;
		int globalMax = 0;
		for(int ind = 1; ind < data.length; ind++) {
			
			if(data[ind] == false) {
				max++;
			} else {
				if(max > globalMax) {
					globalMax = max;
					max = 0;
				}
			}
		}
		return Math.max(max, globalMax);
	}

}