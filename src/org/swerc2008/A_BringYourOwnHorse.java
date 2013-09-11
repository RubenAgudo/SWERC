package org.swerc2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class A_BringYourOwnHorse {

	//the graph
	private static LinkedList<Integer>[] grafo;
	private static ArrayList<Integer> listaNombres;
	
	private static Scanner sc;
	
	public static void main(String[] args) {
		
		try {
			
			
			readFromFileAndProcess(args[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method that reads from the file specified by the attribute and does the processing
	 * @param path The path of the file to read
	 * @throws FileNotFoundException 
	 */
	private static void readFromFileAndProcess(String path) 
			throws FileNotFoundException {
		
		sc = new Scanner(new File(path));
		int cases = sc.nextInt();
		int places, 
			roads, 
			placeFrom, 
			placeTo, 
			distance;
		int indCases = 0,
			indRoads = 0;
		
		while(indCases < cases) {
			
			places = Integer.parseInt(sc.next());
			roads = Integer.parseInt(sc.next());
			
			listaNombres = new ArrayList<Integer>(places);
			
			for(indRoads = 0; indRoads < roads; indRoads++) {
				
				placeFrom = Integer.parseInt(sc.next());
				placeTo = Integer.parseInt(sc.next());
				distance = Integer.parseInt(sc.next());
				
				if(!listaNombres.contains(placeFrom)) {
					listaNombres.add(placeFrom);
				}
				
				if(!listaNombres.contains(placeTo)) {
					listaNombres.add(placeTo);
				}
				
			}
			
			
			
			
			
			
			indCases++;
		}
		
	}

}
