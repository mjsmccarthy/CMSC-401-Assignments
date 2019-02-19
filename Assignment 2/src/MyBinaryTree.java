/**
 * Program takes a list of integer values from the command line and constructs a
 * binary search tree from those values. After creating the tree, the values are printed
 * to the console using in-order traversal.
 * 
 * Resources:
 * 		Introduction to Algorithms, Third Edition, Chapter 12: Binary Search Trees, by
 * 		Cormen, Leiserson, Rivest, and Stein.
 * 
 * @author Matthew McCarthy
 * @version 2/16/2019	Assignment 2, CMSC 401, Sprint 2019
 */
public class MyBinaryTree {
	/**
	 * Main method to parse the command line argument, construct a binary search tree, and
	 * print the tree to the console using an in-order traversal.
	 * @param args - A sequence of integer values separated by spaces.
	 */
	public static void main(String[] args) {
		MyBinaryTree tree = new MyBinaryTree();
		// Loops through the command line argument and adds each value to the BST.
		for (String s: args) {
			tree.add(Integer.parseInt(s));
		}
		System.out.println(tree.inOrder());
	}
	
	// Instance variable that represents the root of the tree.
	private BTNode root;
	
	/**
	 * Default constructor that sets the root of the tree equal to null.
	 */
	public MyBinaryTree() {
		root = null;
	}
	
	/**
	 * A method to check if a BST is empty. 
	 * @return - Boolean: True if the tree is not empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * Searches the BST for a given number. If the tree contains the value, the method
	 * returns true. Otherwise the method returns false.
	 * @param num - An integer value.
	 * @return - Boolean: True if the tree contains a given value, false otherwise.
	 */
	public boolean contains(int num) {
		BTNode currentNode = getNode(num);
		if (currentNode != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Adds a new integer value to the BST. 
	 * @param newNum - The value to be added to the tree.
	 */
	public void add(int newNum) {
		BTNode parentNode = null;					// Parent node
		BTNode rootNode = root;						// Root node
		BTNode nodeToInsert = new BTNode(newNum);	// Node to be inserted
		
		//While rootNode is not null, determine where to place the new node.
		while (rootNode != null) {
			parentNode = rootNode;					// Sets the parent node equal to the root.
			/*
			 * If the number to add is less than or equal to the value at the root, 
			 * move to the left subtree. Otherwise move to the right subtree.
			 */
			if (newNum <= rootNode.key) {			
				rootNode = rootNode.left;
			}
			else {
				rootNode = rootNode.right;
			}
		}
		nodeToInsert.parent = parentNode;			//Sets the parent of nodeToInsert to be parentNode.
		/*
		 * If the parentNode is null, then the noteToInsert is the root of the tree.
		 * If the nodeToInsert is less than or equal to the parent, add the node to
		 * the left subtree. Otherwise, add it to the right subtree.
		 */
		if (parentNode == null) {
			root = nodeToInsert;					//Tree was empty, therefore the root is the new node.
		}
		else if(nodeToInsert.key <= parentNode.key) {
			parentNode.left = nodeToInsert;
		}
		else {
			parentNode.right = nodeToInsert;
		}
	}
	
	/**
	 * Private helper method to find the node containing a specific value. 
	 * @param num - The value to find in the tree.
	 * @return BTNode - The node containing a specific value. If the tree does not
	 * contain the value, the node is returned as null.
	 */
	private BTNode getNode(int num) {
		BTNode currentNode = root;
		while (currentNode != null && num != currentNode.key) {
			if (num < currentNode.key) {
				 currentNode = currentNode.left;
			}
			else {
				currentNode = currentNode.right;
			}
		}
		return currentNode;
	}
	
	/**
	 * Deletes a specific value in the tree.
	 * @param delNum - The value to delete.
	 * @return - Boolean: True if the number was removed from the tree. False if the 
	 * number was not present.
	 */
	public boolean delete(int delNum) {
		BTNode nodeToRemove = getNode(delNum);
		if (nodeToRemove == null) {
			return false;
		}
		else {
			deleteNode(nodeToRemove);
			return true;
		}
	}
	
	/**
	 * Private helper method. Removes the desired node and reconnects the tree to maintain
	 * it's proper structure.
	 * @param nodeToRemove - The node that contains the value to remove.
	 */
	private void deleteNode (BTNode nodeToRemove) {
		/*
		 * If the tree as no left child, replace the node to remove with the right
		 * child.
		 */
		if (nodeToRemove.left == null) {
			transplant(nodeToRemove, nodeToRemove.right);
		}
		/*
		 * If the tree has not right child, replace the node to remove with the left
		 * child.
		 */
		else if (nodeToRemove.right == null) {
			transplant(nodeToRemove, nodeToRemove.left);
		}
		/*
		 * If the node to remove has both a left and right child, determine the successor
		 * (in this case y) to the node to remove. If y is the right child to the node
		 * to remove, replace nodeToRemove with y. Otherwise, replace y by its own right
		 * child and then replace nodeToRemove with y. 
		 */
		else {
			BTNode y = minimum(nodeToRemove.right);
			if (y.parent != nodeToRemove) {
				transplant(y, y.right);
				y.right = nodeToRemove.right;
				y.right.parent = y;
			}
			transplant(nodeToRemove, y);
			y.left = nodeToRemove.left;
			y.left.parent = y;
		}
	}
	
	/**
	 * A private helper method to replace one subtree as a child of its parent with
	 * another subtree. 
	 * @param u - The node that is going to be replaced.
	 * @param v - The node that will take the place of the node rooted at u.
	 */
	private void transplant(BTNode u, BTNode v) {
		/*
		 * Checks if the node u is the root of the tree.
		 */
		if (u.parent == null) {
			root = v;
		}
		/*
		 * Updates the node u if it is a left child.
		 */
		else if (u == u.parent.left) {
			u.parent.left = v;
		}
		/*
		 * Updates the node u if it is a right child.
		 */
		else {
			u.parent.right = v;
		}
		/*
		 * Updates the parent of v to equal the parent of u if v is not null.
		 */
		if (v != null) {
			v.parent = u.parent;
		}
	}
	
	/**
	 * A private helper method that finds the node that contains the minimum value
	 * inside the tree.
	 * @param node - The node to start the search for the minimum from.
	 * @return BTNode - The node that contains the minimum value in the tree.
	 */
	private BTNode minimum(BTNode node) {
		BTNode currentNode = node;
		while (currentNode.left != null) {
			currentNode = currentNode.left;
		}
		return currentNode;
	}
	
	/**
	 * Returns the result of traversing the tree using pre-order traversal.
	 * @return String - A string containing the result of a pre-order traversal.
	 */
	public String preOrder() {
		return preOrderHelper(root);
	}
	
	/**
	 * A helper method that returns the result of a pre-order traversal as a string.
	 * @param root - The root node of the tree.
	 * @return String - A string containing the result of a pre-order traversal.
	 */
	private String preOrderHelper(BTNode root) {
		if (root == null) {
			return "";
		}
		return root.key + " " + preOrderHelper(root.left) + preOrderHelper(root.right);
	}
	
	/**
	 * Returns the result of traversing the tree using in-order traversal.
	 * @return String - A string containing the result of an in-order traversal.
	 */
	public String inOrder() {
		return inorderHelper(root);
	}
	
	/**
	 * A helper method that returns the result of an in-order traversal as a string.
	 * @param root - The root node of the tree.
	 * @return String - A string containing the result of an in-order traversal.
	 */
	private String inorderHelper(BTNode root) {
	
		if (root == null) {
			return "";
		}
		return inorderHelper(root.left) + root.key + " " + inorderHelper(root.right); 
	}
	
	/**
	 * Returns the result of traversing the tree using post-order traversal.
	 * @return String - A string containing the result of a post-order traversal.
	 */
	public String postOrder() {
		return postOrderHelper(root);
	}
	
	/**
	 * A helper method that returns the result of a post-order traversal as a string.
	 * @param root - The root node of the tree.
	 * @return String - A string containing the result of a post-order traversal.
	 */
	private String postOrderHelper(BTNode root) {
		if (root == null) {
			return "";
		}
		return postOrderHelper(root.left) + postOrderHelper(root.right) + root.key + " ";  
	}
	
	/**
	 * A private inner class, containing the necessary data each node inside the tree
	 * contains.
	 */
	private class BTNode {
		// The instance variables for each node
		private int key;
		private BTNode parent;
		private BTNode left;
		private BTNode right;
		
		/**
		 * Creates a node containing a given value. Initially, the left, right, and
		 * parent instance variables are all set to null.
		 * @param num - The value to store inside a node.
		 */
		private BTNode(int num) {
			key = num;
			left = null;
			right = null;
			parent = null;
		}
	}
}
