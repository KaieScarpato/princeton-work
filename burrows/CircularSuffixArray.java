import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private static final int CUTOFF = 15;   // cutoff to insertion sort
    private int length;
    private int[] arr;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        length = s.length();
        arr = new int[length];
        String[] sortedSuffix = new String[length];
        sortedSuffix[0] = (s);
        for (int i = 1; i < length; i++) {
            arr[i] = i;

            char suff = s.charAt(0);
            s = s.substring(1) + suff;
            sortedSuffix[i] = (s);
        }

        sort(sortedSuffix, 0, length - 1, 0);
    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        return arr[i];
    }

    // swaps values in the index array
    private void swapInt(int x, int y) {
        int tmp = arr[x];
        arr[x] = arr[y];
        arr[y] = tmp;
    }

    // swaps strings in the string arr
    private void swap(String[] a, int x, int y) {
        String tmp = a[x];
        a[x] = a[y];
        a[y] = tmp;
    }

    // three way radix quicksort algorithm
    private void sort(String[] l, int start, int end, int index) {
        if (start + CUTOFF >= end) {
            insertion(l, start, end, index);
            return;
        }
        int startPoint = start;
        int endPoint = end;
        int pivot = l[startPoint].charAt(index);
        int pointer = start++;
        while (pointer <= end) {
            int element = l[pointer].charAt(index);
            if (pivot > element) {
                swapInt(startPoint++, pointer++);
                swap(l, startPoint++, pointer++);
            }
            else if (pivot < element) {
                swapInt(pointer, endPoint--);
                swap(l, pointer, endPoint--);
            }
            else {
                pointer++;
            }
        }
        sort(l, start, startPoint - 1, index);
        if (pivot >= 0) {
            sort(l, startPoint, endPoint, index + 1);
        }
        sort(l, endPoint + 1, end, index);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--) {
                swapInt(j, j - 1);
                swap(a, j, j - 1);
            }
        }
    }

    // is v less than w, starting at character d
    private boolean less(String v, String w, int d) {
        assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    // unit testing (required)
    public static void main(String[] args) {
        String str = "ABRACADABRA!";
        CircularSuffixArray a = new CircularSuffixArray(str);
        for (int i = 0; i < 12; i++) {
            StdOut.print(a.index(i) + " ");
        }
        StdOut.println();
    }
}
