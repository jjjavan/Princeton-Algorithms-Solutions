
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{
    private final int trials;
    private final double[] run;
    private static final double CONFIDENCE_95 = 1.96;
    private double mean;
    private double stdDev;

    public PercolationStats(int n, int trials)
    {
        validate(n,trials);
        Percolation p;
        this.trials = trials;
        run = new double[trials];
        int x,
            y;
        for (int i = 0; i < trials; ++i)
        {
            p = new Percolation(n);
            while (!p.percolates())
            {
                x = StdRandom.uniform(1,n+1);
                y = StdRandom.uniform(1,n+1);
                p.open(x, y);
            }

            run[i] = p.numberOfOpenSites()/Math.pow((double)n,2);
            mean = StdStats.mean(run);
            stdDev = StdStats.stddev(run);
            p = null;
        }


    }// perform trials independent experiments on an n-by-n grid

    public double mean()
    {
        return mean;

    }// sample mean of percolation threshold

    public double stddev()
    {
        return stdDev;
    }// sample standard deviation of percolation threshold

    public double confidenceLo()
    {
        double T = Math.sqrt(trials);
        return mean - CONFIDENCE_95*stdDev/T;

    }// low endpoint of 95% confidence interval

    public double confidenceHi()
    {
        double T = Math.sqrt(trials);
        return mean + CONFIDENCE_95*stdDev/T;

    }// high endpoint of 95% confidence interval
    private void validate(int n, int trials)
    {
        if((n <= 0 || trials <= 0))
            throw new IllegalArgumentException("Size of set and trials must be greater than 0. You entered " + n
                    + " for size and " + trials + " for trials.\n");
    }// were valid values entered?
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, trials);
        System.out.printf("mean                    = %.16f%n",percStats.mean());
        System.out.printf("stddev                  = %.16f%n",percStats.stddev());
        System.out.printf("95% confidence interval = [%.16f %.16f]%n", percStats.confidenceLo(), percStats.confidenceHi());
    }
}
