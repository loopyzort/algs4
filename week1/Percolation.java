import java.lang.IndexOutOfBoundsException;

public class Percolation {
    private final WeightedQuickUnionUF mWeightedQuickUnionUF;
    private final int mDimension;
    private final Status[] mGrid;
    private enum Status { BLOCKED, OPEN }

    public Percolation(int N) {               // create N-by-N grid, with all sites blocked
        if (N <= 0) {
            throw new IndexOutOfBoundsException("Percolation must be constructed with N > 0");
        }
        mDimension = N;
        mGrid = new Status[mDimension^2];
        mWeightedQuickUnionUF = new WeightedQuickUnionUF(mDimension^2);
    }

    private int getSiteIndex(int i, int j) {
        checkArguments(i, j);
        return (i - 1 * mDimension) + (j - 1);
    }

    private void checkArguments(int i, int j) {
        if (i < 1 || i > mDimension || j < 1 || j > mDimension) {
            throw new IndexOutOfBoundsException("(" + i + ", " + j + "): index must be > 0 and <= Percolation Dimension");
        }
    }

    public void open(int i, int j) {          // open site (row i, column j) if it is not open already
        int thisIndex = getSiteIndex(i, j);
        if (mGrid[thisIndex] == Status.BLOCKED) {
            mGrid[thisIndex] = Status.OPEN;
            // top
            if (i > 1 && isOpen(i - 1, j)) {
                mWeightedQuickUnionUF.union(thisIndex, getSiteIndex(i - 1, j));
            }
            // bottom
            if (i < mDimension && isOpen(i + 1, j)) {
                mWeightedQuickUnionUF.union(thisIndex, getSiteIndex(i + 1, j));
            }
            // left
            if (j > 1 && isOpen(i, j - 1)) {
                mWeightedQuickUnionUF.union(thisIndex, getSiteIndex(i, j - 1));
            }
            // right
            if (j < mDimension && isOpen(i, j + 1)) {
                mWeightedQuickUnionUF.union(thisIndex, getSiteIndex(i, j + 1));
            }
        }
    }

    public boolean isOpen(int i, int j) {     // is site (row i, column j) open?
        return mGrid[getSiteIndex(i, j)] == Status.OPEN;
    }

    public boolean isFull(int i, int j) {     // is site (row i, column j) full?
        System.out.println("checking isFull: " + i + ", " + j);
        boolean result = false;
        int index = getSiteIndex(i, j);
        for (int k = 0; k < mDimension; k++) {
            if (mWeightedQuickUnionUF.connected(k, index)) {
                result = true;
            }
        }
        System.out.println("isFull: " + result ? "true" : "false");
        return result;
    }
    public boolean percolates() {
        // does the system percolate?
        for (int j = 1; j <= mDimension; j++) {
            if (isFull(mDimension, j)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String... args) {
        Percolation p = new Percolation(3);
        System.out.println("Word");

    }

}
