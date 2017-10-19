import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
 private int trials;
 private int matrixEdge;
 private int[] tests;

   public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n grid
    matrixEdge = n;
    this.trials = trials;
    tests = new int[trials];

    for(int i = 0; i < trials; i ++) {

     Percolation perc = new Percolation(matrixEdge);
     while(perc.percolates()) {
      int rand1 = StdRandom.uniform(matrixEdge);
      int rand2 = StdRandom.uniform(matrixEdge);
      perc.open(rand1, rand2);
      if (perc.percolates()) {
       tests[i] = perc.numberOfOpenSites();
      }
     }
    }

   }
   public double mean() {                          // sample mean of percolation threshold
    return StdStats.mean(tests);
   }

   public double stddev() {                        // sample standard deviation of percolation threshold
    return StdStats.stddev(tests);
   }

   public double confidenceLo() {                 // low  endpoint of 95% confidence interval
    return mean() - (1.96 * Math.sqrt(mean())/(Math.sqrt(trials)));
   }

   public double confidenceHi(){                  // high endpoint of 95% confidence interval
    return mean() + (1.96 * Math.sqrt(mean())/(Math.sqrt(trials)));
 }

   public static void main(String[] args){        // test client (described below)
   }
}