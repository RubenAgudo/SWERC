package org.swerc2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class G_TollRoad {

	//SOlved using Bellman-Ford algorithm
	private static LinkedList<LinkedList<LinkedList<Integer[]>>> graphs;
	private static LinkedList<LinkedList<Integer>> listaNombres;
	
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
		
		int x = 0;
		
		graphs = new LinkedList<LinkedList<LinkedList<Integer[]>>>();
		listaNombres = new LinkedList<LinkedList<Integer>>();
		
		for(x = 0; x < pRoads; x++) {
			
			//we create a graph
			graphs.add(new LinkedList<LinkedList<Integer[]>>());
		
		}
		
		
		
		
	}

}
