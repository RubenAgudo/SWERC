package org.swerc2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class G_TollRoad {

	/**
	 * @author Rubén Agudo Santos
	 * Complexity O(2*|N|*|E| + R) being N number of nodes, E number of edges
	 * and R the number of roads.
	 * 
	 * Meaning that the complexity of the problem is, 2 times the cost of Bellman-Ford
	 * algorithm plus the cost of creating the graph the first time. 
	 */
	
	private static ArrayList<LinkedList<Integer[]>> graph;
	private static ArrayList<Integer> listaNombres;
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
		
		int roads = Integer.parseInt(sc.next());
		
		while(roads != 0) {
			
			createGraph(roads);
			
		}
		
	}


	private static void createGraph(int pRoads) {
		
		int x = 0,
			townFrom,
			townTo,
			profit,
			maxProfit = 0;
		
		int[] startingTowns = new int[2],
				expectedProfits = new int[2];
		
		graph = new ArrayList<LinkedList<Integer[]>>(pRoads);
		listaNombres = new ArrayList<Integer>(pRoads);
		
		for(x = 0; x < pRoads; x++) {
			
			townFrom = Integer.parseInt(sc.next());
			townTo = Integer.parseInt(sc.next());
			profit = Integer.parseInt(sc.next());
			
			if(profit > maxProfit) {
				maxProfit = profit;
				
				startingTowns[0] = townFrom;
				startingTowns[1] = townTo;
				
			}
			
			createLink(townFrom, townTo, profit);
			
			
		}
		
		for(x = 0; x < startingTowns.length; x++) {
			expectedProfits[x] = bellmanFord(startingTowns[x]);
		}
		
		print(expectedProfits);
		
		
		
	}


	private static void print(int[] expectedProfits) {
		
		if(expectedProfits[0] > expectedProfits[1]) {
			
			System.out.println(expectedProfits[0]);
			
		} else {
			
			System.out.println(expectedProfits[1]);
			
		}
		
	}


	private static int bellmanFord(int i) {
		
		return 0;
	}


	private static void createLink(int townFrom, int townTo, int profit) {
		
		addNodeToGraph(townFrom);
		addNodeToGraph(townTo);
		
		graph.get(listaNombres.indexOf(townFrom)).add(
				new Integer[] {townTo, profit});
		
		graph.get(listaNombres.indexOf(townTo)).add(
				new Integer[] {townFrom, profit});
		
	}


	private static void addNodeToGraph(int pTown) {
		
		if(!listaNombres.contains(pTown)) {
			
			listaNombres.add(pTown);
			graph.add(new LinkedList<Integer[]>());
			
		}
		
	}

}
