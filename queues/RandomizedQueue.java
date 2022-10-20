import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    /*
    Queue data structure, supports queue, and randomized dequeue.
    */

    public class Node {
        /*
        Node class, holds Item data and points to Nodes prev and next.
        */

        Item data;      // generic data
        Node next;      // pointer to next node in queue
        Node prev;      // pointer to prev node in queue
    }

    public class customIterator implements Iterator<Item> {
        /*
        Custom iterator used to iterate through deque.
        */

        private Node curr = start;        // curr node at start of iteration

        public boolean hasNext() {
            return curr != null;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item next = curr.data;
            curr = curr.next;
            return next;
        }
    }

    private Node start;  // node at start of queue
    private Node curr;   // current node at end of queue, used to enqueue new nodes
    private int size;    // size of queue

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        start = null;
        curr = null;
    }

    // returns true if the queue is empty, false otherwise
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    // returns the number of items on the randomized queue
    public int size() {
        return size;
    }

    // adds the item to end of queue
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Null Argument");
        size++;

        Node temp = new Node();
        temp.data = item;
        if (start == null) {
            start = temp;
            start.prev = null;
            start.next = null;
            curr = start;
        }
        else {
            temp.prev = curr;
            curr.next = temp;
            curr = temp;
        }
    }

    // removes and returns a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int n = StdRandom.uniform(1, size);
        Node temp = start;
        Node prev = null;
        Item item = null;
        for (int i = 0; i < n; i++) {
            prev = temp.prev;
            item = temp.data;
            temp = temp.next;
        }
        if (n == 1) {
            temp.prev = prev;
            start = temp;
        }
        else if (n == size) {
            prev.next = null;
            curr = prev;
        }
        else {
            temp.prev = prev;
            prev.next = temp;
        }
        size--;
        return item;
    }

    // returns a random item, but doesn't remove it
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int n = StdRandom.uniform(1, size);
        Node temp = start;
        Item item = null;
        for (int i = 0; i < n; i++) {
            item = temp.data;
            temp = temp.next;
        }
        return item;
    }

    // returns iterator
    public Iterator<Item> iterator() {
        return new customIterator();
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> t = new RandomizedQueue<Integer>();
        t.enqueue(1);
        t.enqueue(2);
        t.enqueue(3);
        t.enqueue(4);
        System.out.println("Size = " + t.size());
        for (int i : t) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(t.sample());
        System.out.println(t.dequeue());
        System.out.println("Size = " + t.size());
        for (int i : t) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

}
