import edu.princeton.cs.algs4.Stack;

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
        int score = 0;
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                int val = blocks[row][col];
                // the last value should always be zero
                if (!(row == dimension() - 1 && col == dimension() - 1) &&
                        val != (dimension() * row) + col + 1) {
                    score++;
                }
            }
        }
        return score;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int score = 0;
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                int val = blocks[row][col];
                if (val == 0) {
                    continue;
                }
                int eCol = (val - 1) % dimension();
                int eRow = (val - 1) / dimension();
                score += row > eRow ? row - eRow : eRow - row;
                score += col > eCol ? col - eCol : eCol - col;
            }
        }
        return score;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int row0 = -1;
        int col0 = -1;
        int row1 = -1;
        int col1 = -1;
        int[][] blocks = copyBlocks(this.blocks);
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (blocks[row][col] != 0) {
                    if (row0 == -1) {
                        row0 = row;
                        col0 = col;
                    } else {
                        row1 = row;
                        col1 = col;
                        break;
                    }
                }
            }
            if (row0 != -1 && row1 != -1) {
                break;
            }
        }
        int tmp = blocks[row0][col0];
        blocks[row0][col0] = blocks[row1][col1];
        blocks[row1][col1] = tmp;
        return new Board(blocks);
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
        Stack<Board> result = new Stack<>();
        // first find the empty space
        int row = 0;
        int col = 0;
        boolean foundEmpty = false;
        for (row = 0; row < dimension(); row++) {
            for (col = 0; col < dimension(); col++) {
                if (blocks[row][col] == 0) {
                    foundEmpty = true;
                    break;
                }
            }
            if (foundEmpty) {
                break;
            }
        }
        // go through all the ways we can swap
        if (row > 0) {
            Board neighbor = new Board(copyBlocks(this.blocks));
            swap(neighbor, row, col, row - 1, col);
            result.push(neighbor);
        }
        if (row < dimension() - 1) {
            Board neighbor = new Board(copyBlocks(this.blocks));
            swap(neighbor, row, col, row + 1, col);
            result.push(neighbor);
        }
        if (col > 0) {
            Board neighbor = new Board(copyBlocks(this.blocks));
            swap(neighbor, row, col, row, col - 1);
            result.push(neighbor);
        }
        if (col < dimension() - 1) {
            Board neighbor = new Board(copyBlocks(this.blocks));
            swap(neighbor, row, col, row, col + 1);
            result.push(neighbor);
        }
        return result;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder builder = new StringBuilder().append(dimension()).append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                builder.append(String.format("%2d ", blocks[i][j]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private int[][] copyBlocks(int[][] blocks) {
        int[][] copy = new int[dimension()][];
        for (int i = 0; i < dimension(); i++) {
            copy[i] = Arrays.copyOf(blocks[i], dimension());
        }
        return copy;
    }

    private void swap(Board board, int row0, int col0, int row1, int col1) {
        int val = board.blocks[row0][col0];
        board.blocks[row0][col0] = board.blocks[row1][col1];
        board.blocks[row1][col1] = val;
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

        assert subject.isGoal();
        assert !different.isGoal();

        Board twin = subject.twin();
        assert !subject.equals(twin);
        assert !twin.isGoal();

        data = new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        subject = new Board(data);
        assert subject.hamming() == 5;
        assert subject.manhattan() == 10;


        data = new int[][]{{8, 1, 3}, {4, 2, 0}, {7, 6, 5}};
        subject = new Board(data);

        Board[] expectedNeighbors = new Board[]{
                new Board(new int[][]{{8, 1, 0}, {4, 2, 3}, {7, 6, 5}}),
                new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}}),
                new Board(new int[][]{{8, 1, 3}, {4, 2, 5}, {7, 6, 0}})
        };

        boolean foundExpectedNeighbors = false;
        Iterable<Board> actualNeighbors = subject.neighbors();
        for (Board expectedNeighbor : expectedNeighbors) {
            foundExpectedNeighbors = false;
            for (Board actualNeighbor : actualNeighbors) {
                if (actualNeighbor.equals(expectedNeighbor)) {
                    foundExpectedNeighbors = true;
                    break;
                }
            }
            if (!foundExpectedNeighbors) {
                break;
            }
        }
        assert foundExpectedNeighbors;
    }
}
