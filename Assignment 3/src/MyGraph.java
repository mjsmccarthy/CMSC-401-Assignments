import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * The following program implements a graph from a 2D-matrix (bitmap). Once constructed,
 * various operations can be done to the graph to gain more insight (traversals, connections,
 * out-degree, and hops).
 * 
 * Resources: 
 * 		GeeksforGeek - Depth First Search and Breath First Search
 * 
 * 		Introduction to Algorithms, Third Edition, Chapter 12: Binary Search Trees, by
 * 		Cormen, Leiserson, Rivest, and Stein.
 * 
 * @author Matthew McCarthy
 * @version 3/22/2019	Assignment 3, CMSC 401, Spring 2019
 *
 */
public class MyGraph {

	/**
	 * Main method to test the implementation of a MyGraph object.
	 * @param args - None
	 */
	public static void main(String[] args) {
		int[][] bitmap = { {1, 0, 0, 1, 1},
						   {0, 0, 1, 0, 1},
						   {0, 1, 1, 1, 0},
						   {0, 0, 1, 0, 1},
						   {0, 0, 1, 0 ,0} };

		MyGraph g = new MyGraph(bitmap.length);

		// Create graph from bitmap
		g.ititialize(bitmap);

		// Helper function to print the adjacency list
		g.printGraph(g);
		System.out.println();

		// Testing BFS 
		System.out.println("BFS starting at vertex 0: " + g.BFS(0));
		System.out.println("BFS starting at vertex 1: " + g.BFS(1));
		System.out.println("BFS starting at vertex 2: " + g.BFS(2));
		System.out.println("BFS starting at vertex 3: " + g.BFS(3));
		System.out.println("BFS starting at vertex 3: " + g.BFS(4) + "\n");

		// Testing DFS 
		System.out.println("DFS starting at vertex 0: " + g.DFS(0));
		System.out.println("DFS starting at vertex 1: " + g.DFS(1));
		System.out.println("DFS starting at vertex 2: " + g.DFS(2));
		System.out.println("DFS starting at vertex 3: " + g.DFS(3));
		System.out.println("DFS starting at vertex 3: " + g.DFS(4) + "\n");

		// Testing isConnected 
		System.out.println("Is vertex 0 connected to vertex 0: " + g.isConnected(0, 0));
		System.out.println("Is vertex 1 connected to vertex 1: " + g.isConnected(1, 1));
		System.out.println("Is vertex 0 connected to vertex 2: " + g.isConnected(0, 2));
		System.out.println("Is vertex 3 connected to vertex 1: " + g.isConnected(3, 1));
		System.out.println("Is vertex 1 connected to vertex 2: " + g.isConnected(1, 2));
		System.out.println("Is vertex 3 connected to vertex 4: " + g.isConnected(3, 4));
		System.out.println("Is vertex 4 connected to vertex 0: " + g.isConnected(4, 0));
		System.out.println("Is vertex 0 connected to vertex 3: " + g.isConnected(0, 3) + "\n");

		// Testing outDegree
		System.out.println("Out-degree of vertex 0: " + g.outDegree(0));
		System.out.println("Out-degree of vertex 1: " + g.outDegree(1));
		System.out.println("Out-degree of vertex 2: " + g.outDegree(2));
		System.out.println("Out-degree of vertex 3: " + g.outDegree(3));
		System.out.println("Out-degree of vertex 3: " + g.outDegree(4) + "\n");

		// Testing hops
		System.out.println("Number of hops between vertex 0 and 3: " + g.hops(0, 3));
		System.out.println("Number of hops between vertex 0 and 1: " + g.hops(0, 1));
		System.out.println("Number of hops between vertex 2 and 3: " + g.hops(2, 3));
		System.out.println("Number of hops between vertex 1 and 3: " + g.hops(1, 3));
		System.out.println("Number of hops between vertex 2 and 3: " + g.hops(2, 3));
		System.out.println("Number of hops between vertex 3 and 3: " + g.hops(3, 3));
		System.out.println("Number of hops between vertex 3 and 1: " + g.hops(3, 1));
		System.out.println("Number of hops between vertex 4 and 0: " + g.hops(4, 0));
		System.out.println("Number of hops between vertex 3 and 0: " + g.hops(3, 0));
		System.out.println("Number of hops between vertex 3 and 1: " + g.hops(3, 1));
	}

	private int V;									// Number of vertices
	private ArrayList<LinkedList<Integer>> adjList;	// Adjacency lists

	/**
	 * Default constructor that sets the number of vertices. Based on the amount of 
	 * vertices, an array of lists is constructed.
	 * @param V - The number of vertices in the graph
	 */
	public MyGraph(int V) {
		this.V = V;
		adjList = new ArrayList<LinkedList<Integer>>();

		// Constructs an array of lists based on the number of vertices passed.
		for (int i=0; i < V; i++) {
			adjList.add(new LinkedList<Integer>());
		}
	}

	/**
	 * Adds an edge between vertex v and vertex w.
	 * @param v - The source vertex
	 * @param w - The destination vertex
	 */
	private void addEdge(int v, int w) {
		adjList.get(v).add(w);
	}

	/**
	 * Constructs a graph based on an adjacency matrix. 
	 * @param adjacencyMatrix - A adjacency matrix containing only 0's and 1's
	 */
	public void ititialize(int[][] adjacencyMatrix) {
		for(int row = 0; row < adjacencyMatrix.length; row++) {
			for (int col = 0; col < adjacencyMatrix[row].length; col++) {
				if(adjacencyMatrix[row][col] == 1) {
					addEdge(row, col);
				}
			}
		}
	}

	/**
	 * Prints a given vertex and the vertices it's connected too.
	 * @param graph - A graph that uses an adjacency list to store the structure.
	 */
	public void printGraph(MyGraph graph) {
		for(int i = 0; i < graph.V; i++) {
			System.out.print("Vertex " + i + " is connected to: ");
			for (int el: graph.adjList.get(i)) {
				System.out.print(el + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Determines if there is an edge between two vertices (id1 and id2). If there is
	 * an edge, true is returned. Otherwise, false is returned.
	 * @param id1 - The starting vertex.
	 * @param id2 - The destination vertex.
	 * @return - Boolean: True if an edge exists between id1 and id2. Otherwise, false.
	 */
	public boolean isConnected(int id1, int id2) {
		for (int i = 0; i < adjList.size(); i++) {
			for (int el: adjList.get(i)) {
				if (i == id1 && el == id2) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns the out-degree of a given vertex.
	 * @param id - The vertex 
	 * @return - Integer: The out-degree of a given vertex.
	 */
	public int outDegree(int id) {
		int outDeg = 0;
		for (int i = 0; i < adjList.size(); i++) {
			if (i == id) {
				for (int j = 0; j < adjList.get(i).size(); j++) {
					outDeg++;
				}
			}
		}
		return outDeg;
	}

	/**
	 *  Returns the minimal number of hops from node1 to node2. If there is no
	 *  path between the two nodes, -1 is returned
	 * @param id1 - Starting node
	 * @param id2 - Ending node
	 * @return - Integer: Minimum number of hops from id1 to id2, if a path exists. If 
	 * 			 there is no path, -1 is returned.
	 */
	public int hops(int id1, int id2) {
		boolean visited[] = new boolean[V];				// Boolean array to check if a vertex was visited
		int distance[] = new int[V];					// Int array to keep track of the distance from the starting vertex to other vertices
		LinkedList<Integer> queue = new LinkedList<>(); // Queue to keep track of BFS traversal

		/*
		 * Initialize the distance array at position id1 to be zero. In addition, 
		 * mark the same position in the boolean array as visited. Add the id1 
		 * value to the queue.
		 */
		distance[id1] = 0;
		visited[id1] = true;
		queue.add(id1);


		/* 
		 * Test to see if the passed parameters are the same, in which case the number 
		 * of hops would be zero.
		 */
		if (adjList.get(id1) == adjList.get(id2)) {
			return distance[id1];
		}

		/*
		 * While the size of the queue is not zero, remove the first item in the 
		 * queue and search the corresponding adjacency list associated with that
		 * vertex. 
		 */
		while(queue.size() != 0) {
			int next = queue.pop();

			// For a given vertex, search it's adjacency list.
			for (int i = 0; i < adjList.get(next).size(); i++) {

				// If a value has been visited in the adjacency list, proceed to the next iteration.
				if (visited[adjList.get(next).get(i)]) {
					continue;
				}

				/*
				 * If a value has not been visited, set it's distance from the starting
				 * vertex. Add the not visited vertex to the queue and set it has visited.
				 */
				distance[adjList.get(next).get(i)] = distance[next] + 1;
				queue.add(adjList.get(next).get(i));
				visited[adjList.get(next).get(i)] = true;

				// If the vertex being currently searched contains the value we are looking for, then
				// return the value stored in the distance array at the position corresponding to id2
				if (adjList.get(next).get(i) == id2) {
					return distance[id2];
				}
			}
		}

		// If there is no path between id1 and id2, -1 is returned.
		return -1;
	}

	/**
	 * Returns a String containing the Breath First Search (BFS) of the graph starting
	 * at a given vertex.
	 * @param id - The starting vertex value
	 * @return - String: Contains the result of the BFS starting at a given vertex
	 */
	public String BFS(int id) {
		boolean visited[] = new boolean[V];	// Array to keep track of visited vertices
		LinkedList<Integer> queue = new LinkedList<>(); // Queue to add vertices too
		String result = ""; // String to be returned containing the result

		visited[id] = true; // Set the first vertex passed as visited
		queue.add(id); // Add it to the queue

		/*
		 * While the queue is not empty, remove the first item in the queue and
		 * add it to the string. Then traverse the adjacency list of that vertex,
		 * adding none visited values to the queue.
		 */
		while (queue.size() != 0) {
			id = queue.pop();
			result = result + id + " ";

			Iterator<Integer> i = adjList.get(id).listIterator();
			while (i.hasNext()) {
				int n = i.next();
				if (!visited[n]) {
					visited[n] = true;
					queue.add(n);
				}
			}
		}
		return result;
	}

	/**
	 * Returns a String containing the Depth First Search (DFS) of a graph starting 
	 * at a given vertex.
	 * @param id - The starting vertex value
	 * @return - String: Contains the result of the DFS starting at a given vertex
	 */
	public String DFS(int id) {
		boolean visited[] = new boolean[V]; // Array to keep track of visited vertices
		String result = DFSUtil(id, visited); // String to be returned
		return result;
	}

	/**
	 * Helper method to recursively determine the DFS of a graph.
	 * @param id - Starting vertex
	 * @param visited - Boolean array to indicate if a vertex was visited
	 * @return - A string containing the DFS starting at a given vertex.
	 */
	private String DFSUtil(int id, boolean visited[]) {
		visited[id] = true; // Set the passed id as visited
		String result = id + " "; // Add the value to the string

		Iterator<Integer> iter = adjList.get(id).listIterator(); 
		while(iter.hasNext()) {
			int n = iter.next();
			// If the next value has not been visited, concatenate the string with a recursive call using the new value
			if (!visited[n]) {
				result += DFSUtil(n, visited);
			}
		}
		return result;
	}
}
