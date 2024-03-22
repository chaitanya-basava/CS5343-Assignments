
/** Starter code for AVL Tree
 */
 
// replace package name with your netid
package sxb220302;

import java.util.Comparator;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    AVLTree() {
		super();
    }

	private void updateHeight(BinarySearchTree.Entry<T> node) {
		if (node.left == null && node.right == null) {
			node.height = 0;
		}
		else if (node.left == null) {
			node.height = 1 + node.right.height;
		}
		else if (node.right == null) {
			node.height = 1 + node.left.height;
		}
		else {
			node.height = 1 + Math.max(node.left.height, node.right.height);
		}
	}

	private void balance() {
		while(!this.pathStack.isEmpty()) {
			BinarySearchTree.Entry<T> node = this.pathStack.pop();
			updateHeight(node);
			BinarySearchTree.Entry<T> parent = this.pathStack.peek();

			int balanceCheck = balanceFactor(node);
			if(balanceCheck < -1) {
				if (balanceFactor(node.left) <= 0) {
					balanceLL(node, parent); // Perform LL rotation
				} else {
					balanceLR(node, parent); // Perform LR rotation
				}
			} else if(balanceCheck > 1) {
				if (balanceFactor(node.right) >= 0) {
					balanceRR(node, parent); // Perform RR rotation
				} else {
					balanceRL(node, parent); // Perform RL rotation
				}
			}
		}
	}

	private int balanceFactor(BinarySearchTree.Entry<T> node) {
		return (node == null) ? 0 : (node.left == null ? -1 : node.left.height) - (node.right == null ? -1 : node.right.height);
	}

	/** Balance LL (see Figure 26.3) */
	private void balanceLL(BinarySearchTree.Entry<T> A, BinarySearchTree.Entry<T> parentOfA) {
		BinarySearchTree.Entry<T> B = A.left;

		if (A == root) {
			root = B;
		} else {
			if (parentOfA.left == A) {
				parentOfA.left = B;
			} else {
				parentOfA.right = B;
			}
		}

		A.left = B.right; // Make T2 the left subtree of A
		B.right = A; // Make A the left child of B
		updateHeight(A);
		updateHeight(B);
	}

	/** Balance LR (see Figure 26.5) */
	private void balanceLR(BinarySearchTree.Entry<T> A, BinarySearchTree.Entry<T> parentOfA) {
		BinarySearchTree.Entry<T> B = A.left; // A is left-heavy
		BinarySearchTree.Entry<T> C = B.right; // B is right-heavy

		if (A == root) {
			root = C;
		} else {
			if (parentOfA.left == A) {
				parentOfA.left = C;
			} else {
				parentOfA.right = C;
			}
		}

		A.left = C.right; // Make T3 the left subtree of A
		B.right = C.left; // Make T2 the right subtree of B
		C.left = B;
		C.right = A;

		// Adjust heights
		updateHeight(A);
		updateHeight(B);
		updateHeight(C);
	}

	/** Balance RR (see Figure 26.4) */
	private void balanceRR(BinarySearchTree.Entry<T> A, BinarySearchTree.Entry<T> parentOfA) {
		BinarySearchTree.Entry<T> B = A.right; // A is right-heavy and B is right-heavy

		if (A == root) {
			root = B;
		} else {
			if (parentOfA.left == A) {
				parentOfA.left = B;
			} else {
				parentOfA.right = B;
			}
		}

		A.right = B.left; // Make T2 the right subtree of A
		B.left = A;
		updateHeight(A);
		updateHeight(B);
	}

	/** Balance RL (see Figure 26.6) */
	private void balanceRL(BinarySearchTree.Entry<T> A, BinarySearchTree.Entry<T> parentOfA) {
		BinarySearchTree.Entry<T> B = A.right; // A is right-heavy
		BinarySearchTree.Entry<T> C = B.left; // B is left-heavy

		if (A == root) {
			root = C;
		} else {
			if (parentOfA.left == A) {
				parentOfA.left = C;
			} else {
				parentOfA.right = C;
			}
		}

		A.right = C.left; // Make T2 the right subtree of A
		B.left = C.right; // Make T3 the left subtree of B
		C.left = A;
		C.right = B;

		// Adjust heights
		updateHeight(A);
		updateHeight(B);
		updateHeight(C);
	}

	// TO DO
    @Override
    public boolean add(T x) {
		boolean added = super.add(x);
		if(added) balance();
		return added;
    }
	
	//Optional. Complete for extra credit
	@Override
    public T remove(T x) {
		T value = super.remove(x);
		if(value != null) balance();
		return value;
    }
	
	/** TO DO
	 *	verify if the tree is a valid AVL tree, that satisfies 
	 *	all conditions of BST, and the balancing conditions of AVL trees. 
	 *	In addition, do not trust the height value stored at the nodes, and
	 *	heights of nodes have to be verified to be correct.  Make your code
	 *  as efficient as possible. HINT: Look at the bottom-up solution to verify BST
	*/
	boolean verify(){
		return isBST(root) && isBalanced(root) != -1;
	}

	private boolean isBST(BinarySearchTree.Entry<T> node) {
		if (node == null) return true;
		if (node.left != null && node.left.element.compareTo(node.element) > 0) return false;
		if (node.right != null && node.right.element.compareTo(node.element) < 0) return false;
		return isBST(node.left) && isBST(node.right);
	}

	private int isBalanced(BinarySearchTree.Entry<T> node) {
		if (node == null) return 0;
		int leftHeight = isBalanced(node.left);
		int rightHeight = isBalanced(node.right);
		if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) return -1;
		return 1 + Math.max(leftHeight, rightHeight);
	}
}
