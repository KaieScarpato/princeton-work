import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {
    /*
    Binary search, finds either the first or last index of a term containing a
    certain prefix.
     */

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("");
        }
        int lo = 0;
        int hi = a.length - 1;

        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int n = comparator.compare(key, a[mid]);
            if (n < 0) {
                hi = mid - 1;
            }
            else if (n > 0) {
                lo = mid + 1;
            }
            else {
                while (mid < a.length && comparator.compare(key, a[mid]) == 0) {
                    mid--;
                }
                return mid + 1;
            }
        }
        return -1;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("");
        }
        int lo = 0;
        int hi = a.length - 1;

        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int n = comparator.compare(key, a[mid]);
            if (n < 0) {
                hi = mid - 1;
            }
            else if (n > 0) {
                lo = mid + 1;
            }
            else {
                while (mid < a.length && comparator.compare(key, a[mid]) == 0) {
                    mid++;
                }
                return mid - 1;
            }
        }
        return -1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Integer a[] = new Integer[10];
        for (int i = 0; i < 10; i++) {
            a[i] = i / 4;
        }
        for (int i : a) {
            StdOut.print(i + " ");
        }
        StdOut.println();
        int f = firstIndexOf(a, 1, Comparator.naturalOrder());
        StdOut.println("First index of 1 = " + f);
        int l = lastIndexOf(a, 1, Comparator.naturalOrder());
        StdOut.println("Last index pf 1 = " + l);
    }
}
