public class Art {

    //sets the standard drawing
    private static void setup() {
        StdDraw.setXscale(0, 512);
        StdDraw.setYscale(0, 512);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLACK);
    }

    //takes 4 arguments, x1 and y1 as coordinates, a degree deg, and counting variable n
    //creates two new x and y coordinates x2 and y2
    //draws a new branch with coordines x1, y1, x2, y2
    //calls the function again changing the degree at which the branches are rotated
    private static void branch(double x1, double y1, double deg, int n) {
        if (n > 0) {
            double x2 = x1 + (Math.cos(Math.toRadians(deg))) * 50;
            double y2 = y1 + (Math.sin(Math.toRadians(deg))) * 50;
            StdDraw.line(x1, y1, x2, y2);
            branch(x2, y2, deg - 30, n - 1);
            branch(x2, y2, deg + 30, n - 1);

        }
    }

    //creates a drawing of a recursive tree of order n
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        setup();
        branch(256.0, 0.0, 90.0, n);
    }
}
