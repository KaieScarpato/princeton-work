/*******************************************************************************
 *
 *  This is a template file for GuitarString.java. It lists the constructors
 *  and methods you need, along with descriptions of what they're supposed
 *  to do.
 *
 *  Note: it won't compile until you fill in the constructors and methods
 *        (or at least commment out the ones whose return type is non-void).
 *
 ******************************************************************************/

public class GuitarString {
    private RingBuffer gString;   // guitar string

    // creates a guitar string of the specified frequency,
    // using sampling rate of 44,100
    public GuitarString(double frequency) {
        int n = (int) Math.ceil((44100.0 / frequency));
        gString = new RingBuffer(n);
        for (int i = 0; i < n; i++) {
            gString.enqueue(0);
        }
    }

    // creates a guitar string whose size and initial values are given by
    // the specified array
    public GuitarString(double[] init) {
        gString = new RingBuffer(init.length);
        for (int i = 0; i < init.length; i++) {
            gString.enqueue(init[i]);
        }
    }

    // returns the number of samples in the ring buffer
    public int length() {
        return gString.size();
    }

    // plucks the guitar string (by replacing the buffer with white noise)
    // replaces buffer with values between -0.5 and 0.5
    public void pluck() {
        for (int i = 0; i < gString.capacity(); i++) {
            double n = StdRandom.uniform(-0.5, 0.5);
            gString.dequeue();
            gString.enqueue(n);
        }
    }

    // advances the Karplus-Strong simulation one time step
    public void tic() {
        double s1 = gString.dequeue();
        double s2 = gString.peek();
        double n = 0.996 * (0.5 * (s1 + s2));
        gString.enqueue(n);
    }

    // returns the current sample
    public double sample() {
        return gString.peek();
    }


    // tests and calls every constructor and instance method in this class
    public static void main(String[] args) {
        double[] samples = { 0.2, 0.4, 0.5, 0.3, -0.2, 0.4, 0.3, 0.0, -0.1, -0.3 };
        GuitarString testString = new GuitarString(samples);
        int m = 25; // 25 tics
        for (int i = 0; i < m; i++) {
            double sample = testString.sample();
            StdOut.printf("%6d %8.4f\n", i, sample);
            testString.tic();
        }
        double CONCERT_A = 440.0;
        double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
        StdOut.println(1);
        GuitarString stringA = new GuitarString(CONCERT_A);
        StdOut.println(1);
        GuitarString stringC = new GuitarString(CONCERT_C);
        StdOut.println(1);
        stringA.pluck();
        double sample = stringA.sample() + stringC.sample();
        StdOut.println(sample);
    }
}
