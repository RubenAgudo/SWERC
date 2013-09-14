package org.swerc2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class A_BringYourOwnHorse {

	//the graph
	private static ArrayList<LinkedList<Integer[]>> grafo;
	private static ArrayList<Integer> listaNombres;
	private static int[][] data;
	
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
		
		int cases = sc.nextInt(),
			places, 
			roads, 
			placeFrom, 
			placeTo, 
			distance,
			indCases = 0,
			indRoads = 0;
			
		while(indCases < cases) {
			
			places = Integer.parseInt(sc.next());
			roads = Integer.parseInt(sc.next());
			
			listaNombres = new ArrayList<Integer>(places);
			grafo = new ArrayList<LinkedList<Integer[]>>();
			data = new int[roads][3];
			
			for(indRoads = 0; indRoads < roads; indRoads++) {
				
				placeFrom = Integer.parseInt(sc.next());
				placeTo = Integer.parseInt(sc.next());
				distance = Integer.parseInt(sc.next());
				
				data[indRoads] = new int[] {placeFrom, placeTo, distance};
				
			}
			
			//sorting the data to apply the kruskal algorithm
			mergeSort();
			kruskal();
			evaluateAndPrint(indCases);
			
			indCases++;
		}
		
	}
	
	private static void evaluateAndPrint(int pCase) {
		//evaluations
		int evaluations,
			placeFrom,
			placeTo,
			maxDistance;
		
		evaluations = Integer.parseInt(sc.next());
		
		System.out.println("Case " + pCase);
		
		for(int y = 0; y < evaluations; y++) {
			
			placeFrom = Integer.parseInt(sc.next());
			placeTo = Integer.parseInt(sc.next());
			maxDistance = breadthFirstSearch(placeFrom, placeTo);
			
			System.out.println(maxDistance);
			
		}
		
		System.out.println();
		
	}

	/**
	 * Breadth-First search on the graph
	 * @param from
	 * @param To
	 * @return
	 */
	private static int breadthFirstSearch(int from, int to) {
		
		int maxDistance = 0,
			distance;
		Integer[] object;
		LinkedList<Integer[]> cola = new LinkedList<Integer[]>();
		LinkedList<Integer[]> precedente = new LinkedList<Integer[]>();
		Iterator<Integer[]> itr;
		boolean found = false;
		
		cola.add(new Integer[]{from, 0});
		precedente.add(new Integer[] {0, -1});
		
		while(!cola.isEmpty() && !found) {
			
			object = cola.removeFirst();
			distance = object[1];
			
			if(distance > maxDistance) {
				
				maxDistance = distance;
			
			}
			
			if(object[0] == to) {
				found = true;
			}
			
			itr = grafo.get(listaNombres.indexOf(object[0])).iterator();
			
			while(itr.hasNext()) {
				
				object = itr.next();
				cola.add(object);
				
			}
			
		}
		
		return maxDistance;
	}

	/**
	 * Method that applies the kruskal algorithm to obtain a minimum spanning tree
	 * it is assumed that the data is ordered
	 */
	private static void kruskal() {
		
		int placeFrom,
			placeTo,
			distance;			
		
		for(int x = 0; x < data.length; x++) {
			
			placeFrom = data[x][0];
			placeTo = data[x][1];
			distance= data[x][2];
			
			
			if(!listaNombres.contains(placeFrom)) {
				
				listaNombres.add(placeFrom);
				grafo.add(new LinkedList<Integer[]>());
				
			}
			
			/*
			 * if placeTo is not in the list means that we are
			 * not creating a cycle
			 */
			if(!listaNombres.contains(placeTo)) {
				
				listaNombres.add(placeTo);				
				
				grafo.add(new LinkedList<Integer[]>());
				
				//we add the link to the graph, as it's undirected we make the path in double direction
				grafo.get(listaNombres.indexOf(placeFrom)).add(new Integer[] 
						{placeTo, distance});
				
				grafo.get(listaNombres.indexOf(placeTo)).add(new Integer[] 
						{placeFrom, distance});
				
			}
			
		}
		
	}

	public static void mergeSort() {
		
		mergeSort(0, data.length-1);
	
	}

	private static void mergeSort(int inicio, int fin) {
		
		if ( inicio < fin ) { // hay más de un elemento en la tabla
			
			mergeSort(inicio, (inicio + fin) / 2);
			mergeSort(((inicio + fin) / 2) + 1, fin);
			merge(inicio, (inicio + fin) / 2, fin);
		
		}
	}
	
	private static void merge(int low, int middle, int high) {
		
		int[][] helper = new int[data.length][data[1].length];
				
	    // Copy both parts into the helper array
	    for (int i = low; i <= high; i++) {
	      helper[i] = data[i];
	    }

	    int i = low;
	    int j = middle + 1;
	    int k = low;
	    
	    // Copy the smallest values from either the left or the right side back
	    // to the original array
	    while (i <= middle && j <= high) {
	    	
	    	if (helper[i][2] <= helper[j][2]) {
	        
	    	  data[k] = helper[i];
	    	  i++;
	    	} else {
	    	  data[k] = helper[j];
	    	  j++;
	    	}
	    	k++;
	    }
	    
	    // Copy the rest of the left side of the array into the target array
	    while (i <= middle) {
	    	data[k] = helper[i];
	    	k++;
	    	i++;
	    }
	    
	}

}
