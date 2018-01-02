
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] threshold;
    private double mean;
    private double sd;
    private final double CONFIDENCE_95 = 1.96;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        threshold = new double[trials];
        if (n <= 0) {
            throw new IllegalArgumentException("size n out of bounds");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials out of bounds");
        }
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            do {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            } while (!percolation.percolates());
            threshold[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(threshold);
        sd = StdStats.stddev(threshold);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold   
    public double stddev() {
        return sd;
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean - CONFIDENCE_95 * sd / Math.sqrt(threshold.length);
    }

    // returns high bound of the 95% confidence interval
    public double confidenceHi() {
        return mean + CONFIDENCE_95 * sd / Math.sqrt(threshold.length);
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats PerlocationTestStat = new PercolationStats(5, 2);
        double i = PerlocationTestStat.threshold[0];
        System.out.println(i);
        System.out.println("mean = " + PerlocationTestStat.mean());
        System.out.println("stddev = " + PerlocationTestStat.stddev());
        System.out.println("95% confidence interval = ["
                + PerlocationTestStat.confidenceLo() + ",  " + PerlocationTestStat.confidenceHi() + "]");

    }

}
