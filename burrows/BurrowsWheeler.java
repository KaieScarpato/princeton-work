import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String str = BinaryStdIn.readString();
        CircularSuffixArray array = new CircularSuffixArray(str);
        String out = "";
        int first = 0;
        // find index in which first suffix is located
        for (int i = 0; i < str.length(); i++) {
            int index = array.index(i);
            if (index == 0) {
                first = i;
            }
            // index of character in the original suffix
            int n = index - 1;
            if (n < 0) {
                n += str.length();
            }
            out = out + str.charAt(n);
        }
        BinaryStdOut.write(first);
        BinaryStdOut.write(out);
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        char[] str = BinaryStdIn.readString().toCharArray();
        int[] count = new int[257];
        int[] next = new int[str.length];
        // couting sort
        for (int i = 0; i < str.length; i++) {
            int c = str[i];
            count[c + 1]++;
        }
        for (int i = 0; i < 256; i++) {
            count[i + 1] += count[i];
        }
        for (int i = 0; i < str.length; i++) {
            char c = str[i];
            next[count[c]] = i;
            count[c]++;
        }
        for (int i = 0; i < str.length; i++) {
            BinaryStdOut.write(str[next[first]]);
            first = next[first];
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        if (args[0].equals("+")) inverseTransform();
    }
}
