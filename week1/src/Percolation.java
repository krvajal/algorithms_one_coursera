/**
 * Created by carvajal on 1/5/17.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 * This class  models a percolation
 * problem of a 2D grid with nxn sites.
 *
 * @author Miguel Carvajal
 */
public class Percolation {

    private int n;
    private int openSites;

    private boolean[][] sites;
    private WeightedQuickUnionUF alg;
    private int nodes;

    /**
     * Class constructor specifing the dimensions of the
     * grid.
     *
     * @param nn
     */
    public Percolation(int nn) {

        if (nn <= 0) {
            throw new IllegalArgumentException();
        }
        n = nn;
        openSites = 0;

        sites = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = false;
            }
        }
        nodes = n * n + 2;
        alg = new WeightedQuickUnionUF(nodes);
        // the last two sites are the up and down site


    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(6);

        StdOut.println(percolation.isFull(1, 1));


//        int n = 100;
//        for (int i = 0; i < 100; i++) {
//            Percolation percolation = new Percolation(n);
//            while (!percolation.percolates() && (percolation.numberOfOpenSites() != n * n)) {
//                int row = StdRandom.uniform(1, n + 1);
//                int col = StdRandom.uniform(1, n + 1);
//                percolation.open(row, col);
////                System.out.println("added site at " + row  + ", " + col);
//            }

//            System.out.println("NumOpenSites: " + percolation.numberOfOpenSites());
//            System.out.println(percolation.numberOfOpenSites() / (n * n * 1.0f));
//        }

    }

    /**
     * Private method that maps a site index to
     * a node in the connection tree
     *
     * @param row the site row
     * @param col the site col
     * @return an index from 0 to nodes - 3
     */
    private int siteToIndex(int row, int col) {
        int index = (row - 1) * n + col - 1;
        return index;
    }

    /**
     * Private function to check is the give row and
     * col belongs to a site in the grid
     *
     * @param row
     * @param col
     */
    private void checkBounds(int row, int col) {

        if (row < 1 || row > n) {
            throw new IndexOutOfBoundsException();
        }
        if (col < 1 || col > n) {
            throw new IndexOutOfBoundsException();
        }


    }

    public void open(int row, int col) {
        checkBounds(row, col);

        if (!isOpen(row, col)) {
            sites[row - 1][col - 1] = true;
            int index = siteToIndex(row, col);
            // connect with boundary sites
            for (int delta = -1; delta <= 1; delta += 2) {

                int x = row + delta;
                int y = col + delta;
                if (x > 0 && x <= n) {
                    if (isOpen(x, col) && !alg.connected(index, siteToIndex(x, col))) {
                        alg.union(index, siteToIndex(x, col));
                    }
                }

                if (y > 0 && y <= n) {
                    if (isOpen(row, y) && !alg.connected(index, siteToIndex(row, y))) {
                        alg.union(index, siteToIndex(row, y));
                    }
                }

            }
            if (row == 1) {
                // top row
                alg.union(siteToIndex(row, col), nodes - 2);
            }
            if (row == n) {
                // bottom row
                alg.union(siteToIndex(row, col), nodes - 1);
            }
            openSites++;
        }
    }

    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return sites[row - 1][col - 1];
    }

    /**
     * A full site is an open site that can be connected to an
     * open site in the top row via a chain of neighboring
     * (left, right, up, down) open sites
     *
     * @param row the row of the grid (1..n)
     * @param col the column of the grid (1..n)
     * @return boolean true is can be connected to an opensite int the top row
     */
    public boolean isFull(int row, int col) {

        checkBounds(row, col);

        return alg.connected(nodes - 2, siteToIndex(row, col));

    }

    public int numberOfOpenSites() {

        return openSites;
    }

    public boolean percolates() {

        return alg.connected(nodes - 2, nodes - 1);
    }

}
