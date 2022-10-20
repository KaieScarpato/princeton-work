import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;

public class MoveToFront {

    // apply move-to-front encoding, reading from stdin and writing to stdout
    public static void encode() {
        LinkedList<Character> list = new LinkedList<>();
        for (char c = 0; c < 256; c++) {
            list.add(c);
        }
        int index = 0;
        while (!BinaryStdIn.isEmpty()) {
            char i = BinaryStdIn.readChar();
            index = list.indexOf(i);
            list.remove(index);
            list.addFirst(i);
            BinaryStdOut.write(i);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from stdin and writing to stdout
    public static void decode() {
        LinkedList<Character> list = new LinkedList<>();
        for (char c = 0; c < 256; c++) {
            list.add(c);
        }
        int index = 0;
        while (!BinaryStdIn.isEmpty()) {
            char i = BinaryStdIn.readChar();
            index = list.indexOf(i);
            list.remove(index);
            list.addFirst(i);
            BinaryStdOut.write(i);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        if (args[0].equals("+")) decode();
    }
}
