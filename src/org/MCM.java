package org;

public class MCM {

	public static void main(String[] args) {
		MCM m1 = new MCM();
		
		System.out.println(m1.mcm(5, 15));
		System.out.println(m1.mcm(588, 114));
		
		
	}

	public MCM() {}
	
	private int mcm(int a, int b) {
		int resultado;
		int a2 = a;
		int b2 = b;
		while(a != b) {
			if(a > b) {
				a -= b;
			} else {
				b -= a;
			}
		}
		resultado = a2 * b2 / b;
		return resultado;
	}
}
