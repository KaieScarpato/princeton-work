public class Sierpinski {

    // Height of an equilateral triangle whose sides are of the specified length.
    public static double height(double length) {
        double h = length * Math.sqrt(3) / 2;
        return h;
    }

    // Draws a filled equilateral triangle whose bottom vertex is (x, y)
    // of the specified side length.
    public static void filledTriangle(double x, double y, double length) {
        double x1 = x - length / 2;
        double x2 = x + length / 2;
        double y1 = y + height(length);
        double y2 = y + height(length);

        double[] xArr = { x, x1, x2 };
        double[] yArr = { y, y1, y2 };

        StdDraw.filledPolygon(xArr, yArr);
    }

    // Draws a Sierpinski triangle of order n, such that the largest filled
    // triangle has bottom vertex (x, y) and sides of the specified length.
    public static void sierpinski(int n, double x, double y, double length) {
        if (n > 0) {
            filledTriangle(x, y, length);
            n = n - 1;
            sierpinski(n, x - (length / 2.0), y, length / 2);
            sierpinski(n, x + (length / 2.0), y, length / 2);
            sierpinski(n, x, y + height(length), length / 2);
        }
    }

    // Takes an integer command-line argument n;
    // draws the outline of an equilateral triangle (pointed upwards) of length 1;
    // whose bottom-left vertex is (0, 0) and bottom-right vertex is (1, 0); and
    // draws a Sierpinski triangle of order n that fits snugly inside the outline.
    public static void main(String[] args) {
        double length = 1.0;
        int n = Integer.parseInt(args[0]);
        StdDraw.line(0.0, 0.0, 1.0, 0.0);
        StdDraw.line(0.0, 0.0, 0.5, height(length));
        StdDraw.line(0.5, height(length), 1.0, 0.0);

        sierpinski(n, length / 2.0, 0, length / 2);
    }
}
