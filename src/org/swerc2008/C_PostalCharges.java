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
	
	
	private static LinkedList<LinkedList<BigDecimal[]>> grafo;
	private static LinkedList<BigDecimal[]> listaNombres;
	private static Scanner sc;
	private static int casas;
	
	
	public static void main(String[] args) {
		
		
		
		try {
			sc = new Scanner(new File(args[0]));
			
			while (sc.hasNext()) {
				
				grafo = new LinkedList<LinkedList<BigDecimal[]>>();
				listaNombres = new LinkedList<BigDecimal[]>();
				
				rellenarListaNombres();
				crearEnlaces();
				calcularEImprimir();
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void calcularEImprimir() {
		
		
		int ind = 0;
		int caminosRecorridos = 0 ;
		float suma = 0;
		BigDecimal[] coordenadaBase, coordenadaActual;
		
		Iterator<LinkedList<BigDecimal[]>> itr2 = grafo.iterator();
		
		
		while( itr2.hasNext() ) {
			
			coordenadaBase = listaNombres.get(ind);
			Iterator<BigDecimal[]> itr =  itr2.next().iterator();
			
			while(itr.hasNext()) {
				
				coordenadaActual = itr.next();
				
				suma+= Math.abs(coordenadaActual[0].doubleValue() - coordenadaBase[0].doubleValue()) + 
						Math.abs(coordenadaActual[1].doubleValue() - coordenadaBase[1].doubleValue());
				caminosRecorridos++;
				
			}
			
			ind++;
			
		}
		
		System.out.println(suma / caminosRecorridos);
		
	}


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


	private static void rellenarListaNombres() throws FileNotFoundException {
		
		
		casas = sc.nextInt();
		int ind = 0;
		float x, y;
		
		
		grafo = new LinkedList<LinkedList<BigDecimal[]>>();
		
		while( ind < casas ) {
			
			x = Float.parseFloat(sc.next());
			y = Float.parseFloat(sc.next());
			
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
