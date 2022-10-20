import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] numPercolate;    // array containing percolation thresholds
    private double timeElapsed;       // time elapsed
    private int numTrials;            // number of trials completed

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Invalid input");
        this.numTrials = trials;
        this.numPercolate = new double[trials];
        Stopwatch time = new Stopwatch();
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                p.open(row, col);
            }
            this.numPercolate[i] = (double) p.numberOfOpenSites() / (n * n);
        }
        this.timeElapsed = time.elapsedTime();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(numPercolate);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(numPercolate);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * (stddev() / Math.sqrt(numTrials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * (stddev() / Math.sqrt(numTrials)));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(n, trials);
        StdOut.println(String.format("Mean = %f", p.mean()));
        StdOut.println(String.format("Std Dev = %f", p.stddev()));
        StdOut.println(String.format("Low Point Confidence Inveral = %f",
                                     p.confidenceLow()));
        StdOut.println(String.format("High Point Confidence Inverval = %f",
                                     p.confidenceLow()));
        StdOut.println(String.format("Elapsed time = %f", p.timeElapsed));
    }
}
