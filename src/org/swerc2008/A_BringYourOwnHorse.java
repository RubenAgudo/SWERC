package org.swerc2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class A_BringYourOwnHorse {
	//test
	//the graph
	private static LinkedList<Integer> listaNombres;
	private static LinkedList<LinkedList<Integer[]>> grafo;
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
			grafo = new LinkedList<LinkedList<Integer[]>>();
			data = new int[roads][3];
			
			for(indRoads = 0; indRoads < roads; indRoads++) {
				
				placeFrom = Integer.parseInt(sc.next());
				placeTo = Integer.parseInt(sc.next());
				distance = Integer.parseInt(sc.next());
				
				data[indRoads] = new int[] {placeFrom, placeTo, distance};
				
			}
			
			//sorting the data to apply the kruskal algorithm
			mergeSort();
			kruskal(places);
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
		
		System.out.println("Case " + (pCase + 1));
		
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
		
		int maxDistance = 0;
		Integer[] object;
		LinkedList<Integer[]> cola = new LinkedList<Integer[]>();
		
		LinkedList<Integer> visited = new LinkedList<Integer>();
		
		int[] precedents = new int[listaNombres.size()];
		int[] distances = new int[listaNombres.size()];
		
		
		Iterator<Integer[]> itr;
		int precedent = from,
			distance = -1,
			indexOfObject;
		boolean found = false;
		
		cola.add(grafo.get(listaNombres.indexOf(from)).getFirst());
		
		while(!cola.isEmpty() && !found) {
			
			object = cola.removeFirst();
			
			indexOfObject = listaNombres.indexOf(precedent);
			
			//setting up the visited node
			visited.add(listaNombres.indexOf(object[0]));
			
			
			//inserting in the precedents list the last removed object
			precedents[indexOfObject] = indexOfObject;
			distances[indexOfObject] = distance;
			
			
			//setting up the precedent
			precedent = listaNombres.indexOf(object[0]);
			distance = object[1];
			
			if(object[0] == to) {
				found = true;
			}
			
			itr = grafo.get(indexOfObject).iterator();
			
			while(itr.hasNext()) {
				
				object = itr.next();
				
				if(!visited.contains(listaNombres.indexOf(object[0]))) {
					
					cola.add(object);
					
					
				}
				
			}
			
		}
		
		if(found) {
			maxDistance = doBackTrack(precedents, distances, to);
		}
		
		return maxDistance;
	}

	
	
	/**
	 * Method that backtracks the path from given node
	 * @param precedents
	 * @param from
	 * @return maxDistance
	 */
	private static int doBackTrack(int[] precedents, int[] distances, int from) {
		
		int	maxDistance = 0,
			precedent = from,
			distance = -1,
			indexOfObject;
		
		while(distance != 0) {
			
			distance = distances[precedent - 1];
			precedent = precedents[precedent -1];
			
			if(distance > maxDistance) {
				
				maxDistance = distance;
				
			}
			
		}
		
		return maxDistance;
	}


	/**
	 * Method that applies the kruskal algorithm to obtain a minimum spanning tree
	 * it is assumed that the data is ordered
	 * @param roads 
	 * @param places 
	 */
	private static void kruskal(int places) {
		
		int placeFrom,
			placeTo,
			distance,
			positionFrom = 0,
			positionTo = 0,
			x = 0;
		
		LinkedList<LinkedList<Integer>> disjointSets = new LinkedList<LinkedList<Integer>>();
		
		boolean exit = false,
				foundFrom = false,
				foundTo = false;
		
		while(x < data.length && !exit) {
			
			placeFrom = data[x][0];
			placeTo = data[x][1];
			distance= data[x][2];
			
			
			find(disjointSets, placeFrom, placeTo, foundFrom, foundTo, positionFrom, positionTo);
			
			if(!foundFrom) {
				
				LinkedList<Integer> newSet = new LinkedList<Integer>();
				newSet.add(placeFrom);
				disjointSets.add(newSet);
				listaNombres.add(placeFrom);
				grafo.add(new LinkedList<Integer[]>());
				
			}
			
			if(!foundTo) {
				
				disjointSets.get(positionFrom).add(placeTo);
				listaNombres.add(placeTo);
				grafo.add(new LinkedList<Integer[]>());
				
			}
			
			
			
			if((foundFrom && foundTo) && (positionFrom != positionTo)) {
				
				union(disjointSets, positionFrom, positionTo);
				
			}
			
			if(((foundFrom && foundTo) && (positionFrom != positionTo)) || !foundTo) {
				createLink(placeFrom, placeTo, distance);
			}
			
			if(disjointSets.getFirst().size() == places) {
				exit = true;
			}
						
			x++;
			
		}
		
	}

	private static void union(LinkedList<LinkedList<Integer>> disjointSets, int positionFrom, int positionTo) {
		
		Iterator<Integer> itr2 = disjointSets.get(positionTo).iterator();
		
		while(itr2.hasNext()) {
			
			Integer node = itr2.next();
			disjointSets.get(positionFrom).add(node);
			
		}
		
		disjointSets.remove(positionTo);
		
	}
	
	private static void find(LinkedList<LinkedList<Integer>> disjointSets, int placeFrom, int placeTo, 
			boolean foundFrom, boolean foundTo, int positionFrom, int positionTo) {
		
		Iterator<LinkedList<Integer>> itr = disjointSets.iterator();
		
		foundFrom = false;
		foundTo = false;
		positionFrom = 0;
		positionTo = 0;
		
		while(itr.hasNext() && (!foundFrom || !foundTo)) {
			
			LinkedList<Integer> list = itr.next();
			
			if(!foundFrom) {
				
				if(list.contains(placeFrom)) {
					
					foundFrom = true;
					
				} else {
					
					positionFrom++;
					
				}
				
			}
			
			if(!foundTo) {
				
				if(list.contains(placeTo) && !foundTo) {
					
					foundTo = true;
					
				} else {
					
					positionTo++;
					
				}
				
			}
			
		}
		
	}
	
	private static void createLink(int placeFrom, int placeTo, int distance) {
		//we add the link to the graph, as it's undirected we make the path in double direction
		grafo.get(placeFrom).add(new Integer[] {placeTo, distance});
		grafo.get(placeTo).add(new Integer[] {placeFrom, distance});
		
	}

	public static void mergeSort() {
		
		mergeSort(0, data.length-1);
	
	}

	private static void mergeSort(int inicio, int fin) {
		
		if ( inicio < fin ) { // hay m�s de un elemento en la tabla
			
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
