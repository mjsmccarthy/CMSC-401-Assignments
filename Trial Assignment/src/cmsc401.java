import java.util.Scanner;

public class cmsc401 {
	public static void main(String[] args) {

		Scanner console = new Scanner(System.in);
		boolean done = false;
		
		while (!done) {
			String line = console.nextLine();
			String[] list = line.split(" ");
			
			if (line.equals("")) {
				done = true;
			}
			
			else {
				char command = line.charAt(0);
				
				if (command == 'A') {
					int[] values = new int[list.length];
					int startIndex = Integer.parseInt(list[list.length - 2]);
					int endIndex = Integer.parseInt(list[list.length - 1]);
					
					for (int i = 1; i <= list.length - 3; i++) {
						values[i] = Integer.parseInt(list[i]);
					}
					
					System.out.println(commandA(values, startIndex, endIndex));
				}
				
				if (command == 'B') {
					System.out.println(commandB(Integer.parseInt(list[1])));
				}
			}
			System.out.println();
		}
		console.close();
	}
	
	/**
	 * Takes an integer array and multiplies the numbers within a specific
	 * range when the start and end indices are also provided.
	 * @param arr - An array containing integer values.
	 * @param startIndex - The starting index within the array. Must be greater than 
	 * 					   or equal to 1.
	 * @param endIndex - The end index value with the array. Must be greater than or
	 * 					 equal to 1.
	 * @return - An integer value that is the multiplication of the values in the array 
	 *           when given a specific range.
	 */
	private static int commandA(int[] arr, int startIndex, int endIndex) {
		int result = 1;
		while (startIndex <= endIndex) {
			result *= arr[startIndex];
			startIndex++;
		}
		return result;
	}
	
	/**
	 * Returns the factorial of a given number between 1 and 100.
	 * @param n - The value to be used to determine the factorial.
	 * @return - The factorial of the value passed into the method.
	 */
	private static int commandB(int n) {
		int result = 0;
		if (n >= 1 && n <= 100) {
			if (n == 1) {
				result = 1;
			}
			else {
				result = n * commandB(n - 1);
			}
		}
		return result;
	}
}
