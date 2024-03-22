/**
 * @author rbk, sa
 * Binary search tree (starter code)
 **/

// replace package name with your netid
package sxb220302;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Deque;
import java.util.ArrayDeque;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        int height;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }

    Entry<T> root;
    int size;
    // define stack - using ArrayDeque as a stack for storing the path traversed in the tree
    public Deque<Entry<T>> pathStack = new ArrayDeque<>();

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    /**
     * find method to start searching from root of the tree
     */
    public Entry<T> find(T x) {
        this.pathStack.clear();
        return find(root, x);
    }

    /**
     * find method to start searching from a specific node of the tree
     */
    private Entry<T> find(Entry<T> node, T x) {
        if(node == null || node.element.equals(x)) return node;
        else {
            while (true) {
                int cmp = x.compareTo(node.element);
                if(cmp == 0) {
                    break;
                }
                else if (cmp < 0) {
                    if(node.left == null) break;
                    this.pathStack.push(node);
                    node = node.left;
                }
                else {
                    if(node.right == null) break;
                    this.pathStack.push(node);
                    node = node.right;
                }
            }
        }
        return node;
    }

    /**
     * contains method to check if the element is present in the tree
     */
    public boolean contains(T x) {
        Entry<T> node = find(x);
        if(node == null || !node.element.equals(x)) return false;
        return true;
    }


    /**
     * add method to add an element to the tree if not already present
     */
    public boolean add(T x) {
        if(size==0) {
            root = new Entry<T>(x, null, null);
        }
        else {
            Entry<T> parent = find(x);
            int cmp = x.compareTo(parent.element);
            if(cmp == 0) {
                return false;
            } else if(cmp < 0) {
                parent.left = new Entry<T>(x, null, null);
            } else {
                parent.right = new Entry<T>(x, null, null);
            }
        }
        size++;
        return true;
    }

    /**
     * remove method to remove an existing element from the tree
     */
    public T remove(T x) {
        if(size == 0) {
            return null;
        }
        Entry<T> node = find(x);
        if(!node.element.equals(x)) {
            return null;
        }
        if(node.left == null || node.right == null) {
            splice(node);
        } else {
            this.pathStack.push(node);
            Entry<T> minRight = this.find(node.right, x);
            node.element = minRight.element;
            splice(minRight);
        }
        size--;
        return x;
    }

    /**
     * helper splice method for remove operation
     */
    private void splice(Entry<T> node) {
        Entry<T> parent = this.pathStack.peek();
        Entry<T> child = node.left == null ? node.right : node.left;
        if(parent == null) {
            root = child;
        } else if(parent.left == node) {
            parent.left = child;
        } else {
            parent.right = child;
        }
    }



// Start of Optional problems

    /** Optional problem : Iterate elements in sorted order of keys
     Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
        return null;
    }

    // Optional problem
    public T min() {
        return null;
    }

    public T max() {
        return null;
    }

    // Optional problem.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

   // Optional: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];
        /* write code to place elements in array here */
        return arr;
    }
	
// End of Optional problems

    public static void main(String[] args) throws FileNotFoundException {
        BinarySearchTree<Long> bst = new BinarySearchTree<>();
        Scanner sc;
        if (args.length > 0) {
            File file = new File(args[0]);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        String operation = "";
        long operand = 0;
        int modValue = 999983;
        long result = 0;
        // Initialize the timer
        Timer timer = new Timer();

        while (!((operation = sc.next()).equals("End"))) {
            switch (operation) {
                case "Add": {
                    operand = sc.nextInt();
                    if (bst.add(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Remove": {
                    operand = sc.nextInt();
                    if (bst.remove(operand) != null) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Contains": {
                    operand = sc.nextInt();
                    if (bst.contains(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
            }
//            System.out.print(result + " " + operand + " " + operation + " ");
//            bst.printTree();
        }

        // End Time
        timer.end();

        System.out.println(result);
        System.out.println(timer);
    }


    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }
}




