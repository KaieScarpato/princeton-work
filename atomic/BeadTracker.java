public class BeadTracker {

    // tracks beads across multiple images and shows their displacement
    // uses BeadFinder to locate beads
    public static void main(String[] args) {
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        double delta = Double.parseDouble(args[2]);
        String[] files = new String[args.length - 3];
        for (int i = 0; i < args.length - 3; i++) {
            files[i] = args[i + 3];
        }
        for (int i = 0; i < files.length - 1; i++) {
            Picture image1 = new Picture(files[i]);
            Picture image2 = new Picture(files[i + 1]);
            BeadFinder bead1 = new BeadFinder(image1, tau);
            BeadFinder bead2 = new BeadFinder(image2, tau);
            Blob[] bead1_arr = bead1.getBeads(min);
            Blob[] bead2_arr = bead2.getBeads(min);

            for (int j = 0; j < bead2_arr.length; j++) {
                double dist1;
                double dist2 = Double.POSITIVE_INFINITY;
                for (int a = 0; a < bead1_arr.length; a++) {
                    dist1 = bead2_arr[j].distanceTo(bead1_arr[a]);
                    if (dist1 < dist2 && dist1 <= delta) {
                        dist2 = dist1;
                    }
                }
                if (dist2 > 0.0 && dist2 < Double.POSITIVE_INFINITY) {
                    StdOut.println(dist2);
                }
            }
        }
    }
}
