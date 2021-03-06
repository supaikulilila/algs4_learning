import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF sites;
    private WeightedQuickUnionUF auxSites;
    private boolean[] status;
    private int N;
    private int length;
    private int numberOfOpenSites;

    private int calIdx(int row, int col) {
        return (row - 1) * length + col;
    }

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        N = n * n + 2;
        length = n;
        sites = new WeightedQuickUnionUF(N);
        auxSites = new WeightedQuickUnionUF(N);
        status = new boolean[N];
        numberOfOpenSites = 0;

        for (int i = 0; i < N; i++) {
            status[i] = false;
        }
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        int idx = calIdx(row, col);
        status[idx] = true;
        numberOfOpenSites++;

        if (row != 1 && isOpen(row - 1, col)) { // up
            sites.union(idx, idx - length);
            auxSites.union(idx, idx - length);
        }
        if (row != length && isOpen(row + 1, col)) { // down
            sites.union(idx, idx + length);
            auxSites.union(idx, idx + length);
        }
        if (col != 1 && isOpen(row, col - 1)) { // left
            sites.union(idx, idx - 1);
            auxSites.union(idx, idx - 1);
        }
        if (col != length && isOpen(row, col + 1)) { // right
            sites.union(idx, idx + 1);
            auxSites.union(idx, idx + 1);
        }

        if (row == 1) {
            sites.union(idx, 0);
            auxSites.union(idx, 0);
        }
        if (row == length) {
            sites.union(idx, N - 1);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > length || col > length) {
            throw new IllegalArgumentException();
        }

        int idx = calIdx(row, col);
        return status[idx];
    }

    public boolean isFull(int row, int col) {
        int idx = calIdx(row, col);
        if (isOpen(row, col)) {
            return sites.connected(idx, 0) && auxSites.connected(idx, 0);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        // n - 2, n - 1 means virtual-top and virtual-bottom
        return sites.connected(0, N - 1);
    }

    public static void main(String[] args) {

    }
}
