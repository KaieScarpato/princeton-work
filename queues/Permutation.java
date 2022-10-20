import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String[] strArr = StdIn.readAllStrings();
        RandomizedQueue<String> randomQueue = new RandomizedQueue<>();
        for (String str : strArr) {
            randomQueue.enqueue(str);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(randomQueue.dequeue());
        }
    }
}
