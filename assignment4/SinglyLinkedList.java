/** @author rbk
 *  Singly linked list: for instructional purposes only
 *  Ver 1.0: 2018/08/21
 *  Ver 2.0: 2018/08/28: modified to be able to extend to DoublyLinkedList
 *  Entry class has generic type associated with it, to allow inheritance.
 *  We can now have a doubly linked list class DLL that has
 */

import java.util.Iterator;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {

    /** Class Entry holds a single node of the list */
    static class Entry<E> {
        E element;
        Entry<E> next;

        Entry(E x, Entry<E> nxt) {
            element = x;
            next = nxt;
        }
    }

    // Dummy header is used.  tail stores reference of tail element of list
    Entry<T> head, tail;
    int size;

    public SinglyLinkedList() {
        head = new Entry<>(null, null);
        tail = head;
        size = 0;
    }

    public Iterator<T> iterator() { return new SLLIterator(this); }

    protected class SLLIterator implements Iterator<T> {
        Entry<T> cursor, prev;
        boolean ready;  // is item ready to be removed?
        private final SinglyLinkedList<T> sll;

        SLLIterator(SinglyLinkedList<T> sll) {
            cursor = head;
            prev = null;
            ready = false;
            this.sll = sll;
        }

        public boolean hasNext() {
            return cursor.next != null;
        }

        // implement this method
        public T next() {
            this.prev = this.cursor;
            this.cursor = this.cursor.next;
            this.ready = true;
            return this.cursor.element;
        }

        // implement this method
        // Removes the current element (retrieved by the most recent next())
        // Remove can be called only if next has been called and the element has not been removed
        public void remove() {
            if(!ready) {
                // next() is not called before remove. So throw exeption
                throw new NoSuchElementException();
            }
            // complete the remaining part of the method
            this.prev.next = this.cursor.next;
            this.cursor = this.prev;

            // Dont forget to set the status of ready to an appropriate value
            this.ready = false;
            this.sll.size--;
        }
    }  // end of class SLLIterator

    // Add new elements to the end of the list
    public void add(T x) {
        add(new Entry<>(x, null));
    }

    public void add(Entry<T> ent) {
        tail.next = ent;
        tail = tail.next;
        size++;
    }

    public void printList() {
        System.out.print(this.size + ": ");
        for(T item: this) {
            System.out.print(item + " ");
        }

        System.out.println();
    }

    // optional
    // Rearrange the elements of the list by linking the elements at even index
    // followed by the elements at odd index. Implement by rearranging pointers
    // of existing elements without allocating any new elements.
    public void unzip() {
        if(size < 3) { // Too few elements. No change.
            return;
        }
        int pos = 0;
        Entry<T> curr = head.next;
        Entry<T> evenStart = null, evenEnd = null, oddStart = null, oddEnd = null;

        while(curr != null) {
            if(pos % 2 == 0) {
                if(pos == 0) {
                    evenStart = curr;
                    head.next = evenStart;
                }
                else evenEnd.next = curr;
                evenEnd = curr;
            } else {
                if(pos == 1) oddStart = curr;
                else oddEnd.next = curr;
                oddEnd = curr;
            }
            curr = curr.next;
            pos++;
        }

        if(evenEnd != null) evenEnd.next = oddStart;
        if(oddEnd != null) oddEnd.next = null;
    }

    public static void main(String[] args) throws NoSuchElementException {
        int n = 10;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
        for(int i=1; i<=n; i++) {
            lst.add(Integer.valueOf(i));
        }
        lst.printList();

        Iterator<Integer> it = lst.iterator();
        Scanner in = new Scanner(System.in);
        whileloop:
        while(in.hasNext()) {
            int com = in.nextInt();
            switch(com) {
                case 1:  // Move to next element and print it
                    if (it.hasNext()) {
                        System.out.println(it.next());
                    } else {
                        break whileloop;
                    }
                    break;
                case 2:  // Remove element
                    it.remove();
                    lst.printList();
                    break;
                default:  // Exit loop
                    break whileloop;
            }
        }
        lst.printList();
        lst.unzip();
        lst.printList();
    }
}

/* Sample input:
   1 2 1 2 1 1 1 2 1 1 2 0
   Sample output:
10: 1 2 3 4 5 6 7 8 9 10
1
9: 2 3 4 5 6 7 8 9 10
2
8: 3 4 5 6 7 8 9 10
3
4
5
7: 3 4 6 7 8 9 10
6
7
6: 3 4 6 8 9 10
6: 3 4 6 8 9 10
6: 3 6 9 4 8 10
*/