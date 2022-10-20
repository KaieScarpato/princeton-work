/*******************************************************************************
 *
 *  This is a template file for RingBuffer.java. It lists the constructors
 *  and methods you need to implement, along with descriptions of what they're
 *  supposed to do.
 *
 *  Note: it won't compile until you fill in the constructors and methods
 *        (or at least commment out the ones whose return type is non-void).
 *
 ******************************************************************************/

public class RingBuffer {
    private double[] rb;          // items in the buffer
    private int first;            // index for the next dequeue or peek
    private int last;             // index for the next enqueue
    private int size;             // number of items in the buffer

    // creates an empty ring buffer with the specified capacity
    public RingBuffer(int capacity) {
        this.rb = new double[capacity];
        this.size = capacity;
        this.first = 0;
        this.last = 0;
    }

    // return the capacity of this ring buffer
    public int capacity() {
        return size;
    }

    // return number of items currently in this ring buffer
    public int size() {
        int n = 0;
        for (int i = 0; i < rb.length; i++) {
            if (rb[i] != 0) {
                n += 1;
            }
        }
        return n;
    }

    // is this ring buffer empty (size equals zero)?
    // returns true if empty, false if otherwise
    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    // is this ring buffer full (size equals capacity)?
    // returns true if the size is equal to the capacity, false if otherwise
    public boolean isFull() {
        if (size() == capacity()) {
            return true;
        }
        else {
            return false;
        }
    }

    // adds item x to the end of this ring buffer
    public void enqueue(double x) {
        rb[last] = x;
        if (last == capacity() - 1) {
            last = 0;
        }
        else {
            last += 1;
        }
    }

    // deletes and returns the item at the front of this ring buffer
    public double dequeue() {
        double a = rb[first];
        rb[first] = 0;
        if (first == capacity() - 1) {
            first = 0;
        }
        else {
            first += 1;
        }
        return a;
    }

    // returns the item at the front of this ring buffer
    public double peek() {
        return rb[first];
    }

    // tests and calls every instance method in this class
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RingBuffer buffer = new RingBuffer(n);
        for (int i = 1; i <= n; i++) {
            buffer.enqueue(i);
        }
        double t = buffer.dequeue();
        buffer.enqueue(t);
        StdOut.println("Size after wrap-around is " + buffer.size());
        while (buffer.size() >= 2) {
            double x = buffer.dequeue();
            double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        StdOut.println(buffer.peek());
    }

}
