public class Tour {

    //Node object class
    private class Node {
        private Point p;
        private Node next;

        //creates a Node object
        private Node() {
            this.p = null;
            this.next = null;
        }

        //creates a Node object with point P
        private Node(Point p) {
            this.p = p;
            this.next = null;
        }
    }

    private Node node; //linked list


    // creates an empty tour
    public Tour() {
        this.node = null;
    }

    // creates the 4-point tour a->b->c->d->a (for debugging)
    public Tour(Point a, Point b, Point c, Point d) {
        Node n1 = new Node(a);
        Node n2 = new Node(b);
        Node n3 = new Node(c);
        Node n4 = new Node(d);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n1;
        node = n1;
    }

    // returns the number of points in this tour
    public int size() {
        int n = 0;
        if (node == null) {
            return n;
        }
        Node current = node;
        do {
            n += 1;
            current = current.next;
        } while (current != node);
        return n;
    }

    // returns the length of this tour
    public double length() {
        double distance = 0.0;
        for (int i = 0; i < size(); i++) {
            distance += node.p.distanceTo(node.next.p);
            node = node.next;
        }
        return distance;
    }

    // returns a string representation of this tour
    public String toString() {
        String str = "";
        if (node == null) {
            return str;
        }
        else {
            for (int i = 0; i < size(); i++) {
                str += node.p.toString();
                node = node.next;
                if (i < size() - 1) {
                    str += "\n";
                }
            }
        }
        return str;
    }

    // draws this tour to standard drawing
    public void draw() {
        if (node == null) {
            return;
        }
        else {
            for (int i = 0; i < size(); i++) {
                node.p.drawTo(node.next.p);
                node = node.next;
            }
        }
    }

    // inserts p using the nearest neighbor heuristic
    public void insertNearest(Point p) {
        if (node == null) {
            Node n = new Node(p);
            n.next = n;
            node = n;
            return;
        }
        double dist = Double.POSITIVE_INFINITY;
        Node a = new Node();
        Node b = new Node();
        Node c = new Node(p);
        for (int i = 0; i < size(); i++) {
            double d = node.p.distanceTo(p);
            if (dist > d) {
                dist = d;
                a = node;
                b = node.next;
            }
            node = node.next;
        }
        a.next = c;
        c.next = b;
        node = a;
    }

    // inserts p using the smallest increase heuristic
    public void insertSmallest(Point p) {
        if (node == null) {
            Node n = new Node(p);
            n.next = n;
            node = n;
            return;
        }
        double dist = Double.POSITIVE_INFINITY;
        Node a = new Node();
        Node b = new Node();
        Node c = new Node(p);
        for (int i = 0; i < size(); i++) {
            double l = node.p.distanceTo(p) + node.next.p.distanceTo(p) - node.p
                    .distanceTo(node.next.p);
            if (dist > l) {
                dist = l;
                a = node;
                b = node.next;
            }
            node = node.next;
        }
        a.next = c;
        c.next = b;
        node = a;
    }

    // tests this class by calling all constructors and instance methods
    public static void main(String[] args) {
        // define 4 points, corners of a square
        Point a = new Point(1.0, 1.0);
        Point b = new Point(1.0, 4.0);
        Point c = new Point(4.0, 4.0);
        Point d = new Point(4.0, 1.0);

        // create the tour a -> b -> c -> d -> a
        Tour squareTour = new Tour(a, b, c, d);
        int size = squareTour.size();
        StdOut.println("# of points = " + size);

        double length = squareTour.length();
        StdOut.println("Tour length = " + length);

        StdOut.println(squareTour);

        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);

        Point e = new Point(5.0, 6.0);
        squareTour.insertSmallest(e);
        StdOut.println(squareTour);
        squareTour.draw();

    }
}
