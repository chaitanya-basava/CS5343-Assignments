
/** Starter code for AVL Tree
 */
 
// replace package name with your netid
package sxb220302;

import java.util.Comparator;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        int height;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 0;
        }
    }

    AVLTree() {
	super();
    }

	// TO DO
    @Override
    public boolean add(T x) {
	return super.add(x);
    }
	
	//Optional. Complete for extra credit
	@Override
    public T remove(T x) {
	return super.remove(x);
    }
	
	/** TO DO
	 *	verify if the tree is a valid AVL tree, that satisfies 
	 *	all conditions of BST, and the balancing conditions of AVL trees. 
	 *	In addition, do not trust the height value stored at the nodes, and
	 *	heights of nodes have to be verified to be correct.  Make your code
	 *  as efficient as possible. HINT: Look at the bottom-up solution to verify BST
	*/
	boolean verify(){
	return false;
	}
}

