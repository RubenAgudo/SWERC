package org.swerc2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class CPostalCharges {
	
	/**
	 * @author Rubén Agudo, David Ramirez, Javier Fernandez
	 */
	
	
	private static LinkedList<LinkedList<float[]>> grafo;
	private static LinkedList<float[]> listaNombres;
	private static Scanner sc;
	private static int casas;
	
	
	public static void main(String[] args) {
		
		
		
		try {
			sc = new Scanner(new File(args[0]));
			
			while (sc.hasNext()) {
				
				grafo = new LinkedList<LinkedList<float[]>>();
				listaNombres = new LinkedList<float[]>();
				
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
		float[] coordenadaBase, coordenadaActual;
		
		Iterator<LinkedList<float[]>> itr2 = grafo.iterator();
		
		
		while( itr2.hasNext() ) {
			
			coordenadaBase = listaNombres.get(ind);
			Iterator<float[]> itr =  itr2.next().iterator();
			
			while(itr.hasNext()) {
				
				coordenadaActual = itr.next();
				
				suma+= Math.abs(coordenadaActual[0]-coordenadaBase[0]) + Math.abs(coordenadaActual[1]-coordenadaBase[1]);
				caminosRecorridos++;
				
			}
			
			ind++;
			
		}
		
		System.out.println(suma / caminosRecorridos);
		
	}


	private static void crearEnlaces() {
		
		int ind = 0;
		float[] gremioActual, gremio, paraAnadir;
		
		
		Iterator<float[]> itr1 = listaNombres.iterator();
		
		
		while(itr1.hasNext()) {
			
			gremioActual = itr1.next();
			
			Iterator<float[]> itr2 = listaNombres.iterator();
			
			while(itr2.hasNext()) {
				
				gremio = itr2.next();
				paraAnadir = new float[] {gremio[0], gremio[1]};
				
				if(gremio[0] > gremioActual[0] && 
						gremio[1] > gremioActual[1]) {
					
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
		
		
		grafo = new LinkedList<LinkedList<float[]>>();
		
		while( ind < casas ) {
			
			x = Float.parseFloat(sc.next());
			y = Float.parseFloat(sc.next());
			
			float[] coordenada = new float[] {x,y};
			
			if(!listaNombres.contains(coordenada)) {
				
				listaNombres.add(coordenada);
				grafo.add(new LinkedList<float[]>());
				
			}
			
			ind++;
			
		}
		
	}
	

}
