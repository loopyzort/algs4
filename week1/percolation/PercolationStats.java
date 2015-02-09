public class PercolationStats {
    private static final double Z_P_95 = 1.96;
    private final double[] thresholds;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException(
                    "Must be constructed with arguments > 0");
        }
        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            // TODO(todd): keep track to remove redundant openings
            StdRandom.setSeed(System.currentTimeMillis());
            int openings = 0;
            Percolation percolation = new Percolation(N);
            int openI, openJ;
            while (!percolation.percolates()) {
                openI = StdRandom.uniform(N) + 1;
                openJ = StdRandom.uniform(N) + 1;
                if (!percolation.isOpen(openI, openJ)) {
                    percolation.open(openI, openJ);
                    openings++;
                }
            }
            thresholds[i] = openings / (double) (N * N);
        }

    }     // perform T independent experiments on an N-by-N grid

    public double mean() {
        // sample mean of percolation
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    private double confidenceInterval() {
        return (Z_P_95 * stddev() / (Math.sqrt(thresholds.length)));
    }
    public double confidenceLo() {
        return mean() - confidenceInterval();
    }

    public double confidenceHi() {
        return mean() + confidenceInterval();
    }

    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            StdOut.print("Usage: PercolationStats [size of grid] [number "
                    + "of tests]");
        }
        int[] intArgs = new int[]{
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1])
        };
        PercolationStats stats = new PercolationStats(intArgs[0], intArgs[1]);
        StdOut.printf("Mean                    = %9.9f", stats.mean());
        StdOut.println();
        StdOut.printf("Stddev                  = %9.9f", stats.stddev());
        StdOut.println();
        StdOut.printf("95%% confidence interval = %9.9f, %9.9f",
                stats.confidenceLo(),
                stats.confidenceHi());
    }
}
