package org.swerc2010;

public class Pair implements Comparable<Pair> {
	
	private double x;
	private double y;
	
	public Pair(double x2, double y2) {
		x = x2;
		y = y2;
	}
	
	@Override public String toString() {
		return String.format("(" + x + ", " + y + ")" );
	}

	@Override
	public int compareTo(Pair pOtherPair) {
		
		int result = 0;
		
		if(x > pOtherPair.x) {
			result = 1;
		} else if(x < pOtherPair.x) {
			result = -1;
		}
		return result;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
}