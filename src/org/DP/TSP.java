package org.DP;

public class TSP {

	public static void main(String[] args) {
		
		int x = 32; //0010 0000
		System.out.println(x);
		x &= (1<<3); //0010 0000 AND 1000 = 0 comprueba si el cuarto bit esta encendido
		x |= (1<<3); //0010 0000 OR 1000 = 0010 1000 = 40 enciende el cuarto bit
		x ^= (1<<3); //0010 0000 XOR 1000 = 0010 1000 = 40 cambia el estado del cuarto bit
		x &= ~(1<<3); //Pone a 0 el cuarto bit
		x =(x & (-x)); //devuelve el primer bit que esta puesto a 1
		//x = (1<<n)-1; //pone todos los bits a 1
		System.out.println(x);
		
	}

}
