
/** Starter code for AVL Tree
 */
 
// replace package name with your netid
package sxb220302;

import java.util.Deque;
import java.util.Comparator;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    AVLTree() {
		super();
    }

	/**
	 * helper method to get the height of a node
	 */
	private int height(BinarySearchTree.Entry<T> node) {
		return node == null ? 0 : node.height;
	}

	/**
	 * helper method to update the height of a node
	 */
	private void updateHeight(BinarySearchTree.Entry<T> node) {
		if(node == null) return;
		node.height = 1 + Math.max(height(node.left), height(node.right));
	}

	/**
	 * helper method to balance the tree
	 */
	private void balance(T x) {
		Deque<BinarySearchTree.Entry<T>> stack = this.pathStack;
		// update heights and balance the tree from the bottom up
		while(!stack.isEmpty()) {
			BinarySearchTree.Entry<T> node = stack.pop();
			updateHeight(node);

			int balanceCheck = balanceFactor(node);

			// Case 1 - Left Left
			if (balanceCheck > 1 && x.compareTo(node.left.element) < 0) {
				if (!stack.isEmpty()) {
					if (stack.peek().left == node) stack.peek().left = rightRotate(node);
					else stack.peek().right = rightRotate(node);
				} else {
					root = rightRotate(node);
				}
				break;
			}

			// Case 2 - Right Right
			if (balanceCheck < -1 && x.compareTo(node.right.element) > 0) {
				if (!stack.isEmpty()) {
					if (stack.peek().left == node) stack.peek().left = leftRotate(node);
					else stack.peek().right = leftRotate(node);
				} else {
					root = leftRotate(node);
				}
				break;
			}

			// Case 3 - Left Right
			if (balanceCheck > 1 && x.compareTo(node.left.element) > 0) {
				node.left = leftRotate(node.left);
				if (!stack.isEmpty()) {
					if (stack.peek().left == node) stack.peek().left = rightRotate(node);
					else stack.peek().right = rightRotate(node);
				} else {
					root = rightRotate(node);
				}
				break;
			}

			// Case 4 - Right Left
			if (balanceCheck < -1 && x.compareTo(node.right.element) < 0) {
				node.right = rightRotate(node.right);
				if (!stack.isEmpty()) {
					if (stack.peek().left == node) stack.peek().left = leftRotate(node);
					else stack.peek().right = leftRotate(node);
				} else {
					root = leftRotate(node);
				}
				break;
			}
		}
	}

	/**
	 * helper method to get the balance factor of a node
	 * helps identify which child is causing the imbalance
	 */
	private int balanceFactor(BinarySearchTree.Entry<T> node) {
		return node == null ? 0 : height(node.left) - height(node.right);
	}

	/**
	 * helper method to perform right rotation
	 */
	private Entry<T> rightRotate(BinarySearchTree.Entry<T> A) {
		BinarySearchTree.Entry<T> B = A.left;
		A.left = B.right;
		B.right = A;
		updateHeight(A);
		updateHeight(B);
		return B;
	}

	/**
	 * helper method to perform left rotation
	 */
	private Entry<T> leftRotate(BinarySearchTree.Entry<T> A) {
		BinarySearchTree.Entry<T> B = A.right;
		A.right = B.left;
		B.left = A;
		updateHeight(A);
		updateHeight(B);
		return B;
	}

	/**
	 * add method to add a new node to the AVL tree
	 * reuses the BST add and find methods
	 */
    @Override
    public boolean add(T x) {
		boolean added = super.add(x); // add new node to the tree using BST method
		if(added) balance(x); // if added, then balance the tree - takes O(log n) time
		return added;
    }
	
	//Optional. Complete for extra credit
	@Override
    public T remove(T x) {
		T value = super.remove(x);
		// if(value != null) balance(x);
		return value;
    }
	
	/**
	 *	verify if the tree is a valid AVL tree, that satisfies 
	 *	all conditions of BST, and the balancing conditions of AVL trees. 
	 *	In addition, do not trust the height value stored at the nodes, and
	 *	heights of nodes have to be verified to be correct.  Make your code
	 *  as efficient as possible. HINT: Look at the bottom-up solution to verify BST
	*/
	/**
	 * Result class to store the result of the verification
	 */
	class Result {
		boolean isBST;
		int height;
		T min, max;

		Result(boolean isBST, int height, T min, T max) {
			this.isBST = isBST;
			this.height = height;
			this.min = min;
			this.max = max;
		}
	}

	boolean verify(){
		return check(root).isBST;
	}

	/**
	 * helper method to verify if the tree is a valid AVL tree
	 */
	private Result check(BinarySearchTree.Entry<T> node) {
		if (node == null) return new Result(true, 0, null, null); // empty tree is a valid BST

		Result leftResult = check(node.left); // get result for left subtree
		Result rightResult = check(node.right); // get result for left subtree

		// validate if the current subtree is a valid BST
		boolean isBST = leftResult.isBST && rightResult.isBST
				&& (leftResult.max == null || node.element.compareTo(leftResult.max) > 0)
				&& (rightResult.min == null || node.element.compareTo(rightResult.min) < 0);

		// compute height
		int height = 1 + Math.max(leftResult.height, rightResult.height);
		boolean isBalanced = Math.abs(leftResult.height - rightResult.height) <= 1;

		T min = (leftResult.min != null) ? leftResult.min : node.element; // get min value of the subtree
		T max = (rightResult.max != null) ? rightResult.max : node.element;	// get max value of the subtree

		return new Result(isBST && isBalanced, height, min, max);
	}

	/**
	 * helper method to print the tree (for debugging)
	 */
	public void printTree(BinarySearchTree.Entry<T> node) {
		if (node != null) {
			printTree(node.left);
			System.out.print(" " + node.element + "(" + node.height + ") ");
			printTree(node.right);
		}
	}
}
