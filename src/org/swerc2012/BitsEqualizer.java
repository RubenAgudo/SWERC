package org.swerc2012;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BitsEqualizer {
	
	/**
	 * @author Rubén Agudo Santos <ragudo001@ikasle.ehu.es>
	 * Metodo que calcula si un String S compuesto por 0, 1, y ? puede transformarse en otro String T compuesto
	 * de 0 y 1 en los minimos movimientos posibles.
	 * 
	 * Cada movimiento puede ser:
	 * 	<ul>
	 * 		<li>Cambiar un '0' en S a '1'</li>
	 * 		<li>Cambiar un '?' en S a '0' o '1'</li>
	 * 		<li>Intercambiar dos elementos en S</li>
	 * 	</ul>
	 * @param args Se le pasa un fichero de texto como entrada a traves de los argumentos.
	 * 		La primera linea contiene un número C(C<=200) que indica el numero de casos. Cada caso consiste en dos
	 * 		lineas S y T. La primera contiene '0', '1' y '?', y la segunda solo '0' y '1'
	 * 
	 * @out El numero de pasos para convertir S en T
	 */
	public static void main(String[] args) {
		//los dos Strings que tienen que ser iguales
		char[] stringS, stringT;
		int cerosS, cerosT;
		int numTests, operaciones = 0;
		int x = 0;
		
		//leemos el fichero
		//File fichero = new File(args[0]);
		File fichero = new File("D://SkyDrive//Documentos//UPV-EHU//SWERC//TestCases//testcases//B//test.2.in");
		Scanner sc;
		
		try {
			sc = new Scanner(fichero);
			
			//sabemos que la primera linea es un integer
			numTests = sc.nextInt();
			
			for(x = 0; x < numTests; x++) {
				
				//reseteamos la variable
				operaciones = 0;
				
				//obtenemos las dos lineas de bits
				stringS = sc.next().toCharArray();
				stringT = sc.next().toCharArray();
				
				//contamos el numero de ceros
				cerosS = contarCeros(stringS);
				cerosT = contarCeros(stringT);
				
				//Si S tiene menos ceros que T no se puede hacer
				if(cerosS  < cerosT) {
					operaciones = -1;
				} else {
					//cuantos ceros y ? tenemos que cambiar a 1
					int cerosACambiar = cerosS - cerosT;
					
					operaciones += ponerCerosAUno(cerosACambiar, stringS, stringT);
					
					operaciones += intercambiar(stringS, stringT);
					
					
					
				}
				System.out.println("Case " + x + ": " + operaciones);
			}
			
			sc.close(); //cerramos el lector
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	private static int intercambiar(char[] pStringS, char[] pStringT) {
		int x, y = 0, operaciones = 0;
		boolean sustitutoEncontrado = false;
		
		for(x = 0; x < pStringT.length; x++) {
			
			//if(pStringS[x] != '?') {
			
				//si el caracter de la posicion actual son distintos, buscamos sustituto
				if(pStringS[x] != pStringT[x]) {
					
					y = x+1;
					sustitutoEncontrado = false;
					
					while(y < pStringS.length && !sustitutoEncontrado) {
						
						if(pStringS[y] != pStringT[y]) {
							char aux = pStringS[x];
							pStringS[x] = pStringS[y];
							pStringS[y] = aux;
							operaciones++;
							sustitutoEncontrado = true;
						}
						y++;
					}
					
				//}
			}
			
				
		}
		
		return operaciones;
		
	}

	private static int ponerCerosAUno(int cerosACambiar, char[] pStringS, char[] pStringT) {
		int x = 0, y = 0, operaciones = 0;
		
			
		while(x < cerosACambiar) {
			
			if(y < pStringS.length) {
				
				if((pStringS[y] == '0' || pStringS[y] == '?') && (pStringT[y] == '1')) {
					pStringS[y] = '1';
					operaciones++;
					x++;
				}
			y++;	
			} else {
				y = 0;
			}
			
		}
		
		y = 0;
		while(y < pStringS.length) {
			
				
			if(pStringS[y] == '?') {
				pStringS[y] = '0';
				operaciones++;
			}
			
			y++;
		}	
		
		
		
		return operaciones;
		
	}

	private static int contarCeros(char[] pCadena) {
		
		int longitud = pCadena.length;
		
		int x, resultado = 0;
		
		for(x = 0; x < longitud; x++) {
			
			if(pCadena[x] == '0' || pCadena[x] == '?') {
				resultado++;
			}
			
		}
		
		return resultado;
		
	}
}
