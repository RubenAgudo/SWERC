package org.swerc2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class A_BYOH {

	private static class Vertex {
		private Vertex parent;
		private int rank;
		private int name;
	
		public Vertex(int pName) {
			name = pName;
		}
		
		@Override public boolean equals(Object o) {
			return (o instanceof Vertex) && (name == ((Vertex) o).name);
		}
		
		@Override public String toString() {
			return String.format("" + name);
		}
	}
	
	private static class Road {
		
		
		private Vertex fromTown;
		private Vertex toTown;
		private int distance;
		
		public Road(Vertex pPlaceFrom, Vertex pPlaceTo, int pDistance) {
			fromTown = pPlaceFrom;
			toTown = pPlaceTo;
			distance = pDistance;
		}
		
		@Override public boolean equals(Object o) {
            return (o instanceof Road) && (fromTown == ((Road) o).fromTown && 
					toTown == ((Road) o).toTown &&
					distance == ((Road) o).distance);
        }
		
		@Override public String toString() {
			return String.format("[" + fromTown + ", " + toTown + ", " + distance + "]");
		}
		
		
	}

	private static Map<Vertex, LinkedList<Road>> graph;
	private static ArrayList<Vertex> vertexList;
	private static Road[] data;
	private static Scanner sc;
	
	public static void main(String[] args) {
		
		try {
			readFromFile(args[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static void readFromFile(String path) throws FileNotFoundException {
		
		sc = new Scanner(new File(path));
		
		int cases = sc.nextInt(),
			places, 
			roads,
			distance,
			indCases = 0,
			indRoads = 0;
		
		Vertex placeFrom,
				placeTo;
		
		
		
		while(indCases < cases) {
			
			places = Integer.parseInt(sc.next());
			roads = Integer.parseInt(sc.next());
			
			data = new Road[roads];
			vertexList = new ArrayList<Vertex>(places);
			graph = new HashMap<Vertex, LinkedList<Road>>();
			
			for(indRoads = 0; indRoads < roads; indRoads++) {
				
				placeFrom = new Vertex(Integer.parseInt(sc.next()));
				placeTo = new Vertex(Integer.parseInt(sc.next()));
				distance = Integer.parseInt(sc.next());
				
				data[indRoads] = new Road(placeFrom, placeTo, distance);
				
			}
			
			//sorting the data to apply the kruskal algorithm
			mergeSort();
			
			//applying the kruskal general algorithm using disjointed data sets
			kruskal();
			evaluateAndPrint(indCases);
			
			indCases++;
		}
		
	}

	private static void evaluateAndPrint(int pIndCases) {
		
		int from, to, tests, result;
		
		System.out.println("Case " + (pIndCases + 1));
		
		tests = Integer.parseInt(sc.next());
		
		for(int x = 0; x < tests; x++) {
			
			from = Integer.parseInt(sc.next());
			to = Integer.parseInt(sc.next());
			
			result = dfs(from, to);
			
			System.out.println(result);
			
		}
		
	}
	
	private static int dfs(int pFrom, int pTo) {
		
		int maxDistance = 0;
		boolean found = false;
		
		Vertex from = new Vertex(pFrom);
		Vertex to = new Vertex(pTo);
		Road aRoad;
		
		LinkedList<Vertex> stack = new LinkedList<Vertex>();
		LinkedList<Vertex> visited = new LinkedList<Vertex>();
		Map<Vertex, Integer> maxDistanceToVertex = new HashMap<Vertex, Integer>();
		
		Iterator<Road> itr;
		
		stack.addFirst(from);
		
		while(!stack.isEmpty() && !found){
			
			from  = stack.pop();
			
			
			visited.add(from);
			
			if(from.equals(to)) {
				found = true;
				maxDistance = maxDistanceToVertex.get(from);
			} else {
				itr = graph.get(from).iterator();
				
				while(itr.hasNext()) {
					
					aRoad = itr.next();
					
					if(!visited.contains(aRoad.toTown)) {
						
						stack.add(aRoad.toTown);
						
						if(maxDistanceToVertex.get(from) < aRoad.distance ) {
							maxDistanceToVertex.put(aRoad.toTown, aRoad.distance);
						} else {
							maxDistanceToVertex.put(aRoad.toTown, maxDistanceToVertex.get(from));
						}
					}
					
				}
				
			}
			
			
			
			
		}
		
		return maxDistance;
	}

	private static Vertex find(Vertex pVertex) {
	    if(!pVertex.parent.equals(pVertex)) {
	    	pVertex.parent = find(pVertex.parent);
	    }
	       
	    return pVertex.parent;
	}
	
	private static void union(Vertex pVertexX, Vertex pVertexY) {
		Vertex xRoot = find(pVertexX);
		Vertex yRoot = find(pVertexY);
		
		if(!xRoot.equals(yRoot)) {
			
			if(xRoot.rank < yRoot.rank) {
				xRoot.parent = yRoot;
			} else if(xRoot.rank > yRoot.rank) {
				yRoot.parent = xRoot;
			} else {
				yRoot.parent = xRoot;
				xRoot.rank += 1;
			}
			
		}
		
	}
	
	private static void makeSet(Vertex pVertex){
		pVertex.parent = pVertex;
		pVertex.rank = 0;
	}
	
	private static void kruskal() {
		
		Vertex u, v;
		LinkedList<Vertex> pointers = new LinkedList<Vertex>();
		
		for(int x = 0; x < data.length; x++) {
			makeSet(data[x].fromTown);
			makeSet(data[x].toTown);
		}
		
		for(int x = 0; x < data.length; x++) {
			
			/*
			 * Esto es feisimo, seguro que hay una manera mejor de hacerlo
			 * TODO mejorar este trozo 
			 */
			if(pointers.contains(data[x].fromTown)) {
				u = pointers.get(pointers.indexOf(data[x].fromTown));
				data[x].fromTown = u;
			} else {
				u = data[x].fromTown;
				pointers.add(u);
			}
			
			if(pointers.contains(data[x].toTown)) {
				v = pointers.get(pointers.indexOf(data[x].toTown));
				data[x].toTown = v;
			} else {
				v = data[x].toTown;
				pointers.add(v);
			}

			if(!find(u).equals(find(v))) {
			
				createLink(data[x]);
				union(u, v);
				
			}
			
		}
		
	}
	
	private static void createLink(Road pRoad) {
		if(graph.get(pRoad.fromTown) == null) {
			
			graph.put(pRoad.fromTown, new LinkedList<Road>());
			
		} 
		
		if(graph.get(pRoad.toTown) == null) {
			
			graph.put(pRoad.toTown, new LinkedList<Road>());
			
		} 
			
		graph.get(pRoad.fromTown).add(pRoad);
		graph.get(pRoad.toTown).add(new Road(pRoad.toTown, pRoad.fromTown, pRoad.distance));
		
		
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
		
		Road[] helper = new Road[data.length];
				
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
	    	
	    	if (helper[i].distance <= helper[j].distance) {
	        
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
