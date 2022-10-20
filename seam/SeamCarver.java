import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.HashMap;

public class SeamCarver {
    /*
    Image resizing, removes a seam of low energy pixels from an images and
    updates the image.
     */
    private Picture pic;                    // picture
    private HashMap<Integer, Integer> seam; // seam
    private double seamEnergy;              // total energy in seam

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("");
        this.pic = picture;
    }

    // current picture
    public Picture picture() {
        return pic;
    }

    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= pic.width() || y >= pic.height()) {
            throw new IllegalArgumentException("");
        }
        double X;
        double Y;
        Color x1;
        Color x2;
        Color y1;
        Color y2;
        if (x == 0) {
            x1 = pic.get(width() - 1, y);
            x2 = pic.get(x + 1, y);
        }
        else if (x == width() - 1) {
            x1 = pic.get(x - 1, y);
            x2 = pic.get(0, y);
        }
        else {
            x1 = pic.get(x - 1, y);
            x2 = pic.get(x + 1, y);
        }
        if (y == 0) {
            y1 = pic.get(x, height() - 1);
            y2 = pic.get(x, y + 1);
        }
        else if (y == height() - 1) {
            y1 = pic.get(x, y - 1);
            y2 = pic.get(x, 0);
        }
        else {
            y1 = pic.get(x, y - 1);
            y2 = pic.get(x, y + 1);
        }
        X = Math.pow(x1.getRed() - x2.getRed(), 2) + Math.pow(x1.getBlue() - x2.getBlue(), 2)
                + Math.pow(x1.getGreen() - x2.getGreen(), 2);
        Y = Math.pow(y1.getRed() - y2.getRed(), 2) + Math.pow(y1.getBlue() - y2.getBlue(), 2)
                + Math.pow(y1.getGreen() - y2.getGreen(), 2);
        return Math.sqrt(X + Y);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        seam = new HashMap<>();
        seamEnergy = Double.MAX_VALUE;
        int[] seamHorizontal = new int[width()];
        for (int i = 0; i < height(); i++) {
            pathToSide(i);
        }
        for (int i = 0; i < width(); i++) {
            seamHorizontal[i] = seam.get(i);
        }
        return seamHorizontal;
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        seam = new HashMap<>();
        seamEnergy = Double.MAX_VALUE;
        int[] seamVertical = new int[height()];
        for (int i = 0; i < width(); i++) {
            pathToBottom(i);
        }
        for (int i = 0; i < height(); i++) {
            seamVertical[i] = seam.get(i);
        }
        return seamVertical;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (pic.height() == 1 || !isValidHorizontal(seam)) throw new IllegalArgumentException("");
        Picture newPic = new Picture(width(), height() - 1);
        int w = 0;
        for (int i = 0; i < width(); i++) {
            int index = seam[i];
            int h = 0;
            for (int j = 0; j < height(); j++) {
                if (j != index) {
                    newPic.set(w, h, pic.get(i, j));
                    h++;
                }
            }
            w++;
        }
        this.pic = newPic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (pic.width() == 1 || !isValidVertical(seam)) throw new IllegalArgumentException("");
        Picture newPic = new Picture(width() - 1, height());
        int h = 0;
        for (int i = 0; i < height(); i++) {
            int index = seam[i];
            int w = 0;
            for (int j = 0; j < width(); j++) {
                if (j != index) {
                    newPic.set(w, h, pic.get(j, i));
                    w++;
                }
            }
            h++;
        }
        this.pic = newPic;
    }

    // finds lowest energy path to bottom from starting point
    private void pathToBottom(int start) {
        double energy = energy(start, 0);
        HashMap<Integer, Integer> tempSeam = new HashMap<>();
        tempSeam.put(0, start);
        for (int i = 1; i < height(); i++) {
            double minEnergy = Double.MAX_VALUE;
            int index = 0;
            for (int j = -1; j < 2; j++) {
                if (start + j > 0 && start + j < width()) {
                    double tempEnergy = energy(start + j, i);
                    if (tempEnergy < minEnergy) {
                        minEnergy = tempEnergy;
                        index = start + j;
                    }
                }
            }
            start = index;
            energy += minEnergy;
            tempSeam.put(i, index);
        }
        if (energy < seamEnergy) {
            seamEnergy = energy;
            seam = tempSeam;
        }
    }

    // finds lowest energy path to side from startint point
    private void pathToSide(int start) {
        double energy = energy(0, start);
        HashMap<Integer, Integer> tempSeam = new HashMap<>();
        tempSeam.put(0, start);
        for (int i = 1; i < width(); i++) {
            double minEnergy = Double.MAX_VALUE;
            int index = 0;
            for (int j = -1; j < 2; j++) {
                if (start + j > 0 && start + j < height()) {
                    double tempEnergy = energy(i, start + j);
                    if (tempEnergy < minEnergy) {
                        minEnergy = tempEnergy;
                        index = start + j;
                    }
                }
            }
            start = index;
            energy += minEnergy;
            tempSeam.put(i, index);
        }
        if (energy < seamEnergy) {
            seamEnergy = energy;
            seam = tempSeam;
        }
    }

    // returns true if the seam is valid, false otherwise
    private boolean isValidVertical(int[] seam) {
        int dif;
        if (seam == null) {
            return false;
        }
        if (seam[0] > pic.height() || seam[0] < 0) {
            return false;
        }
        for (int i = 1; i < seam.length; i++) {
            dif = Math.abs(seam[i - 1] - seam[i]);
            if (seam[i] > pic.height() || seam[i] < 0 || dif > 1) {
                return false;
            }
        }

        return true;
    }

    // returns true if the seam is valid, false otherwise
    private boolean isValidHorizontal(int[] seam) {
        int dif;
        if (seam == null) {
            return false;
        }
        if (seam[0] > pic.width() || seam[0] < 0) {
            return false;
        }
        for (int i = 1; i < seam.length; i++) {
            dif = Math.abs(seam[i - 1] - seam[i]);
            if (seam[i] > pic.width() || seam[i] < 0 || dif > 1) {
                return false;
            }
        }

        return true;
    }

    //  unit testing (required)
    public static void main(String[] args) {
        Picture picture = new Picture("6x5.png");
        SeamCarver sm = new SeamCarver(picture);
        Picture newPic;
        int[] seamV = sm.findVerticalSeam();
        int[] seamH = sm.findHorizontalSeam();
        for (int i : seamV) {
            StdOut.print(i + " ");
        }
        StdOut.println();
        for (int i : seamH) {
            StdOut.print(i + " ");
        }
        StdOut.println();
        sm.removeHorizontalSeam(seamH);
        newPic = sm.picture();
        newPic.save("horizRemoved.png");
        sm.removeVerticalSeam(seamV);
        newPic = sm.picture();
        newPic.save("newPic.png");
    }
}

