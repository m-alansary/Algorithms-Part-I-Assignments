import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public final class Board {
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

//    public Board(Board board) {
//        int n = board.dimension();
//        this.tiles = new int[n][n];
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                this.tiles[i][j] = board.tiles[i][j];
//            }
//        }
//    }

    // string representation of this board
    public String toString() {
        Integer n = tiles.length;
        StringBuilder s = new StringBuilder(n.toString());
        s.append("\n");
        for (int i = 0; i < n; i++) {
            s.append(" ");
            for (int j = 0; j < n; j++) {
                s.append(tiles[i][j]).append("\t");
            }
            if (i != n - 1)
                s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int result = 0;
        int n = tiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != j + i * tiles.length + 1 && tiles[i][j] != 0)
                    result++;
            }
        }
        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        int n = tiles.length;
        int col, row;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                row = (tiles[i][j] - 1) / tiles.length;
                col = (tiles[i][j] - 1) % tiles.length;
                result += Math.abs(row - i) + Math.abs(col - j);
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int num = 1;
        int n = tiles.length;
        if (tiles[n - 1][n - 1] != 0)
            return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    break;
                if (tiles[i][j] != num)
                    return false;
                num++;
            }
        }
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y == this)
            return true;

        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension())
            return false;
        int n = this.dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int zeroR = 0, zeroC = 0;
        int n = tiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    zeroR = i;
                    zeroC = j;
                }
            }
        }
        Board[] base = new Board[4];
        int x = 0;
        if (zeroC - 1 >= 0) {
            base[x] = new Board(this.tiles);
            base[x].tiles[zeroR][zeroC] = base[x].tiles[zeroR][zeroC - 1];
            base[x].tiles[zeroR][zeroC - 1] = 0;
            x++;
        }
        if (zeroC + 1 <= n - 1) {
            base[x] = new Board(this.tiles);
            base[x].tiles[zeroR][zeroC] = base[x].tiles[zeroR][zeroC + 1];
            base[x].tiles[zeroR][zeroC + 1] = 0;
            x++;
        }
        if (zeroR - 1 >= 0) {
            base[x] = new Board(this.tiles);
            base[x].tiles[zeroR][zeroC] = base[x].tiles[zeroR - 1][zeroC];
            base[x].tiles[zeroR - 1][zeroC] = 0;
            x++;
        }
        if (zeroR + 1 <= n - 1) {
            base[x] = new Board(this.tiles);
            base[x].tiles[zeroR][zeroC] = base[x].tiles[zeroR + 1][zeroC];
            base[x].tiles[zeroR + 1][zeroC] = 0;
            x++;
        }
        Board[] results = new Board[x];
        for (int i = 0; i < x; i++) {
            results[i] = base[i];
        }
        return Arrays.asList(results);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board board = new Board(this.tiles);
        if (board.tiles[0][0] != 0 && board.tiles[0][1] != 0) {
            int t = board.tiles[0][0];
            board.tiles[0][0] = board.tiles[0][1];
            board.tiles[0][1] = t;
        } else {
            int t = board.tiles[1][0];
            board.tiles[1][0] = board.tiles[1][1];
            board.tiles[1][1] = t;
        }
        return board;
    }


//    private class ListIterator implements Iterator<Board> {
//        private Board[] boards;
//        private int it = 0;
//
//        ListIterator(Board[] boards) {
//            int n = boards.length;
//            this.boards = new Board[n];
//            for (int i = 0; i < n; i++) {
//                this.boards[i] = boards[i];
//            }
//        }
//
//        @Override
//        public boolean hasNext() {
//            return it != boards.length;
//        }
//
//        @Override
//        public Board next() {
//            Board b = boards[it];
//            it++;
//            return b;
//        }
//    }


    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {{4, 7, 1}, {3, 0, 8}, {2, 5, 6}};
        Board board = new Board(tiles);
        if (board.equals(board))
            StdOut.println(true);
        StdOut.println(board.toString());
        StdOut.println(board.dimension());
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println(board.isGoal());
        Iterable<Board> boards = board.neighbors();
        StdOut.println(board.toString());
        for (Board i : boards) {
            StdOut.println(i.toString());
        }
    }

}

