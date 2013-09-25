package org.swerc2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class G_TollRoad_BellmanFord {

	/**
	 * @author Rubén Agudo Santos
	 * Complexity O(2*|N|*|E| + R) being N number of nodes, E number of edges
	 * and R the number of roads.
	 * 
	 * Meaning that the complexity of the problem is, 2 times the cost of Bellman-Ford
	 * algorithm plus the cost of creating the graph the first time. 
	 */
	
	private static ArrayList<Integer[]> edges;
	private static ArrayList<Integer> listaNombres;
	private static Scanner sc;
	
	private static final int INFINITY = 1001;
	
	public static void main(String[] args) {
		
		try {
			readFromFile(args[0]);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}

	}

	
	private static void readFromFile(String path) throws FileNotFoundException {
		
		sc = new Scanner(new File(path));
		
		int roads = Integer.parseInt(sc.next());
		
		while(roads != 0) {
			
			createEdges(roads);
			roads = Integer.parseInt(sc.next());
		}
		
	}


	private static void createEdges(int pRoads) {
		
		int x = 0,
			townFrom,
			townTo,
			profit,
			maxProfit = 0;
		
		LinkedList<Integer> startingTowns = new LinkedList<Integer>(),
				expectedProfits = new LinkedList<Integer>();
		
		edges = new ArrayList<Integer[]>(pRoads * 2);
		listaNombres = new ArrayList<Integer>(pRoads);
		
		for(x = 0; x < pRoads; x++) {
			
			townFrom = Integer.parseInt(sc.next());
			townTo = Integer.parseInt(sc.next());
			profit = Integer.parseInt(sc.next());
			
			if(profit > maxProfit) {
				
				startingTowns = new LinkedList<Integer>();
				startingTowns.add(townFrom);
				startingTowns.add(townTo);
				
				maxProfit = profit;
				
			} else if(profit == maxProfit) {
				if(!startingTowns.contains(townFrom)) {
					startingTowns.add(townFrom);
				}
				
				if(!startingTowns.contains(townTo)) {
					startingTowns.add(townTo);
				}
			}
			
			createLink(townFrom, townTo, profit);
			
			
		}
		
		Iterator<Integer> itr = listaNombres.iterator();
		while(itr.hasNext()) {
			Integer x2 = itr.next();
			expectedProfits.add(bellmanFord(x2, pRoads));
		}
		
		print(expectedProfits);
		
		
		
	}


	private static void createLink(int townFrom, int townTo, 
			int profit) {
		
		addNodeToGraph(townFrom);
		addNodeToGraph(townTo);
		
		edges.add(new Integer[] {townFrom, townTo, profit*(-1)});
		edges.add(new Integer[] {townTo, townFrom, profit*(-1)});
		
	}


	private static void addNodeToGraph(int pTown) {
		
		if(!listaNombres.contains(pTown)) {
			
			listaNombres.add(pTown);
			
		}
		
	}

	/**
	 * The bellman-ford algorithm, only indicating the number of roads and the starting node.
	 * @param pStartingTown
	 * @param pRoads
	 * @return
	 */
	private static int bellmanFord(int pStartingTown, int pRoads) {
		int[] predecessors = new int[listaNombres.size()];
		int[] distances = new int[listaNombres.size()];
		
		int i, j, maxProfit = 0;
		
		for(i = 0; i < listaNombres.size(); i++) {
			if(listaNombres.get(i) == pStartingTown) {
				distances[i] = 0;
			} else {
				distances[i] = INFINITY;
			}
			
			predecessors[i] = -1;
			
		}
		
		for(i = 0; i < listaNombres.size()-1; i++) {
			
			for(j = 0; j < pRoads; j++) {
				
				int x = distances[listaNombres.indexOf(edges.get(j)[0])]; //distance of NodeFrom
				int y = edges.get(j)[2]; //profit
				int z = distances[listaNombres.indexOf(edges.get(j)[1])]; //distance of NodeTo
				
				if(x + y < z) {
					//distances[NodeTo] = distances[NodeFrom] + profit
					distances[listaNombres.indexOf(edges.get(j)[1])] = distances[listaNombres.indexOf(edges.get(j)[0])] + edges.get(j)[2];
		            predecessors[listaNombres.indexOf(edges.get(j)[1])] = edges.get(j)[0];
					
				} else if(z + y < x) {
					
					//distances[NodeTo] = distances[NodeFrom] + profit
					distances[listaNombres.indexOf(edges.get(j)[0])] = distances[listaNombres.indexOf(edges.get(j)[1])] + edges.get(j)[2];
		            predecessors[listaNombres.indexOf(edges.get(j)[0])] = edges.get(j)[1];
					
				}
				
			}
			
		}
		
		for(i = 0; i < listaNombres.size(); i++) {
			
			if(distances[i]*(-1) > maxProfit) {
				maxProfit = distances[i]*(-1);
			}

		}
		
		return maxProfit;
	}


	private static void print(LinkedList<Integer> expectedProfits) {
		
		Iterator<Integer> itr = expectedProfits.iterator();
		int maxProfit = 0, profit;
		
		while(itr.hasNext()) {
			
			profit = itr.next();
			
			if(profit > maxProfit) {
				
				maxProfit = profit;
				
			}
			
		}
		
		System.out.println(maxProfit);
		
	}

}
