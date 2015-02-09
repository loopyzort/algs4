public class Percolation {
    private final WeightedQuickUnionUF mUFTopAndBottom;
    private final WeightedQuickUnionUF mUFTopOnly;
    private final int mDimension;
    private final byte[] mGrid;
    private final int mVirtualTop;
    private final int mVirtualBottom;

    public Percolation(int N) {  // create N-by-N grid, with all sites blocked
        if (N <= 0) {
            throw new IllegalArgumentException("Percolation must be constructed "
                    + "with N > 0");
        }
        mDimension = N;
        mGrid = new byte[N * N];
        mUFTopAndBottom = new WeightedQuickUnionUF(N * N + 2);
        mUFTopOnly = new WeightedQuickUnionUF(N * N + 1);
        mVirtualTop = N * N;
        mVirtualBottom = mVirtualTop + 1;
    }

    private int getSiteIndex(int i, int j) {
        checkArguments(i, j);
        return ((i - 1) * mDimension) + (j - 1);
    }

    private void checkArguments(int i, int j) {
        if (i < 1 || i > mDimension || j < 1 || j > mDimension) {
            throw new IndexOutOfBoundsException("index must be > 0 and"
                    + "<= Percolation Dimension");
        }
    }

    public void open(int i, int j) {
        int thisIndex = getSiteIndex(i, j);
        if (mGrid[thisIndex] != 1) {
            mGrid[thisIndex] = 1;
            if (i == 1) { // VirtualTop
                mUFTopAndBottom.union(thisIndex, mVirtualTop);
                mUFTopOnly.union(thisIndex, mVirtualTop);
            }
            else if (i > 1 && isOpen(i - 1, j)) { // top
                mUFTopAndBottom.union(thisIndex, getSiteIndex(i - 1, j));
                mUFTopOnly.union(thisIndex, getSiteIndex(i - 1, j));
            }
            if (i == mDimension) { // VirtualBottom
                mUFTopAndBottom.union(thisIndex, mVirtualBottom);
            }
            else if (i < mDimension && isOpen(i + 1, j)) { // bottom
                mUFTopAndBottom.union(thisIndex, getSiteIndex(i + 1, j));
                mUFTopOnly.union(thisIndex, getSiteIndex(i + 1, j));
            }
            // left
            if (j > 1 && isOpen(i, j - 1)) {
                mUFTopAndBottom.union(thisIndex, getSiteIndex(i, j - 1));
                mUFTopOnly.union(thisIndex, getSiteIndex(i, j - 1));
            }
            // right
            if (j < mDimension && isOpen(i, j + 1)) {
                mUFTopAndBottom.union(thisIndex, getSiteIndex(i, j + 1));
                mUFTopOnly.union(thisIndex, getSiteIndex(i, j + 1));
            }
        }
    }

    public boolean isOpen(int i, int j) {     // is site (row i, column j) open?
        return mGrid[getSiteIndex(i, j)] == 1;
    }

    public boolean isFull(int i, int j) {     // is site (row i, column j) full?
        int index = getSiteIndex(i, j);
        return mUFTopOnly.connected(mVirtualTop, index);
    }

    public boolean percolates() {
        return mUFTopAndBottom.connected(mVirtualTop, mVirtualBottom);
    }

}
