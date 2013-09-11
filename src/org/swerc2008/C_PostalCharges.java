package org.swerc2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class C_PostalCharges {
	
	/**
	 * @author Rubén Agudo, David Ramirez, Javier Fernandez
	 */
	
	//creating a graph using adjadency lists of BigDecimals
	private static LinkedList<LinkedList<BigDecimal[]>> grafo;
	private static LinkedList<BigDecimal[]> listaNombres;
	
	private static Scanner sc;
	
	
	public static void main(String[] args) {
		
		
		try {
			//reading from the arguments
			sc = new Scanner(new File(args[0]));
			
			while (sc.hasNext()) {
				
				//instantiating the graph for every execution
				grafo = new LinkedList<LinkedList<BigDecimal[]>>();
				listaNombres = new LinkedList<BigDecimal[]>();
				
				//do the processing
				rellenarListaNombres();
				crearEnlaces();
				calcularEImprimir();
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * This method calculates the mean distance between guilds.
	 * 
	 */
	private static void calcularEImprimir() {
		
		
		int ind = 0;
		int caminosRecorridos = 0 ;
		float suma = 0;
		BigDecimal[] coordenadaBase, coordenadaActual;
		
		Iterator<LinkedList<BigDecimal[]>> itr2 = grafo.iterator();
		
		//for every node of the graph
		while( itr2.hasNext() ) {
			
			//we get the coordinates of the guild
			coordenadaBase = listaNombres.get(ind);
			Iterator<BigDecimal[]> itr =  itr2.next().iterator();
			
			while(itr.hasNext()) {
				
				coordenadaActual = itr.next();
				
				/*
				 * Manhattan distance for two point (x1,y1) and (x2,y2) is
				 * |x1-x2| + |y1-y2|
				 */
				suma+= Math.abs(coordenadaActual[0].subtract(coordenadaBase[0]).doubleValue()) +
						Math.abs(coordenadaActual[1].subtract(coordenadaBase[1]).doubleValue()); 
						
				caminosRecorridos++;
				
			}
			
			ind++;
			
		}
		
		System.out.println(suma / caminosRecorridos);
		
	}

	/**
	 * This method creates the links between nodes in the graph
	 */
	private static void crearEnlaces() {
		
		int ind = 0;
		BigDecimal[] gremioActual, gremio, paraAnadir;
		
		
		Iterator<BigDecimal[]> itr1 = listaNombres.iterator();
		
		
		while(itr1.hasNext()) {
			
			gremioActual = itr1.next();
			
			Iterator<BigDecimal[]> itr2 = listaNombres.iterator();
			
			while(itr2.hasNext()) {
				
				gremio = itr2.next();
				paraAnadir = new BigDecimal[2];
				paraAnadir[0] = gremio[0];
				paraAnadir[1] = gremio[1];
				
				if(gremio[0].intValue() > gremioActual[0].intValue() && 
						gremio[1].intValue() > gremioActual[1].intValue()) {
					
					grafo.get(ind).add(paraAnadir);
					
				}
				
			}
			
			ind++;
			
		}
		
	}


	/**
	 * Method that creates the list with the guilds
	 * @throws FileNotFoundException
	 */
	private static void rellenarListaNombres() throws FileNotFoundException {
		
		
		int casas = sc.nextInt();
		int ind = 0;
		String x, y;
		
		
		grafo = new LinkedList<LinkedList<BigDecimal[]>>();
		
		while( ind < casas ) {
			
			x = sc.next();
			y = sc.next();
			
			BigDecimal[] coordenada = new BigDecimal[] {
					new BigDecimal(x), new BigDecimal(y)};
			
			if(!listaNombres.contains(coordenada)) {
				
				listaNombres.add(coordenada);
				grafo.add(new LinkedList<BigDecimal[]>());
				
			}
			
			ind++;
			
		}
		
	}
	
	

}
