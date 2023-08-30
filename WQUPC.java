// Weighted Quick Union with Path Compression
public class WQUPC {
	
	private int[] list;
	private int[] sizes;
	
	// initialise WQUPC with n elements
	public WQUPC(int n) {
		list = new int[n];
		sizes = new int[n];
		
		for (int i=0; i < n; i++)
			list[i] = i;
	}
	
	// Connects two elements together (i.e., connects the two trees).
	public void union(int p, int q) {
		
		if (sizes[p] > sizes[q]) { // Weighted Union
			list[this.find(q)] = find(p);
			sizes[p] += sizes[q];
		} else {
			list[this.find(q)] = find(p);
			sizes[q] += sizes[p];
		}
		
	}
	
	// Finds the root of an element.
	public int find(int p) {
		
		while (p != list[p]) {
			list[p] = list[list[p]]; // path compression
			p = list[p];
		}
		return p;
	}
	
	// checks if two elements are connected (i.e., there is a path between the two).
	public boolean connected(int p, int q) {
		return this.find(p) == this.find(q);
	}
	

}
