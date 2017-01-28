import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class Board {


    private int[][] blocks;
    private int blankRow;
    private int blankCol;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        int n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (this.blocks[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }

    // all neighboring boards
    public static void main(String[] args) {

        int[][] blocks = {{0, 1, 3}, {4, 8, 2}, {7, 6, 5}};
        Board board = new Board(blocks);
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println(board);
        for (Board b : board.neighbors()) {
            StdOut.println(b);
        }

    }

    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {

        int outOfPlaceCount = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != 0) {
                    int val = blocks.length * i + j + 1;
                    outOfPlaceCount += (val != blocks[i][j]) ? 1 : 0;
                }
            }
        }
        return outOfPlaceCount;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {

        int distancesCount = 0;
        int n = blocks.length;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != 0) {
                    int val = blocks[i][j] - 1;
                    int row = val / n;
                    int col = val % n;
                    distancesCount += Math.abs(row - i) + Math.abs(col - j);

                }
            }
        }
        return distancesCount;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int rowOne = blankRow;
        int colOne = blankCol;
        int rowTwo = blankRow;
        int colTwo = blankCol;

        while (rowOne == blankRow && colOne == blankCol) {
            rowOne = StdRandom.uniform(0, blocks.length);
            colOne = StdRandom.uniform(0, blocks.length);
        }
        while ((rowTwo == blankRow && colTwo == blankCol) ||
                (rowOne == rowTwo && colOne == colTwo)) {
            colTwo = StdRandom.uniform(0, blocks.length);
            rowTwo = StdRandom.uniform(0, blocks.length);
        }
        int val = blocks[rowOne][colOne];
        blocks[rowOne][colOne] = blocks[rowTwo][colTwo];
        blocks[rowTwo][colTwo] = val;
        Board b = new Board(blocks);
        blocks[rowTwo][colTwo] = blocks[rowOne][colOne];
        blocks[rowOne][colOne] = val;
        return b;
    }

    // does this board equal y?
    public boolean equals(Object that) {
        if (that == null) return false;
        if (that == this) return true;
        if (this.getClass() != that.getClass()) return false;
        Board b = (Board) that;
        if (this.dimension() != b.dimension()) return false;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (this.blocks[i][j] != b.blocks[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neigh = new ArrayList<>();
        int n = blocks.length;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int x = dx + blankCol;
                int y = dy + blankRow;
                if (dx != 0 && dy != 0) continue;
                if (dx == 0 && dy == 0) continue;
                if (x >= 0 && x < n) {
                    if (y >= 0 && y < n) {

                        blocks[blankRow][blankCol] = blocks[y][x];
                        blocks[y][x] = 0;
                        neigh.add(new Board(blocks));
                        blocks[y][x] = blocks[blankRow][blankCol];
                        blocks[blankRow][blankCol] = 0;
                    }
                }
            }
        }
        return neigh;
    }


    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(blocks.length);
        builder.append("\n");
        String format = "%2d";
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                builder.append(String.format(format, blocks[i][j]));
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}