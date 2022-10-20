import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeST {
    /*
    Kd Tree with two dimensions, orientation flips at each level,
    if orientation is vertical, x elements of points are compared,
    if orientation is horizontal, y elements of points are compared.
     */

    private class Node {
        /*
        Node class used to implement Kd Tree
         */

        private Point2D p;           // point, has x and y values
        private Node left;           // left child of node
        private Node right;          // right child of node
        private boolean orientation; // true if vertical, false if horizontal
        private RectHV rect;         // rectangle node resides in

        public Node(Point2D p, RectHV rect, boolean orientation) {
            this.p = p;
            this.left = null;
            this.right = null;
            this.rect = rect;
            this.orientation = orientation;
        }
    }

    private Node root;      // root node
    private int size;       // size of kd tree

    // initializes empty tree
    public KdTreeST() {
        root = null;
        size = 0;
    }

    // true if tree is empty, false otherwise
    public boolean isEmpty() {
        return size() == 0;
    }

    // size of tree
    public int size() {
        return size;
    }

    // inserts point p into tree, inserts it at the root if root == null
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        if (root == null) {
            size++;
            RectHV rect = new RectHV(0, 0, 1, 1);
            root = new Node(p, rect, true);
        }
        if (!contains(p)) {
            root = insert(root, p, root.rect, root.orientation);
        }
    }

    // helper class to insert point p into tree, at horizontal levels, if
    // node.p.x() < p.x() p is inserted to right of node, left otherwise
    // at vertival levels, if node.p.y() < p.y() p is inserted to right of node,
    // left otherwise
    private Node insert(Node node, Point2D p, RectHV rect, boolean orientation) {
        if (p == null || rect == null) throw new IllegalArgumentException("");
        if (node == null) {
            size++;
            return new Node(p, rect, orientation);
        }

        boolean nextOrientation = !orientation;
        if (orientation) {
            if (p.x() < node.p.x()) {
                RectHV newRect = new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax());
                node.left = insert(node.left, p, newRect, nextOrientation);
            }
            else if (p.x() >= node.p.x()) {
                RectHV newRect = new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
                node.right = insert(node.right, p, newRect, nextOrientation);
            }
        }
        else {
            if (p.y() < node.p.y()) {
                RectHV newRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y());
                node.left = insert(node.left, p, newRect, nextOrientation);
            }
            else if (p.y() >= node.p.y()) {
                RectHV newRect = new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax());
                node.right = insert(node.right, p, newRect, nextOrientation);
            }
        }

        return node;
    }

    // true if p is in the tree, false otherwise
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        return contains(root, p);
    }

    // helper class for contains
    private boolean contains(Node node, Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        while (node != null) {
            if (node.orientation) {
                if (p.x() < node.p.x()) {
                    node = node.left;
                }
                else if (p.x() > node.p.x()) {
                    node = node.right;
                }
                else if (p.y() == node.p.y()) {
                    return true;
                }
                else {
                    node = node.right;
                }
            }
            else {
                if (p.y() < node.p.y()) {
                    node = node.left;
                }
                else if (p.y() > node.p.y()) {
                    node = node.right;
                }
                else if (p.x() == node.p.x()) {
                    return true;
                }
                else {
                    node = node.right;
                }
            }
        }
        return false;
    }

    // returns an iterable of all points in the tree using bfs
    public Iterable<Point2D> points() {
        Queue<Point2D> pointQueue = bfs();

        return pointQueue;
    }

    // returns an iterable of all points in a rectangle using bfs
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("");
        Queue<Point2D> temp = bfs();
        Queue<Point2D> rangeQueue = new Queue<Point2D>();

        for (Point2D n : temp) {
            if (rect.contains(n)) {
                rangeQueue.enqueue(n);
            }
        }

        return rangeQueue;
    }

    // returns nearest point to p
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("");
        Queue<Point2D> temp = bfs();
        Point2D nearestPoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Point2D p1 : temp) {
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

    // draws the tree
    public void draw() {
        draw(root, root.orientation);
    }

    // helper class to draw tree
    private void draw(Node node, boolean orientation) {
        if (node != null) {
            if (orientation) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(0.005);
                StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(0.005);
                StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.02);
            StdDraw.point(node.p.x(), node.p.y());
            if (node.left != null) {
                draw(node.left, node.left.orientation);
            }
            if (node.right != null) {
                draw(node.right, node.right.orientation);
            }
        }
    }

    // bfs helper class
    private Queue<Point2D> bfs() {
        Queue<Node> bfs = new Queue<Node>();
        Queue<Point2D> pointQueue = new Queue<Point2D>();

        bfs.enqueue(root);

        while (!bfs.isEmpty()) {

            Node node = bfs.dequeue();
            pointQueue.enqueue(node.p);

            if (node.left != null) {
                bfs.enqueue(node.left);
            }

            if (node.right != null) {
                bfs.enqueue(node.right);
            }
        }
        return pointQueue;
    }

    public static void main(String args[]) {
        KdTreeST kd = new KdTreeST();
        RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
        kd.insert(new Point2D(0.7, 0.2));
        kd.insert(new Point2D(0.5, 0.4));
        kd.insert(new Point2D(0.2, 0.3));
        kd.insert(new Point2D(0.4, 0.7));
        kd.insert(new Point2D(0.9, 0.6));
        kd.draw();
        StdOut.println(kd.size());
        StdOut.println(kd.points());
        StdOut.println(kd.range(rect));
        StdOut.println(kd.nearest(new Point2D(0.2, 0.3)));

    }
}
