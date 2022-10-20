/* *****************************************************************************
 * Name:
 * NetID:
 * Precept:
 *
 * Description:
 *
 **************************************************************************** */

public class LFSR {
    private int n; //number of bits in seed
    private int[] seed; //instance variable seed as an array
    private int tap; //instance variable tap as an int

    // creates an LFSR with the specified seed and tap
    public LFSR(String seed, int tap) {
        this.seed = new int[seed.length()];
        for (int i = 0; i < seed.length(); i++) {
            this.seed[i] = Character.getNumericValue(seed.charAt(i));
        }
        this.n = seed.length();
        this.tap = tap;
    }

    // returns the number of bits in this LFSR
    public int length() {
        return n;
    }

    // returns the ith bit of this LFSR (as 0 or 1)
    public int bitAt(int i) {
        return seed[n - i];
    }

    // returns a string representation of this LFSR
    public String toString() {
        String str = "";
        for (int i = n - 1; i > 0; i--) {
            str += String.valueOf(bitAt(i));
        }
        return str;
    }

    // simulates one step of this LFSR and returns the new bit (as 0 or 1)
    public int step() {
        int a = bitAt(n) ^ bitAt(tap);
        for (int i = 0; i < n - 1; i++) {
            seed[i] = seed[i + 1];
        }
        seed[n - 1] = a;
        return a;
    }

    // simulates k steps of this LFSR and returns the k bits as a k-bit integer
    public int generate(int k) {
        String s = "";
        for (int i = 0; i < k; i++) {
            s += String.valueOf(step());
        }
        return Integer.parseInt(s, 2);
    }

    // tests this class by directly calling all instance methods
    public static void main(String[] args) {
        StdOut.println("Testing methods");
        LFSR lfsr = new LFSR("11111111011", 9);
        StdOut.println("Seed = " + lfsr);
        StdOut.println("Length of seed = " + lfsr.length());
        StdOut.println("Bit at 3 = " + lfsr.bitAt(3));
        StdOut.println();

        StdOut.println("Testing step");
        LFSR lfsr1 = new LFSR("01101000010", 9);
        StdOut.println(lfsr1);
        for (int i = 0; i < 10; i++) {
            int bit = lfsr1.step();
            StdOut.println(lfsr1 + " " + bit);
        }
        StdOut.println();
        StdOut.println("Testing generate");
        LFSR lfsr20 = new LFSR("01101000010100010000", 17);
        StdOut.println(lfsr20);
        for (int i = 0; i < 10; i++) {
            int r = lfsr20.generate(8);
            StdOut.println(lfsr20 + " " + r);
        }
    }
}
