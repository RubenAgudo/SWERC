package org.amazon;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class ReconstructTree {
	/*
	 * Given an input like: 
 [2, 4]
 [1, 2]
 [3, 6]
 [1, 3]
 [2, 5]
 Use it to reconstruct this binary tree:
 1
 2 3
 4 5 6
 Note that:
 a) The first number in each line is a parent.
 b) Second number is a child.
 c) The left child always shows up in the input before the right child.
 Return root.
 You may use the standard or base library included with the language of your choice. Your solution will be evaluated on correctness, runtime complexity (big-O), and adherence to coding best practices. A complete answer will include the following: 
 1. List the language you’re using
 2. Document your assumptions
 3. Explain your approach and how you intend to solve the problem
 4. Provide code comments where applicable
 5. Explain the big-O run time complexity of your solution. Justify your answer
 6. Identify any additional data structure you used and justify why you used them.
 You should start by using the example function prototype listed below or recreate in the language of your choice. You can assume this method will be called for each pair.
 class BuildTree {
 public static Node reconstructTree(int[][] input) {
 // TODO: Build the tree from the values in input and return the root node.
 }
	 */
	
	//Language used: Java
	//Assumptions: The tree it must not be printed, it's no necessary to use a tree structure, each parent only have to children.
	//Approach: Reconstruct the tree using a HashMap, in which the key is the parent and the value is a list of children.
	//			Whenever you read a new pair, add it to the HashMap and save which one is a parent and a child.
	//			When you finish adding pairs, Iterate through isParent, and when you found a parent that is not child, that is root
	//			return root
	//runtime complexity: Worst case scenario O(2n). 
	//				Adding N pairs has cost N because a HashMap can add elements in constant time. Then, you search for the root, which may be
	//				the last element in the isParent list.
	//HashMap: Used because it provides efficient data retrieval (O(1)).
	//HashSet: Perfect for storing "appeared" values and searching for a value is also constant.
	//LinkedList. A priori we don't know how many pairs will be, so we don't know how many parents will be. This structure it does not have a limit
	//like the classic array or arraylist, and we can iterate through it using the Java Iterator<T>
	//Downsides of HashMap and HashSet: to function properly and avoid collisions, they need to be large, so in space complexity they are not
	//very efficient, but this exercise asked for time efficiency.
	
	HashMap<Integer, Integer[]> tree;
	HashSet<Integer> isChild;
	LinkedList<Integer> isParent;
	private int root;
	
	public ReconstructTree() {
		tree = new HashMap<Integer, Integer[]>();
		isChild = new HashSet<Integer>();
		isParent = new LinkedList<Integer>();
		int[][] input = new int[][] {{2,4},{1,2},{3,6},{1,3},{2,5}};
		buildTree(input);
	}

	public int buildTree(int[][] input) {
		
		for(int x = 0; x < input.length; x++) {
			
//			isParent.add(input[x][0]);
			isChild.add(input[x][1]);
			addToTree(input[x]);
			

		}
//		getRoot();
		System.out.println("Root is: " + root);
		return root;
	}

//	private void getRoot() {
//		Iterator<Integer> itr = isParent.iterator();
//		boolean rootFound = false;
//		
//		while(itr.hasNext() && !rootFound) {
//			int aParent = itr.next();
//			if(!isChild.contains(aParent)) {
//				rootFound = true;
//				root = aParent;
//			}
//		}
//	}

	private void addToTree(int[] aPair) {

		Integer parent = aPair[0];
		Integer element = aPair[1];
		Integer[] pairInTree = tree.get(parent);
		
		if(pairInTree == null) {
			pairInTree = new Integer[2];
			pairInTree[0] = element;
			tree.put(parent, pairInTree);
		} else {
			pairInTree[1] = element;
		}
		
		if(!isChild.contains(parent)) {
			root = parent;
		}
		
	}

	public static void main(String[] args) {
		new ReconstructTree();
	}
}
