import java.util.Random;

public class Percolation {

    private int[][] grid;
    private WQUPC connections;
    private int connectionsCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Invalid value of n");

        grid = new int[n][n];
        connectionsCount = n * n + 2;
        connections = new WQUPC(connectionsCount);

        for (int i=0; i < n; i++)
            for (int j=0; j < n; j++)
                grid[i][j] = 0;
        
    }

    // converts 2D row col to 1D index for connections
    private int getIndex(int row, int col) {
        return (row - 1) * grid.length + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row < 1 || row > grid.length) || (col < 1 || col > grid.length)) 
            throw new IllegalArgumentException("Invalid row and column indicies.");

        grid[row - 1][col - 1] = 1;
        
        if (row == 1)
        	connections.union(getIndex(row, col), 0); // artificial top root
        if (row == grid.length)
        	connections.union(getIndex(row, col), connectionsCount - 1); // artificial bottom root
        
        // check left, right, top, and bottom. If any is open, union.
        if (col > 1 && isOpen(row, col - 1)) // left
        	connections.union(getIndex(row, col), getIndex(row, col - 1));
        if (col < grid.length && isOpen(row, col + 1)) // right
        	connections.union(getIndex(row, col), getIndex(row, col + 1));
        if (row > 1 && isOpen(row - 1, col)) // top
        	connections.union(getIndex(row, col), getIndex(row - 1, col));
        if (row < grid.length && isOpen(row + 1, col)) // top
        	connections.union(getIndex(row, col), getIndex(row + 1, col));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row < 1 || row > grid.length) || (col < 1 || col > grid.length)) 
            throw new IllegalArgumentException("Invalid row and column indicies.");

        return grid[row - 1][col - 1] == 1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int counter = 0;
        for (int i=0; i < grid.length; i++)
            for (int j=0; j <  grid.length; j++)
                if (grid[i][j] == 1)
                	counter++;
        
        return counter;
    }


    //does the system percolate?
    public boolean percolates() {
    	return connections.find(0) == connections.find(connectionsCount - 1);
    }
    
    public void print() {
        String black = "\u2B1B \t";
        String white = "\u2B1C \t";
        
        System.out.println("----------------------------");
        for (int i=0; i < grid.length; i++) {
            for (int j=0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] == 1 ? white : black);
            }
        System.out.println("\n----------------------------");
        }

        System.out.println("Peroclates? " + this.percolates());
        System.out.println("Open (White) Sites: " + this.numberOfOpenSites());
        System.out.println();
    }
    
    // for testing purposes
    public static void main(String[] args) {
    	Random rand = new Random();
    	
    	int length = 6;
    	Percolation test = new Percolation(length);
    	while(!test.percolates()) {
    		int random1 = rand.nextInt(length) + 1;
    		int random2 = rand.nextInt(length) + 1;
			while (test.isOpen(random1, random2)) { // the space is already opened so skip
				random1 = rand.nextInt(length) + 1;
	    		random2 = rand.nextInt(length) + 1;
			}
			test.open(random1, random2);
    	}
    	
    	test.print();
    	
    }
}