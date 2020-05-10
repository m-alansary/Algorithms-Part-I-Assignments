import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Solver {
    private MinPQ<Node> nodes;
    private MinPQ<Node> twinNodes;
    private Node solution;
    private boolean solvable;

    private class Node {
        private Board current;
        private Node previous = null;
        private int moves = 0;

        public Node(Board initial) {
            current = initial;
        }

        public Node(Node node) {
            this.current = node.current;
            this.previous = node.previous;
            this.moves = node.moves;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        nodes = new MinPQ<Node>(nodeComparator);
        twinNodes = new MinPQ<Node>(nodeComparator);

        nodes.insert(new Node(initial));
        twinNodes.insert(new Node(initial.twin()));

        Node minNode, minTwinNode;
        do {
            minNode = nodes.delMin();
            minTwinNode = twinNodes.delMin();

            for (Board board : minNode.current.neighbors()) {
                if (minNode.previous == null || !board.equals(minNode.previous.current)) {
                    Node node = new Node(board);
                    node.previous = minNode;
                    node.moves = minNode.moves + 1;
                    nodes.insert(node);
                }
            }

            for (Board board : minTwinNode.current.neighbors()) {
                if (minTwinNode.previous == null || !board.equals(minTwinNode.previous.current)) {
                    Node node = new Node(board);
                    node.previous = minTwinNode;
                    node.moves = minTwinNode.moves + 1;
                    twinNodes.insert(node);
                }
            }
        } while (!minNode.current.isGoal() && !minTwinNode.current.isGoal());
        if (minNode.current.isGoal()) {
            solvable = true;
            solution = minNode;
        } else {
            solvable = false;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolvable()) {
            return solution.moves;
        }
        return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        int moves = solution.moves;
        Board[] boards = new Board[moves + 1];
        Node node = new Node(solution);
        for (int i = moves; i >= 0; i--) {
            boards[i] = node.current;
            node = node.previous;
        }
        return Arrays.asList(boards);
    }

    private Comparator<Node> nodeComparator = new Comparator<Node>() {
        @Override
        public int compare(Node a, Node b) {
            return a.current.manhattan() + a.moves - b.current.manhattan() - b.moves;
        }
    };

    // test client (see below)
    public static void main(String[] args) {
        int n;
        n = StdIn.readInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = StdIn.readInt();
            }
        }
        Board board = new Board(a);
        Solver solver = new Solver(board);
        int moves = solver.moves();
        if (solver.isSolvable()) {
            Iterable<Board> boards = solver.solution();
            for (Board b : boards) {
                StdOut.println(b.toString());
            }
        }
    }

}
