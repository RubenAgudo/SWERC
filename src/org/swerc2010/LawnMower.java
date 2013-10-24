package org.swerc2010;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @author Rubén Agudo Santos
 */

public class LawnMower {

	
	/*
	 * Attributes
	 */
	private static Scanner sc;
	private static PrintWriter out;
	
	public static void main(String[] args) {
		
		try {
			sc = new Scanner(new File(args[0]));
			out = new PrintWriter(
					new BufferedWriter(
					new FileWriter("A2010Mio.out")));
			
			proccess();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		
		} finally {
			out.close();
			sc.close();
		}
		
		

	}

	/**
	 * This method processes all the input. 
	 * It is not put in the main for cleanse reasons.
	 */
	private static void proccess() {
		
		boolean end = false;
		double passesInX, passesInY, widthLM;
		
		boolean coveredInX = false;
		boolean coveredInY = false;
		
		while(!end) {
			
			//obtaining the first line
			passesInX = Double.parseDouble(sc.next());
			passesInY = Double.parseDouble(sc.next());
			widthLM = Double.parseDouble(sc.next());
			
			/*
			 * if we finished proccessing the last line. 
			 * I.E we finish the program
			 */
			if( passesInX == 0 && passesInY == 0 && widthLM == 0.0f ) {
				
				end = true;
				
			} else {
				
				coveredInX = proccessField(passesInX, widthLM, 75d);
				
				/*
				 * if we don't cover along the lenght it does not meet 
				 * the requirements to have done the job well.
				 */
				if(coveredInX) {
					
					coveredInY = proccessField(passesInY, widthLM, 100d);
				
				} else {
					/*
					 * So we skip the next line
					 */
					sc.nextLine();
					sc.nextLine();
				}
				
				if(coveredInX && coveredInY) {
					
					out.println("YES");
					
				} else {
					
					out.println("NO");
					
				}
				
			}
			
		}
		
	}
	
	
	/**
	 * This method calculates if Guido covers all the field.
	 * @param widthLM the width of the lawn-mower
	 * @param passesInX the number of passes 
	 * @return true if it's properly covered. False otherwise
	 */
	private static boolean proccessField(double passesInX, double widthLM, double pMax) {
		PriorityQueue<Pair> data = new PriorityQueue<Pair>((int) passesInX);
		int numPasses = 0;
		double x, y;
		
		/*
		 * the start and the end of the cut of the lawn mower in
		 * each pass and the starting position of the pass. 
		 */
		
		float startingPosition = 0.0f;
		Pair aPair;
		
		while(numPasses < passesInX) {
			
			startingPosition = Float.parseFloat(sc.next());
			x = startingPosition - widthLM/2;
			y = startingPosition + widthLM/2;
			
			aPair = new Pair(x, y);
			
			//inserting in the priority queue keeping
			//the Pair.X parameter as the ordering parameter
			data.add(aPair);
			
			
			numPasses++;
			
		}
		
		return covered(data, pMax);
	}
	

	
	private static boolean covered(PriorityQueue<Pair> data, double pMax) {
		double max = 0d;
		double min = 100001d;
		boolean exit = false;
		Pair aPair, latestPair = null;
		
		//we take the first chunk
		latestPair = data.poll();
		
		//initializing the min max variables
		if(latestPair.getX() < min && latestPair.getY() >= max) {
			min = latestPair.getX();
			max = latestPair.getY();
		}
		
		/*
		 * if min is greater than zero, means that we can't 
		 * cover the field because the first element of the
		 * priority queue is the closest to zero
		 */
		if(min > 0) {
			exit = true;
		}
		
		/*
		 * while chunks are available
		 */
		while(!data.isEmpty() & !exit) {
			
			aPair = data.poll();
			
			/*
			 * as it's ordered, if the next chunk
			 * cannot "extend" the covered part we abort
			 * we update max if needed
			 */
			if(aPair.getX() <= latestPair.getY()) {
				if(aPair.getY() > max) {
					max = aPair.getY();
				}
			} else {
				exit = true;
			}
			
			latestPair = aPair;
		}
		
		return min <= 0 && max >= pMax;
	}
		
}