public class BeadFinder {
    private Picture image; // image of blobs
    private Double tau;    // constant tau
    private Blob blob;     // blob instance
    private Blob[] bList;  // list of blobs
    private boolean[][] b; // list of pixels, true if blob is located at (x,y)

    // finds all blobs in the specified picture using luminance threshold tau
    // if luminance is greater than tau, creates a new blob with pixels(x,y)
    // then uses dfs algorithm to search for all pixels in blob
    public BeadFinder(Picture picture, double tau) {
        this.image = picture;
        this.tau = tau;
        this.bList = new Blob[0];
        this.b = new boolean[picture.width()][picture.height()];
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                if (!b[x][y]) {
                    if (Luminance.intensity(image.get(x, y)) >= tau) {
                        blob = new Blob();
                        dfs(x, y);
                        Blob[] temp = new Blob[bList.length + 1];
                        for (int k = 0; k < bList.length; k++) {
                            temp[k] = bList[k];
                        }
                        bList = temp;
                        bList[bList.length - 1] = blob;
                    }
                }
            }
        }
    }

    // finds pixels in blob
    // returns if a pixel is not found, else adds pixel to blob
    // and checks surrounding pixels
    private void dfs(int x, int y) {
        if (x < 0 || y < 0 || x >= image.width() || y >= image.height() || b[x][y]) {
            return;
        }
        if (Luminance.intensity(image.get(x, y)) < tau) {
            return;
        }
        else {
            b[x][y] = true;
            blob.add(x, y);
            dfs(x + 1, y);
            dfs(x - 1, y);
            dfs(x, y + 1);
            dfs(x, y - 1);
        }
    }

    //  returns list of all beads (blobs with >= min pixels)
    public Blob[] getBeads(int min) {
        int count = 0;
        for (int i = 0; i < bList.length; i++) {
            if (bList[i].mass() >= min) {
                count += 1;
            }
        }
        Blob[] temp = new Blob[count];
        int idx = 0;
        for (int i = 0; i < bList.length; i++) {
            if (bList[i].mass() >= min) {
                temp[idx] = bList[i];
                idx += 1;
            }
        }
        return temp;
    }

    //  test client, as described below
    public static void main(String[] args) {
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        Picture image = new Picture(args[2]);
        BeadFinder f = new BeadFinder(image, tau);
        Blob[] a = f.getBeads(min);
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
}
