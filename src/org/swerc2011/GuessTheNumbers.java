package org.swerc2011;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class GuessTheNumbers {

	/**
	 * @author Rubén Agudo Santos
	 */
	
	
	private static Scanner sc;
	private static String expresion;
	private static Character[] postFijo;
	private static PrintWriter out;
	
	
	public static void main(String[] args) {
		int[] arrayNumeros;
		boolean terminar = false;
		
		try {
			sc = new Scanner(new File("F.in"));
			out = new PrintWriter(new BufferedWriter(new FileWriter("Fmio.out")));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		while(!terminar) {
		
			arrayNumeros = leerNumeros();
			
			if(arrayNumeros[0] == 0 && arrayNumeros[1] == 0) {
				
				
				terminar = true;
				
			} else {
				expresion = sc.nextLine();
				postFijo = transformacionRPN(expresion);
				
				procesarFuncion(arrayNumeros, postFijo);
				
				
				
			}
			
		}
		out.close();
	}

	private static int[] leerNumeros() {
		
		String linea = sc.nextLine();
		String[] arrayNumerosString = linea.split("\\s+");
		int[] arrayNumeros = new int[arrayNumerosString.length];
		
		int x;
		for(x = 0; x < arrayNumerosString.length; x++) {
			
			arrayNumeros[x] = Integer.parseInt(arrayNumerosString[x]);
			
		}
		
		return arrayNumeros;
	}

	/**
	 * Transforma la notacion algebraica en notacion polaca inversa
	 * @param pExpresion
	 */
	private static Character[] transformacionRPN(String pExpresion) {
		
		LinkedList<Character> list = new LinkedList<Character>(); //Lista para la expresion
		Stack<Character> simbolos = new Stack<Character>(); //Pila para los simbolos
		
		
		int pos = 0;
		
		//mientras no hayamos llegado al final de la exprsion
		while(pos < pExpresion.length()) {
			
			//si abrimos un parentesis,no hacemos nada
			if(pExpresion.charAt(pos) == '(') {
			
			//si cerramos un parentesis, añadimos un simbolo de operacion
			}else if(pExpresion.charAt(pos) == ')') {
				
				list.add(simbolos.pop());
			
			//si es un simbolo de operacion, añadimos un simbolo a la pila de simbolos
			} else if(
					pExpresion.charAt(pos) == '+' || 
					pExpresion.charAt(pos) == '-' || 
					pExpresion.charAt(pos) == '*') {
				
				simbolos.push(pExpresion.charAt(pos));
			
			//si es cualquier otra cosa (numeros) los añadimos al final de la lista
			} else {
				list.add(pExpresion.charAt(pos));
			}
			//System.out.println(list.toString());
			//finalmente avanzamos una posicion
			
			pos++;
		}
		
		//devolvemos la lista enlazada en un array
		return list.toArray(new Character[list.size()]);
	}

	
	private static void procesarFuncion(int[] arrayNumeros, Character[] expresionPostFija) {
		
		//el primer numero es el numero de incognitas, y el ultimo el resultado
		int numIncognitas = arrayNumeros[0];
		int resultado = arrayNumeros[arrayNumeros.length-1];
		
		//obtenemos el resto de numeros, que son los valores posibles de las incognitas
		LinkedList<Integer> incognitas = new LinkedList<Integer>();
		int x;
		
		for(x = 0; x < numIncognitas; x++) {
			
			incognitas.add(arrayNumeros[x+1]);
			
		}
		
		//realizamos las permutaciones
		boolean resoluble = permute(expresionPostFija, incognitas, resultado);
		
		if(resoluble) {
			System.out.println("YES");
			out.println("YES");
		} else {
			System.out.println("NO");
			out.println("NO");
		}
		
		
	}

	
	/**
	 * 
	 * @param incognitas la lista de incognitas a permutar
	 * @param k el número k-esimo de la lista
	 * @return 
	 */
//	static void permute(LinkedList<Integer> incognitas, int k, 
//			Character[] expresionPostFija, int resultado){
//
//		
//			for(int i = k; i < incognitas.size(); i++){
//	            
//	        	Collections.swap(incognitas, i, k);
//	            permute(incognitas, k+1, expresionPostFija, resultado);
//	            Collections.swap(incognitas, k, i);
//	            i++;
//	           
//	        }
//	        
//	        if (k == incognitas.size() -1){
//	        	
//	        	evaluar(expresionPostFija, incognitas, resultado);
//	            
//	        }
//	        
//	        
//	}
	
	// NOTICE:  Copyright 2008, Phillip Paul Fuchs
	private static boolean permute(Character[] postFijo, LinkedList<Integer> incognitas, int resultado ) {
		
		boolean salir = false;
		
	    salir = evaluar(postFijo, incognitas, resultado);
		
	    int n = incognitas.size();
	    int[] p = new int[n];  // Index control array initially all zeros
	    int i = 1;
	    while (i < n && !salir) {
	        if (p[i] < i) {
	            int j = ((i % 2) == 0) ? 0 : p[i];
	            swap(incognitas, i, j);
	            // Print current
	            
	            salir = evaluar(postFijo, incognitas, resultado);
	            
//	            System.out.println(join(a));
	            p[i]++;
	            i = 1;
	        }
	        else {
	            p[i] = 0;
	            i++;
	        }
	    }
	    
	    return salir;
	}

//	private static String join(char[] a) {
//	    StringBuilder builder = new StringBuilder();
//	    builder.append(a);
//	    return builder.toString();
//	}

	private static void swap(LinkedList<Integer> a, int i, int j) {
	    int temp = a.get(i);
	    a.set(i, a.get(j));
	    a.set(j, temp);
	}
    
	
	private static boolean evaluar(Character[] expresionPostFija, 
			LinkedList<Integer> incognitas, int resultado) {
		
		Stack<Integer> pila = new Stack<Integer>();
		
		int x, numActual = 0;
		
		for(x = 0; x < expresionPostFija.length; x++) {
		
			Character c = expresionPostFija[x];
			
			//Si es una operacion
			if(c == '+' || c == '-' || c == '*') {
				switch(c) {
				case '+':
					pila.push(pila.pop()+pila.pop());
					break;
				case '-':
					//el caso de la resta es un caso particular sacamos los dos numeros pero se restan al reves
					int valor1 = pila.pop();
					int valor2 = pila.pop();
					pila.push(valor2-valor1);
					break;
				default:
					pila.push(pila.pop()*pila.pop());
					break;
				}
				
			//si es un numero lo metemos en la pila
			} else {
				
				pila.push(incognitas.get(numActual));
				numActual++;
			}
			
			
		}
		return (pila.pop() == resultado);
		
	}
}
