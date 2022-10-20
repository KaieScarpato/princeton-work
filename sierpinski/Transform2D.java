public class Transform2D {
    // Returns a new array that is an exact copy of the given array.
    // The given array is not mutated.
    public static double[] copy(double[] array) {
        double array2[] = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            array2[i] = array[i];
        }
        return array2;
    }

    // Scales the given polygon by the factor alpha.
    public static void scale(double[] x, double[] y, double alpha) {
        double[] x2 = copy(x);
        double[] y2 = copy(y);

        for (int i = 0; i < x.length; i++) {
            x[i] = x2[i] * alpha;
        }
        for (int i = 0; i < y.length; i++) {
            y[i] = y2[i] * alpha;
        }
    }

    // Translates the given polygon by (dx, dy).
    public static void translate(double[] x, double[] y, double dx, double dy) {
        double[] x2 = copy(x);
        double[] y2 = copy(y);

        for (int i = 0; i < x.length; i++) {
            x[i] = x2[i] + dx;
        }
        for (int i = 0; i < y.length; i++) {
            y[i] = y2[i] + dy;
        }
    }

    // Rotates the given polygon theta degrees counterclockwise, about the origin.
    public static void rotate(double[] x, double[] y, double theta) {
        double[] x2 = copy(x);
        double[] y2 = copy(y);
        double theta2 = Math.toRadians(theta);

        for (int i = 0; i < x.length; i++) {
            x[i] = x2[i] * Math.cos(theta2) - y2[i] * Math.sin(theta2);
        }
        for (int i = 0; i < y.length; i++) {
            y[i] = y2[i] * Math.cos(theta2) + x2[i] * Math.sin(theta2);
        }
    }

    public static void main(String[] args) {
        // Set the x- and y-scale
        StdDraw.setScale(-5.0, +5.0);

        // Create polygon
        double[] x = { 0, 1, 1, 0 };
        double[] y = { 0, 0, 2, 1 };

        // Draw original polygon in red
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.polygon(x, y);

        // Translate polygon by
        // 2.0 in the x-direction
        // 1.0 in the y-direction
        translate(x, y, 2.0, 1.0);
        //Rotate polygon by 90 degrees
        rotate(x, y, 90.0);
        //Scale polygon by 1.5
        scale(x, y, 1.5);

        // Draw translated polygon in blue
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.polygon(x, y);
    }
}
