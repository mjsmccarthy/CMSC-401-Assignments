/**
 * Implementation of the quick-sort algorithm to sort an array of integers. The program takes
 * a list of values from the command line and then prints the sorted output to the console.
 * 
 * Resources:
 * 		Introduction to Algorithms, Third Edition, Chapter 7: Quicksort, by
 * 		Cormen, Leiserson, Rivest, and Stein.
 * 
 * @author Matthew McCarthy
 * @version 2/16/2019	Assignment 2, CMSC 401, Spring 2019
 */
public class MyQuicksort {
	/**
	 * Takes a list of numbers from the command line, sorts the numbers, and prints the sorted
	 * array to the console.
	 * @param args - Command line argument containing the array to be sorted.
	 */
	public static void main(String[] args) {
		int[] inputArr = new int[args.length];
		for (int i = 0; i < args.length; i++) {		 // Transfer the values from the command line to an integer array.
			inputArr[i] = Integer.parseInt(args[i]);
		}
		
		quickSort(inputArr, 0, inputArr.length - 1); // Call quickSort on the array
		
		for(int el: inputArr) {						 // Print the now sorted array
			System.out.print(el + " ");
		}
	}
	
	/**
	 * Takes an integer array, along with the start and end indices, and recursively 
	 * sorts the array in increasing order.
	 * @param array - An integer array.
	 * @param begin - The starting index of the array.
	 * @param end - The last index of the array.
	 */
	public static void quickSort(int array[], int begin, int end) {
		// Checks if the array is more than a single element.
		if (begin < end) {
			int partitionIndex = partition(array, begin, end);	// Determines the location of the partition index 
			quickSort(array, begin, partitionIndex - 1);		// Recursively calls quickSort on the left part of the array up to the int before the partition index
			quickSort(array, partitionIndex + 1, end);			// Recursively calls quickSort on the right part of the array from the value after the partition till the last index
		}
	}
	
	/**
	 * Chooses the first element in the array to be the pivot value. Elements less than the 
	 * pivot are placed on the left side of the array, while the values greater than the pivot
	 * are placed on the right. After searching the entire array, the pivot value is placed
	 * in it's correct location in the array. The index value of the pivot is then returned to 
	 * be used as the point to partition the array into smaller parts.
	 * @param a - An integer array.
	 * @param begin - The starting index of the array.
	 * @param end - The last index of the array.
	 * @return - The location of the pivot value in the array.
	 */
	private static int partition(int a[], int begin, int end) {
		int i = begin + 1;			// Let i point to the element before the pivot value.
		int pivot = a[begin];		// Pick the pivot to be the first element
		// Search all elements in the array, excluding the pivot element
		for (int j = begin + 1; j <= end; j++) {
			/*
			 * If an element is less than or equal to the pivot value, swap that element with
			 * the element located at position i and then increment i.
			 */
			if (a[j] <= pivot) {
				int temp = a[i];	
				a[i] = a[j];
				a[j] = temp;
				i++;
			}
		}
		// After going through the entire array, place the pivot value in the correct location.
		int temp = a[begin];
		a[begin] = a[i - 1];
		a[i - 1] = temp;
		return i - 1;
	}
}
