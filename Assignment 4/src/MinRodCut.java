import java.util.ArrayList;
import java.util.Scanner;

public class MinRodCut {

	public static void main(String[]args){
		int rodLength;
		int numCuts;
		int[] cutPoints;

		Scanner in = new Scanner(System.in);
		rodLength = in.nextInt();
		if (rodLength < 2 || rodLength > 100) {
			throw new IndexOutOfBoundsException("Index " + rodLength + " must be between 2 and 100, inclusively.");
		}
		numCuts = in.nextInt();
		if (numCuts < 1 || numCuts > rodLength - 1) {
			throw new IndexOutOfBoundsException("Number of cutting points, M, must be 1 <= M <= N-1");
		}
		cutPoints = new int[numCuts];

		for (int i = 0; i < numCuts; i++) {
			cutPoints[i] = in.nextInt();
		}

		System.out.println(findMinCost(cutPoints, rodLength));
		//	    System.out.println(findMinCost(new int[]{1, 5, 7, 9}, 10));
	}
	/**
	  The plan
	  ======
	  M markings => M+2 markings, including the beginning and the end of the log
	  dp[i][j] = optimum cost cutting up log between marking i and marking j
	  dp[i][j] = if |j-i| < 2 then 0, same marking or no inbetween markings
	     dp[i][j] = dp[i][k]+dp[k][j]+logLen(i,j) over all k:i+1..j-1
	  O(M**2) different (i,j) pairs:
	     maximum O(M) different cuts between i and j:
	       1 sum calculation, 1 length calculation  -> time:O(1), space:O(M)
	dp time: O((M**2)*M), space: O(M**2)
	 **/
	private static int findMinCost(int[] cutPoints, int len){
		int[] length = cutLength(cutPoints, len); // Sets cut points array to include start and end positions
		int[][] dp = new int[cutPoints.length + 2][cutPoints.length + 2]; // 2D array to hold costs of cutting a rod between points

		/*
		 * For each cutting point, calculate the minimum cost between any given set
		 * of points. The optimal cost of cutting between two given points is stored 
		 * in the 2D array dp.
		 */
		for(int betweenMarkCount = 1; betweenMarkCount <= cutPoints.length; betweenMarkCount++){
			/*
			 * Search all potential paths, storing only the minimum values. i is the starting point of the row and j 
			 * is the column value, dependent on the count between marks. The first loop looks at only 1 cutting point, 
			 * then progressively adds more cutting points.
			 */
			for(int i = 0; i < cutPoints.length; i++){
				int j = i + betweenMarkCount + 1; // 

				// Exits when the value of j is larger than the length of the dp array 
				if (j >= dp.length) {
					break;
				}

				int min = Integer.MAX_VALUE; // Set min value
				int cost = length[j] - length[i]; // Set cost to be the difference between two points
				/*
				 * Search all suitable paths and store the most optimal in the dp array.
				 */
				for(int k = i+1; k <= j-1; k++){
					min = Math.min(min, dp[i][k] + dp[k][j] + cost); 
				}
				dp[i][j] = min; // Store optimal min
			}
		}
//		for (int i = 0; i < dp.length; i++) {
//			for (int j = 0; j < dp.length; j++) {
//				System.out.printf("%5d ", dp[i][j]);
//			}
//			System.out.println();
//		}
		return dp[0][dp.length-1]; // Returns the minimal cost using the passed cutting points
	}

	/**
	 * Returns an updated array of points that include the first and last indices.
	 * @param markings - Array of points that can be used to cut a rod by.
	 * @param len - The length of the array 
	 * @return An array that includes the first and last positions, in addition to the points passed as a parameter.
	 */
	private static int[] cutLength(int[] markings, int len){
		int[] result = new int[markings.length+2];
		for(int i = 1; i <= markings.length; ++i){
			result[i] = markings[i-1];
		}
		result[result.length - 1] = len;
		return result;
	}
}
