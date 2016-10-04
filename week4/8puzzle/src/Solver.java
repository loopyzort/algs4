import edu.princeton.cs.algs4.*;

public class Solver {
    private MinPQ<Node> priorityQueue = new MinPQ<>();
    private Node solution = null;

    private class Node implements Comparable<Node> {
        private final Node previous;
        private final Board board;
        private final int moves;

        Node(Node previous, Board board, int moves) {
            this.previous = previous;
            this.board = board;
            this.moves = moves;
        }

        Iterable<Node> children() {
            Stack<Node> result = new Stack<>();
            for (Board neighbor : board.neighbors()) {
                if (previous == null || !previous.board.equals(neighbor)) {
                    result.push(new Node(this, neighbor, moves + 1));
                }
            }
            return result;
        }

        @Override
        public int compareTo(Node comp) {
            int priority = board.manhattan() + moves;
            int compPriority = comp.board.manhattan() + comp.moves;
            if (priority == compPriority) {
                return 0;
            } else if (priority > compPriority) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("Instantiated with a null argument");
        }
        priorityQueue.insert(new Node(null, initial, 0));
        solution = solve();
    }

    private Node solve() {
        if (priorityQueue.isEmpty()) {
            // no solution
            return null;
        }
        Node min = priorityQueue.delMin();
        if (min.board.isGoal()) {
            return min;
        } else {
            for (Node child : min.children()) {
                priorityQueue.insert(child);
            }
            return solve();
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution == null ? -1 : solution.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solution != null) {
            Stack<Board> boards = new Stack<>();
            Node currentNode = solution;
            while (currentNode != null) {
                boards.push(currentNode.board);
                currentNode = currentNode.previous;
            }
            return boards;
        } else {
            return null;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

