import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

public class PointST<Value> {
    /*
    Point Set class implemented using Red Black Trees
     */
    private RedBlackBST<Point2D, Value> table;

    // construct an empty symbol table of points
    public PointST() {
        table = new RedBlackBST<Point2D, Value>();

    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return table.isEmpty();
    }

    // number of points
    public int size() {
        return table.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null) throw new IllegalArgumentException("");
        table.put(p, val);

    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        return table.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        return table.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return table.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("");
        RedBlackBST<Point2D, Value> temp = new RedBlackBST<>();
        for (Point2D p : table.keys()) {
            if (rect.contains(p)) {
                temp.put(p, table.get(p));
            }
        }
        return temp.keys();
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        Point2D nearestPoint = null;
        double minDistance = Double.MAX_VALUE;
        for (Point2D p1 : table.keys()) {
            if (nearestPoint == null) {
                nearestPoint = p1;
                minDistance = p.distanceTo(p1);
            }
            else if (!p1.equals(p)) {
                double distance = p.distanceTo(p1);
                if (distance < minDistance) {
                    nearestPoint = p1;
                    minDistance = distance;
                }
            }
        }
        return nearestPoint;
    }

    // unit testing (required)
    public static void main(String[] args) {
        PointST<Integer> st = new PointST<Integer>();
        Point2D p1 = new Point2D(1.0, 1.0);
        Point2D p2 = new Point2D(2.0, 2.0);
        Point2D p3 = new Point2D(3.0, 3.0);
        Point2D p4 = new Point2D(4.0, 4.0);
        st.put(p1, 1);
        st.put(p2, 2);
        st.put(p3, 3);
        st.put(p4, 4);
        RectHV rect = new RectHV(0.0, 0.0, 2.0, 2.0);
        StdOut.println(st.size());
        StdOut.println(st.isEmpty());
        StdOut.println(st.points());
        StdOut.println(st.range(rect));
        StdOut.println(st.get(p1));
        StdOut.println(st.contains(p1));
        StdOut.println(st.nearest(p2));
    }
}
