package swerc2009;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class F_HauntedGraveyard {

	public static class Vertex {
		
		private int posX;
		private int posY;
		
		public Vertex(int pPosX, int pPosY) {
			
			posX = pPosX;
			posY = pPosY;
			
		}
		
		@Override public boolean equals(Object o) {
			return (o instanceof Vertex) && (posX == ((Vertex) o).posX && posY == ((Vertex) o).posY);
		}
	}
	
	public static class Road {
		
		private Vertex from;
		private Vertex to;
		private int time;
		
		public Road(Vertex pFrom, Vertex pTo, int pTime) {
			
			from = pFrom;
			to = pTo;
			time = pTime;
			
		}
		
		@Override public boolean equals(Object o) {
			return (o instanceof Road) && 
					(from.equals(((Road)o).from) && 
					to.equals(((Road)o).to) && 
					time == ((Road)o).time);
		}
		
	}
	
	private static LinkedList<Vertex> collectionVertices;
	private static LinkedList<Road> graph;
	private static Scanner sc;
	private static final int INFINITY = -10001;
	
	public static void main(String[] args) {
		
		int w, h;
		
		try {
			sc = new Scanner(new File(args[0]));
			
			w = Integer.parseInt(sc.next());
			h = Integer.parseInt(sc.next());
			
			while(w != 0 && h != 0) {
				collectionVertices = new LinkedList<Vertex>();
				graph = new LinkedList<Road>();
				process(w, h);
				
				w = Integer.parseInt(sc.next());
				h = Integer.parseInt(sc.next());
				
			}
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private static void process(int w, int h) {
		int gravestones = 0;
		int hauntedHoles = 0;
		
		createTheVertices(w, h);
		gravestones = Integer.parseInt(sc.next());
		removeGravestones(gravestones);
		createGraph(w, h);
		hauntedHoles = Integer.parseInt(sc.next());
		createHauntedHoles(hauntedHoles);
		findOptimumPath(w, h);
		
	}

	private static void findOptimumPath(int w, int h) {
		
		Vertex origin = new Vertex(0, 0);
		
		int result = bellmanFord(origin, graph.size(), w, h);
		
		if(result == INFINITY) {
			System.out.println("Impossible");
		} else if(result == -1) {
			System.out.println("Never");
		} else {
			System.out.println(result);
		}
	}

	/**
	 * The bellman-ford algorithm, only indicating the number of roads and the starting node.
	 * @param pStartingTown
	 * @param pRoads
	 * @param height 
	 * @param width 
	 * @return
	 */
	private static int bellmanFord(Vertex pOrigin, int pRoads, int width, int height) {
		HashMap<Vertex, Vertex> predecessors = new HashMap<Vertex, Vertex>();
		HashMap<Vertex, Integer> distances = new HashMap<Vertex, Integer>();
		HashMap<Vertex, Boolean> visited = new HashMap<Vertex, Boolean>();
		
		int i, j, result = 0;
		
		for(i = 0; i < collectionVertices.size(); i++) {
			if(collectionVertices.get(i).equals(pOrigin)) {
				distances.put(collectionVertices.get(i), 0);
			} else {
				distances.put(collectionVertices.get(i), INFINITY);
			}
			
			predecessors.put(collectionVertices.get(i), null);
			visited.put(collectionVertices.get(i), false);
		}
		
		for(i = 0; i < collectionVertices.size()-1; i++) {
			
			for(j = 0; j < pRoads; j++) {
				
				if(!visited.get(graph.get(j).to)) {
					
					int u = distances.get(graph.get(j).from); //distance of NodeFrom
					int w = graph.get(j).time;
					int v = distances.get(graph.get(j).to); //distance of NodeTo
					
					if(u + w < v) {
						//distances[NodeTo] = distances[NodeFrom] + profit
						distances.put(graph.get(j).to, u + w);
			            predecessors.put(graph.get(j).to, graph.get(j).from);
						
					}
					
					visited.put(graph.get(j).to, true);
				}
			}
			
		}
		
		//check for negative cycles
		for(i = 0; i < graph.size(); i++) {
			
			int u = distances.get(graph.get(i).from); //distance of NodeFrom
			int w = graph.get(i).time;
			int v = distances.get(graph.get(i).to); //distance of NodeTo
			
			if(u + w < v) {
				
				result = -1;
				
			}
		}
		
		if(result != -1) {
			
			result = distances.get(new Vertex(width, height));
		
		}
		
		return result;
	}
	
	private static void createHauntedHoles(int hauntedHoles) {
		
		int posXFrom, posYFrom, posXTo, posYTo, time;
		
		Vertex from, to;
		Road hauntedRoad;
		
		for(int x = 0; x < hauntedHoles; x++) {
			
			posXFrom = Integer.parseInt(sc.next());
			posYFrom = Integer.parseInt(sc.next());
			
			posXTo = Integer.parseInt(sc.next());
			posYTo = Integer.parseInt(sc.next());
			
			time = Integer.parseInt(sc.next());
			
			from = new Vertex(posXFrom, posYFrom);
			to = new Vertex(posXTo, posYTo);
			
			hauntedRoad = new Road(from, to, time);
			
			graph.add(hauntedRoad);
		}
		
	}

	private static void createGraph(int w, int h) {
		
		Vertex me, otherVertex = null;
		Road aRoad;
		
		for(int x = 0; x < collectionVertices.size(); x++) {
			
			me = collectionVertices.get(x);
			
			for(int y = 0; y < 4; y++) {
				
				switch(y) {
				case 0:
					otherVertex = new Vertex(me.posX + 1, me.posY);
					break;
				case 1:
					otherVertex = new Vertex(me.posX, me.posY + 1);
					break;
				case 2:
					otherVertex = new Vertex(me.posX - 1, me.posY);
					break;
				case 3:
					otherVertex = new Vertex(me.posX, me.posY - 1);
					break;
				}
				
				if(otherVertex.posX < w  && otherVertex.posY < h &&
						otherVertex.posX >= 0 && otherVertex.posY >= 0) {
					
					aRoad = new Road(me, otherVertex, 1);
					graph.add(aRoad);
				}
						
			}
			
		}
		
	}

	private static void removeGravestones(int gravestones) {
		
		int posXGravestone, posYGravestone;
		Vertex gravestone;
		
		for(int x = 0; x < gravestones; x++) {
			posXGravestone = Integer.parseInt(sc.next());
			posYGravestone = Integer.parseInt(sc.next());
			gravestone = new Vertex(posXGravestone, posYGravestone);
			collectionVertices.remove(gravestone);
		}
		
	}
	
	
	private static void createTheVertices(int w, int h) {
		
		for(int x = 0; x < w; x++) {
			for(int y = 0; x < w; x++) {
				collectionVertices.add(new Vertex(x, y));
			}
		}
		
	}

}
