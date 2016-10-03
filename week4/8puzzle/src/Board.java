import java.util.Arrays;

public class Board {
    private final int[][] blocks;

    // construct a board from an n-by-narray of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = blocks;
    }

    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            return false;
        }
        Board comp = (Board) y;
        if (comp.dimension() != comp.dimension()) {
            return false;
        }
        for (int i = 0; i < dimension(); i++) {
            if (!Arrays.equals(blocks[i], comp.blocks[i])) {
                return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder builder = new StringBuilder().append(dimension()).append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                builder.append(" ").append(blocks[i][j]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }


    // unit tests (not graded)
    public static void main(String[] args) {
        // test equality
        int[][] data = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board subject = new Board(data);
        assert !(subject.equals("bunk"));
        assert !(subject.equals(null));
        assert subject.equals(new Board(data));
        Board smaller = new Board(new int[][]{{1, 2}, {3, 4}});
        assert !(subject.equals(smaller));
        int[][] differentData = new int[][]{{1, 3, 2}, {4, 5, 6}, {7, 8, 0}};
        Board different = new Board(differentData);
        assert !(subject.equals(different));

    }
}
