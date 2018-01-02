
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private boolean[][] isOpen;
    private int top;
    private int bottom;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF gridTop;
    private int numberOfOpenSites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("size n out of bounds");
        }
        this.size = n;
        this.top = 0;
        this.bottom = n * n + 1;
        grid = new WeightedQuickUnionUF(this.size * this.size + 2);
        gridTop = new WeightedQuickUnionUF(this.size * this.size + 1);
        isOpen = new boolean[this.size][this.size];
        this.numberOfOpenSites = 0;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > this.size) {
            throw new IllegalArgumentException("row index out of bounds");
        }
        if (col < 1 || col > this.size) {
            throw new IllegalArgumentException("column index out of bounds");
        }
        if (!isOpen(row, col)) {
            this.isOpen[row - 1][col - 1] = true;
            if (row == 1) {
                grid.union(gridIndex(row, col), top);
                gridTop.union(gridIndex(row, col), top);

            }
            if (row == this.size) {
                grid.union(gridIndex(row, col), bottom);
            }
            if (row != 1) {
                if (this.isOpen[row - 2][col - 1]) {
                    grid.union(gridIndex(row, col), gridIndex(row - 1, col));
                    gridTop.union(gridIndex(row, col), gridIndex(row - 1, col));

                }
            }
            if (row != this.size) {
                if (this.isOpen[row][col - 1]) {
                    grid.union(gridIndex(row, col), gridIndex(row + 1, col));
                    gridTop.union(gridIndex(row, col), gridIndex(row + 1, col));
                }
            }
            if (col != 1) {
                if (this.isOpen[row - 1][col - 2]) {
                    grid.union(gridIndex(row, col), gridIndex(row, col - 1));
                    gridTop.union(gridIndex(row, col), gridIndex(row, col - 1));
                }
            }
            if (col != this.size) {
                if (this.isOpen[row - 1][col]) {
                    grid.union(gridIndex(row, col), gridIndex(row, col + 1));
                    gridTop.union(gridIndex(row, col), gridIndex(row, col + 1));
                }
            }
            this.numberOfOpenSites++;
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > this.size) {
            throw new IllegalArgumentException("row index out of bounds");
        }
        if (col < 1 || col > this.size) {
            throw new IllegalArgumentException("column index out of bounds");
        }
        return this.isOpen[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > this.size) {
            throw new IllegalArgumentException("row index out of bounds");
        }
        if (col < 1 || col > this.size) {
            throw new IllegalArgumentException("column index out of bounds");
        }
        return gridTop.connected(top, gridIndex(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.connected(top, bottom);
    }

    // map from 2D row and col imput to 1D index.
    private int gridIndex(int row, int col) {
        return (row - 1) * this.size + col;
    }

    public static void main(String[] args) {
        Percolation test = new Percolation(5);
        test.open(1, 2);
        test.open(2, 2);
        test.open(2, 2);
        test.open(3, 2);
        test.open(4, 2);
        test.open(5, 2);
        test.open(5, 5);
        int i = test.numberOfOpenSites();
        int j = test.gridIndex(2, 1);

        System.out.println(test.isFull(5, 5));
        System.out.println(i);

    }
}
