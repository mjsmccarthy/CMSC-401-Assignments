import java.util.Arrays;
import java.util.Scanner;

/**
 * Program to calculate the minimum cost of cutting a rod. When provided a rod that 
 * is N inches long and a set of M cutting points on the the rod, the minimum cost
 * is calculated using any order of the cuts provided. After a cut, the rod is divided
 * into two smaller sub-rods. The cost of making a cut is the length of the current 
 * sub-rod in which the cut is being made.
 * 
 * Resources: 
 * 		InterviewBit - Rod-Cutting Problem
 * 
 * @author Matthew McCarthy
 * @version 4/17/2019 CMSC 401 - Assignment #4
 *
 */
public class cmsc401 {

	/**
	 * Main method to prompt the user for input and output the result.
	 * @param args - N
	 */
	public static void main(String[]args){
		int rodLength;		// Length of the rod
		int numCuts;		// Number of cuts
		int[] cuts;	// Location of cuts

		Scanner in = new Scanner(System.in);
		System.out.print("Rod Length: ");
		rodLength = in.nextInt();

		System.out.print("Number of cuts: ");
		numCuts = in.nextInt();

		cuts = new int[numCuts];

		System.out.println("Location of cuts: ");
		for (int i = 0; i < numCuts; i++) {
			cuts[i] = in.nextInt();
		}

		Arrays.sort(cuts);
		
		System.out.println(findMinCost(cuts, rodLength));
//			    System.out.println(findMinCost(new int[]{1, 5, 7, 9}, 10));
//			    System.out.println(findMinCost(new int[]{1, 2, 5}, 6));
		in.close();
	}
	
	/**
	 * Determines the minimum cost of cutting a rod when provided a sequence of points
	 * to cut the rod along. To determine the minimum, all combinations of cutting point 
	 * are analyzed and the minimum is stored.
	 * @param cuts - An array of points to cut the rod by.
	 * @param len - The length of the rod
	 * @return - An integer value that is the minimum cost of cutting the rod by the optimal 
	 * 			 sequence from the cut points provided.
	 */
	private static int findMinCost(int[] cuts, int len){
		int[] length = cutLength(cuts, len); // Adds start and end positions to the cutting points
		int[][] res = new int[cuts.length + 2][cuts.length + 2]; // 2D array to hold costs of cutting a rod between points

		/*
		 * For each cutting point, calculate the minimum cost between any given set
		 * of points. The optimal cost of cutting between two given points is stored 
		 * in the 2D array res. 
		 * 
		 * The idea is to calculate and store the minimum at each stage of the process. The
		 * first step is to calculate the minimum when there is only one cut for each possible
		 * choice. The next iteration increases the number of cuts and repeats the process again.
		 * This continues until 4 counts are made and the optimal minimum for cutting with any
		 * sequence of points is stored.
		 */
		for(int count = 1; count <= cuts.length; count++){
			/*
			 * Search all cut points
			 */
			for(int i = 0; i < cuts.length; i++){
				int j = i + count + 1; // j is dependent on the outer for loop count 

				// Exits when the value of j is larger than the length of the res array 
				if (j >= res.length) {
					break;
				}

				int min = Integer.MAX_VALUE; 		// Set min value
				int cost = length[j] - length[i]; 	// Set cost to be the difference between two points
				/*
				 * Search all suitable paths and find the most optimal minimum.
				 */
				for(int k = i+1; k <= j-1; k++){
					min = Math.min(min, res[i][k] + res[k][j] + cost); 
				}
				res[i][j] = min; // Store optimal min
			}
		}

		return res[0][res.length-1]; // Returns the minimal cost using the passed cutting points
	}

	/**
	 * Helper method that returns an updated array of points that include the first and last indices.
	 * @param markings - Array of points that can be used to cut a rod by.
	 * @param len - The length of the array 
	 * @return An array that includes the first and last positions, in addition to the points passed as a parameter.
	 */
	private static int[] cutLength(int[] markings, int len){
		int[] result = new int[markings.length+2];	// New array to store first and last values
		// Add all values from passed array to new array
		for(int i = 1; i <= markings.length; i++){
			result[i] = markings[i-1];
		}
		result[result.length - 1] = len; // Add last value to the end of the new array
		return result;
	}
}
