package org.swerc2011;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

public class NonNegativePartialSums {

	private static TreeSet<String> ciclosRealizados;
	private static PrintWriter out;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ciclosRealizados = new TreeSet<String>();
		
		try {
			//instanciamos el Scanner que leera desde el fichero de entrada
			Scanner sc = new Scanner(new File("sample.in"));
			out = new PrintWriter(new BufferedWriter(new FileWriter("salida.out")));
			
			//creamos la variable
			LinkedList<Integer> listNumeros;
			int resultado = 0;
			
			/*
			 * creamos una variable "n" que sera la cantidad de numeros y 
			 * "numeros" el array con los numeros en si
			 */
			int n = sc.nextInt();
			
			/*
			 * Mientras "n" no sea cero, no hemos terminado de ejecutar, asi que
			 * recogemos los numeros
			 */
			while( n != 0 ) {
				listNumeros = new LinkedList<Integer>();
				
				//recogemos los datos
				recogerDatos(sc, listNumeros, n);
				
				//realizamos la primera rotacion circular
				resultado = realizarRotacionYEvaluar(listNumeros, n);
				
				escribirResultados(resultado);
				
				n = sc.nextInt();
				
			}
			
			sc.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Inserta en listNumeros los numeros que hay en el String numeros
	 * @param n
	 * @param sc
	 * @param listNumeros
	 */
	private static void recogerDatos(Scanner sc, LinkedList<Integer> listNumeros, int n) {		
		
		int x;
		
		for(x = 0; x < n; x++) {
			
			listNumeros.add(sc.nextInt());
			
		}
		
	}
	
	/**
	 * Realiza todas las posibles rotaciones circulares y comprueba las sumas parciales
	 * @param listNumeros
	 */
	private static int realizarRotacionYEvaluar(LinkedList<Integer> listNumeros, int n) {
		
		int rotacionesRealizadas = 0;
		int rotacionesMaximas = listNumeros.size();
		int resultado = 0;
		
		//metemos en el arbol la lista de numeros inicial
		ciclosRealizados.add(listNumeros.toString());
		
		while(rotacionesRealizadas < rotacionesMaximas) {
			
			//si la rotacion actual ya esta en el arbol, ni la miramos, el resultado nos da igual
			//ya que una rotacion circular no contempla las repeticiones
			if(ciclosRealizados.contains(listNumeros.toString())) {
			
				//Calculamos las sumas parciales
				resultado += sumasParciales(listNumeros, n);
				
			}
			//Realizamos una rotacion
			rotar(listNumeros, rotacionesRealizadas);
			
			//aumentamos el contador
			rotacionesRealizadas++;
		}
		
		return resultado;
	}
	
	/**
	 * Devuelve 1 si todas las sumas parciales son positivas, 0 en caso contrario
	 * @param listNumeros
	 * @param n
	 * @return
	 */
	private static int sumasParciales(LinkedList<Integer> listNumeros, int n) {
		int i = 1;
		boolean salir = false;
		int resultado = 0;
		
		while(i <= n && !salir) {
			
			salir = sumaParcialNegativa(listNumeros, i);
			i++;
		}
		
		if(!salir) {
			resultado = 1;
		}
		return resultado;
	}

	/**
	 * Calcula si la suma parcial es negativa
	 * @param listNumeros
	 * @param i
	 * @return Devuelve true si la suma parcial es negativa, false en caso contrario
	 */
	private static boolean sumaParcialNegativa(LinkedList<Integer> listNumeros,
			int i) {
		int x;
		int sumaParcial = 0;
		
		for(x = 0; x < i; x++) {
			sumaParcial += listNumeros.get(x); 
		}

		return (sumaParcial < 0);
	}
	

	

	/**
	 * Genera una rotacion circular y añade la rotacion al arbol
	 * @param listNumeros
	 * @param posActual
	 */
	private static void rotar(LinkedList<Integer> listNumeros,
			int posActual) {
		
		if(listNumeros.size() > 1 ) {
			int aux = listNumeros.get(posActual);
			listNumeros.set(posActual, listNumeros.get(posActual+1));
			listNumeros.set(posActual + 1, aux);
			ciclosRealizados.add(listNumeros.toString());
		}
		
		
	}


	/**
	 * Escribe los resultados por pantalla y a un fichero de salida para poder comparar
	 * @param resultado
	 * @throws IOException 
	 */
	private static void escribirResultados(int resultado) throws IOException {
		
		System.out.println(resultado);
		out.println(resultado);
		
	}

	

}
