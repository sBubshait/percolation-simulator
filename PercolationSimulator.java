import java.util.Random;

public class PercolationSimulator {   
	
	private double[] results;
	private int trialsCount;

    // perform independent trials on an n-by-n grid
    public PercolationSimulator(int n, int trials) {
    	
    	this.trialsCount = trials;
    	Percolation trial;
    	results = new double[trials];
    	Random rand = new Random();
    	
    	for (int i=0; i < trials; i++) {
    		trial = new Percolation(n);
    		
    		while(!trial.percolates()) {
    			int random1 = rand.nextInt(n) + 1;
    			int random2 = rand.nextInt(n) + 1;
    			
    			while (trial.isOpen(random1, random2)) {
    				random1 = rand.nextInt(n) + 1;
        			random2 = rand.nextInt(n) + 1;
    			}
    			
    			trial.open(random1, random2);
    		}
    		
			results[i] = (double) trial.numberOfOpenSites() / (n * n);
    	}
        

    }
    
    // sample mean of percolation threshold
    public double mean() {
    	double sum = 0;
    	for (int i=0; i < results.length; i++)
    		sum += results[i];
    
    	return sum / results.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	double avg = this.mean();
    	double sum = 0;
    	for (int i=0; i < results.length; i++)
    		sum += Math.pow((results[i] - avg), 2);
    	return Math.sqrt(sum / (trialsCount - 1));
    }

	// low end-point of 95% confidence interval
    public double confidenceLo() {
    	return this.mean() - (1.96 * this.stddev() / Math.sqrt(trialsCount));
    }

	// high end-point of 95% confidence interval
    public double confidenceHi() {
    	return this.mean() + (1.96 * this.stddev() / Math.sqrt(trialsCount));

    }

	// testing
   public static void main(String[] args) {
	   int n = Integer.parseInt(args[0]); 
	   int T = Integer.parseInt(args[1]);
	   PercolationSimulator t = new PercolationSimulator(n, T);
	   System.out.println("mean                    = " + t.mean());
	   System.out.println("stddev                  = " + t.stddev());
	   System.out.println("95% confidence interval = [" + t.confidenceLo() + ", " + t.confidenceHi() + "]"	);
   }

}
