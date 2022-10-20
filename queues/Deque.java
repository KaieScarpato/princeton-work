import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    /*
    Queue data structure, supports queue to front and end of queue, and dequeue
    from front and end of queue.
    */

    public class Node {
        /*
        Node class, holds Item data and points to Nodes prev and next.
        */

        Item data;     // generic data
        Node prev;     // pointer to previous node in queue
        Node next;     // pointer to next node in queue
    }

    public class customIterator implements Iterator<Item> {
        /*
        Custom iterator used to iterate through deque.
        */

        private Node curr = front;     // node at start of iteration

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

    private Node front; // node to keep track of front of queue
    private Node back;  // node to keep track of back of deque
    private int size;   // size of deque

    // creates emptpy deque data structure
    public Deque() {
        size = 0;
        front = null;
        back = null;
    }

    // returns true if deque is empty, false if otherwise
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    // returns size of deque
    public int size() {
        return size;
    }

    // adds item to front of deque
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Null Argument");
        size++;

        Node temp = new Node();

        temp.data = item;
        temp.next = front;
        temp.prev = null;
        if (front != null) {
            front.prev = temp;
        }
        else {
            back = temp;
        }

        front = temp;
    }

    // adds item to end of deque
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Null Argument");
        size++;

        Node temp = new Node();

        temp.data = item;
        temp.prev = back;
        temp.next = null;

        if (back != null) {
            back.next = temp;
        }
        else {
            front = temp;
        }

        back = temp;
    }

    // removes first item and returns it
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Item first = front.data;
        front = front.next;

        if (front != null) {
            front.prev = null;
        }

        size--;
        return first;
    }

    // removes last item and returns it
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Item last = back.data;
        back = back.prev;

        if (back != null) {
            back.next = null;
        }

        size--;
        return last;
    }

    // returns iterator
    public Iterator<Item> iterator() {
        return new customIterator();
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();
        test.addFirst(2);
        test.addFirst(3);
        test.addLast(1);
        System.out.println("Items in Deque....");
        for (int i : test) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("---------------------------");
        System.out.println("Size = " + test.size());
        System.out.println();
        int one = test.removeFirst();
        int two = test.removeFirst();
        int three = test.removeLast();

        System.out.println("Items removed in order from Deque");
        System.out.print(one + " ");
        System.out.print(two + " ");
        System.out.println(three + " ");
        System.out.println("New Size = " + test.size());
    }
}
