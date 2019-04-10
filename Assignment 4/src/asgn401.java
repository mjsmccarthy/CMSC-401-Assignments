import java.util.ArrayList;
import java.util.Scanner;

public class asgn401 {
//	public static int cut(int cost[], int n) {
//		int val[] = new int[n + 1];
//		val[0] = 0;
//		
//		for (int i = 1; i <= n; i++) {
//			int max_val = Integer.MAX_VALUE;
//			for (int j = 0; j < i; j++) {
//				max_val = Math.min(max_val, cost[j] + val[i-j-1]);
//			}
//			val[i] = max_val;
//		}
//		return val[n];
//	}
//	
//	public static void main(String args[]) {
//		int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//		int size = arr.length;
//		System.out.println("Maximum Obtainable value is " + cut(arr, size));
//	}
	
	private ArrayList<Integer> result;
	//ArrayList<Integer> minCost = new ArrayList<>();
	int minCost;
    int[] cuts;
    int[][] parent;
    int[][] dp;
    public ArrayList<Integer> rodCut(int rod, ArrayList<Integer> scores) {
        int n = scores.size() + 2;
        cuts = new int[n];
        cuts[0] = 0;
        for (int i = 0; i < scores.size(); i++) {
            cuts[i + 1] = scores.get(i);
        }
        cuts[n - 1] = rod;
        
        //int[][] dp = new int[n][n];
        dp = new int[n][n];
        parent = new int[n][n];
        for (int len = 1; len <= n; len++) {
            for (int s = 0; s < n - len; s++) {
                int e = s + len;
                for (int k = s + 1; k < e; k++) {
                    int sum = cuts[e] - cuts[s] + dp[s][k] + dp[k][e];
                    if (dp[s][e] == 0 || sum < dp[s][e]) {
                        dp[s][e] = sum;
                        parent[s][e] = k;
                    }                    
                }
            }
        }
        
        result = new ArrayList<>();
        backTrack(0, n - 1);
        
        return result;
    }
    
    private void backTrack(int s, int e) {
        if (s + 1 >= e) {
            return;
        }        
        
        result.add(cuts[parent[s][e]]);
        minCost = Integer.MIN_VALUE;
        
        //minCost.add(dp[s][e]);
        backTrack(s, parent[s][e]);
        backTrack(parent[s][e], e);
        
        if (minCost < dp[s][e]) {
        	minCost = dp[s][e];
        }
    }
    
    public static void main(String args[]) {
    	ArrayList<Integer> cutPoints = new ArrayList<>();
    	int rodLength;
    	int numCuts;
    	Scanner in = new Scanner(System.in);
    	rodLength = in.nextInt();
    	numCuts = in.nextInt();
    	for (int i = 0; i < numCuts; i++) {
    		cutPoints.add(in.nextInt());
    	}
    	
    	asgn401 a = new asgn401();
    	a.rodCut(rodLength, cutPoints);
    	System.out.println(a.result);
    	System.out.println(a.minCost);

//    	for (int el: a.cuts) {
//    		System.out.print(el + " ");
//    	}
    	System.out.println();
    	for (int i = 0; i < a.dp.length; i++) {
    		for (int j = 0; j < a.dp.length; j++) {
    			System.out.print(a.dp[i][j]+ " ");
    		}
    		System.out.println();
    	}
    }
}