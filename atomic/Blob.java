public class Blob {
    private int pixels;  // number of pixels
    private double xSum; // number of pixels in x plane
    private double ySum; // number of pixels in y plane

    //  creates an empty blob
    public Blob() {
        this.pixels = 0;
        this.xSum = 0.0;
        this.ySum = 0.0;

    }

    //  adds pixel (x, y) to this blob
    public void add(int x, int y) {
        pixels++;
        xSum += x;
        ySum += y;

    }

    //  number of pixels added to this blob
    public int mass() {
        return pixels;
    }

    //  Euclidean distance between the center of masses of the two blobs
    public double distanceTo(Blob that) {
        double x1 = this.xSum / this.pixels;
        double y1 = this.ySum / this.pixels;
        double x2 = that.xSum / that.pixels;
        double y2 = that.ySum / that.pixels;
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    //  string representation of this blob (see below)
    public String toString() {
        Double xCoord = xSum / pixels;
        Double yCoord = ySum / pixels;
        return String.format("%2d (%8.4f, %8.4f)", pixels, xCoord, yCoord);
    }

    //  tests this class by directly calling all instance methods
    public static void main(String[] args) {
        Blob b = new Blob();
        b.add(1, 2);
        b.add(3, 3);
        Blob a = new Blob();
        a.add(0, 4);
        a.add(3, 2);
        StdOut.println(b.distanceTo(a));
        StdOut.println(b);
    }
}
