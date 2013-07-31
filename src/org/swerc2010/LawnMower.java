package org.swerc2010;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
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
			sc = new Scanner(new File("A2010.in"));
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
		int passesInX, passesInY;
		float widthLM;
		
		boolean coveredInX = false;
		boolean coveredInY = false;
		
		while(!end) {
			
			//obtaining the first line
			passesInX = Integer.parseInt(sc.next());
			passesInY = Integer.parseInt(sc.next());
			widthLM = Float.parseFloat(sc.next());
			
			/*
			 * if we finished proccessing the last line. 
			 * I.E we finish the program
			 */
			if( passesInX == 0 && passesInY == 0 && widthLM == 0.0f ) {
				
				end = true;
				
			} else {
				
				coveredInX = proccessField(passesInX, widthLM);
				
				/*
				 * if we don't cover along the lenght it does not meet 
				 * the requirements to have done the job well.
				 */
				if(coveredInX) {
					
					coveredInY = proccessField(passesInY, widthLM);
				
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
	 * @param pWidthLM the width of the lawn-mower
	 * @param pPasses the number of passes 
	 * @return true if it's properly covered. False otherwise
	 */
	private static boolean proccessField(int pPasses, float pWidthLM) {
		
		LinkedList<float[]> data = new LinkedList<float[]>();
		int x = 0;
		
		/*
		 * the start and the end of the cut of the lawn mower in
		 * each pass and the starting position of the pass. 
		 */
		
		float startingPosition = 0.0f;
		
		while(x < pPasses) {
			
			float[] pass = new float[2];
			
			startingPosition = Float.parseFloat(sc.next());
			pass[0] = startingPosition - pWidthLM/2;
			pass[1] = startingPosition + pWidthLM/2;
			
			
			if(data.isEmpty()) {
				
				data.add(pass);

			} else {
				
				insert(pass, data);
			}
			
			x++;
			
		}
		
		return data.size()==1;
	}
	

	
	/**
	 * Method that inserts in order and merges if necessary.
	 * @param pPass
	 * @param pData
	 */
	private static void insert(float[] pPass, LinkedList<float[]> pData) {
		
		
		float[] storedPass;
		int index = 0;
		boolean exit = false;
		boolean keepProccessing = false;
		
		while(pData.size() > index && !exit) {
			
			storedPass = new float[2];
			storedPass = pData.removeFirst();
			
			//Possible cases
			//storedPass = [10,20], pPass = [0,5]  -> result = [0,5], [10,20]
			//storedPass = [10,20], pPass = [0,11]  -> result = [0,20]
			//storedPass = [10,20], pPass = [0,30]  -> result = [0,30]
			//storedPass = [10,20], pPass = [11,19]  -> result = [10,20]
			//storedPass = [10,20], pPass = [13,30]  -> result = [10,30]
			//storedPass = [10,20], pPass = [25,30]  -> result = process with the next storedPass
			
			//cases put in the order shown above.
			
			if( storedPass[0] > pPass[0] && 
					storedPass[0] > pPass[1] &&
					storedPass[1] > pPass[0] &&
					storedPass[1] > pPass[1] && !keepProccessing) {
			
				pData.add(index, pPass);
				pData.add(index + 1, storedPass);
				exit = true;
				
			} else if(storedPass[0] > pPass[0] && 
					storedPass[0] <= pPass[1] &&
					storedPass[1] > pPass[0] &&
					storedPass[1] >= pPass[1]) {
				
				pPass = new float[] {pPass[0], storedPass[1]};
				pData.add(index, pPass);
				exit = true;
				
			} else if(storedPass[0] <= pPass[0] && 
					storedPass[0] < pPass[1] &&
					storedPass[1] > pPass[0] &&
					storedPass[1] >= pPass[1]) {
				
				//TODO Do nothing
				exit = true;
				
			} else if(storedPass[0] <= pPass[0] && 
					storedPass[0] < pPass[1] &&
					storedPass[1] >= pPass[0] &&
					storedPass[1] < pPass[1]) {
			
				keepProccessing = true;
				pPass = new float[] {storedPass[0], pPass[1]};
				pData.add(index, pPass);
				
				//continuar procesando por haber alargado el pPass
				
			} else if(storedPass[0] <= pPass[0] && 
					storedPass[0] < pPass[1] &&
					storedPass[1] > pPass[0] &&
					storedPass[1] <= pPass[1]) {
			
				keepProccessing = true;
				pData.add(index, pPass);
				
			} else if(storedPass[0] < pPass[0] && 
					storedPass[0] < pPass[1] &&
					storedPass[1] < pPass[0] &&
					storedPass[1] < pPass[1]) {
				
				keepProccessing = true;
				pData.add(index, storedPass);
				pData.add(index + 1, pPass);
				
					
				
				
			}
				
			index++;
		}
		
	}
	
}
