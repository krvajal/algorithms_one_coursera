/**
 * Created by carvajal on 1/5/17.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {


    private double[] p;
    private double mean;
    private double sdtdev;
    private double lo;
    private double hi;

    public PercolationStats(int n, int trials) {


        p = new double[trials];

        for (int i = 0; i < trials; i++) {
            p[i] = run(n);
//            StdOut.println(p[i]);
        }
        mean = StdStats.mean(p);
        sdtdev = StdStats.stddev(p);
        double delta = 1.96 * sdtdev / Math.sqrt(trials);
        lo = mean - delta;
        hi = mean + delta;


    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());

    }

    private double run(int n) {

        Percolation percolation = new Percolation(n);
        while (!percolation.percolates() && (percolation.numberOfOpenSites() != n * n)) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            percolation.open(row, col);
//                System.out.println("added site at " + row  + ", " + col);
        }
        return percolation.numberOfOpenSites() / (double) (n * n);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {

        return sdtdev;
    }

    public double confidenceLo() {
        return lo;
    }

    public double confidenceHi() {
        return hi;
    }
}
