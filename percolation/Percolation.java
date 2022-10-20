import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;             // percolation grid
    private int numOpen;              // number of open sites
    private int length;               // number of rows/cols in grid
    private WeightedQuickUnionUF uf;  // tracks whether or not system percolates
    private WeightedQuickUnionUF buf; // tracks backwash, stops it from filling
    private int top;                  // top of grid
    private int bottom;               // bottom of grid


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Invalid Input");

        this.grid = new int[n][n];
        this.numOpen = 0;
        this.length = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.buf = new WeightedQuickUnionUF(n * n + 1);
        this.top = n * n;
        this.bottom = n * n + 1;
    }

    // helper function, validates index
    public boolean valid(int row, int col) {
        return (!(row < 0 || col < 0 || row > length - 1 || col > length - 1));
    }

    // helper function, converts 2D cords to 1D
    private int convert(int row, int col) {
        return (length * row + col);
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!valid(row, col)) throw new IllegalArgumentException("Invalid Input");
        else {
            if (grid[row][col] != 1) {
                grid[row][col] = 1;
                numOpen += 1;
            }

            if (row == 0) {
                uf.union(convert(row, col), top);
                buf.union(convert(row, col), top);
            }

            if (row == this.length - 1) {
                uf.union(convert(row, col), bottom);
            }

            if (valid(row, col - 1)) {
                if (isOpen(row, col - 1)) {
                    uf.union(convert(row, col), convert(row, col - 1));
                    buf.union(convert(row, col), convert(row, col - 1));
                }
            }

            if (valid(row, col + 1)) {
                if (isOpen(row, col + 1)) {
                    uf.union(convert(row, col), convert(row, col + 1));
                    buf.union(convert(row, col), convert(row, col + 1));
                }
            }

            if (valid(row + 1, col)) {
                if (isOpen(row + 1, col)) {
                    uf.union(convert(row, col), convert(row + 1, col));
                    buf.union(convert(row, col), convert(row + 1, col));
                }
            }

            if (valid(row - 1, col)) {
                if (isOpen(row - 1, col)) {
                    uf.union(convert(row, col), convert(row - 1, col));
                    buf.union(convert(row, col), convert(row - 1, col));
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!valid(row, col)) throw new IllegalArgumentException("Invalid Input");
        else {
            return (grid[row][col] == 1);
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!valid(row, col)) throw new IllegalArgumentException("Invalid Input");
        else {
            return (buf.find(convert(row, col)) == buf.find(top));
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return (uf.find(top) == uf.find(bottom));
    }

    public static void main(String[] args) {
        StdOut.println("Creating new Percolation of n = 2");
        Percolation p = new Percolation(2);
        StdOut.println("Opening (0, 1)");
        p.open(0, 1);
        StdOut.println(String.format("isOpen = true %b", p.isOpen(0, 1)));
        StdOut.println(String.format("isFull = true %b", p.isFull(0, 1)));
        StdOut.println("Opening (1, 1)");
        p.open(1, 1);
        StdOut.println(String.format("isFull = true %b", p.isFull(0, 1)));
        StdOut.println("Testing for false open statement at (0, 1)");
        StdOut.println(String.format("isOpen = false %b", p.isOpen(1, 0)));
        StdOut.println(String.format("numberOfOpenSites = 2 %b",
                                     p.numberOfOpenSites() == 2));
        StdOut.println(String.format("Percolates = true %b", p.percolates()));
    }
}
