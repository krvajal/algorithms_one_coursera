import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {


    private Node solutionNode = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        Node root = new Node(null, initial);

        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> pqAlternative = new MinPQ<>();
        pq.insert(root);
        Node alternativeInitialNode = new Node(null, initial.twin());
        int delMinCount = 0;
        boolean firstTurn = true;
        pqAlternative.insert(alternativeInitialNode);
        while (!pq.isEmpty()) {
            if (firstTurn) {
                firstTurn = false;
                Node node = pq.delMin();
                delMinCount++;
//                StdOut.println(node.priority);
//                StdOut.println(node.board);
                if (node.board.isGoal()) {
                    solutionNode = node;
                    break;
                } else {
                    for (Board b : node.board.neighbors()) {
                        if (node.parent != null && b.equals(node.parent.board)) continue;
                        Node n = new Node(node, b);
                        n.board = b;
                        n.parent = node;
                        n.moves = node.moves + 1;
                        pq.insert(n);
                    }
                }
            } else {

                firstTurn = true;
                if (!pqAlternative.isEmpty()) {
                    Node node = pqAlternative.delMin();
                    delMinCount++;
                    if (node.board.isGoal()) {
                        solutionNode = null;
                        break;
                    } else {
                        for (Board b : node.board.neighbors()) {
                            if (node.parent != null && b.equals(node.parent.board)) continue;
                            Node n = new Node(node, b);
                            pqAlternative.insert(n);
                        }
                    }
                }
            }
        }
        StdOut.println(delMinCount);
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

    // is the initial board solvable?
    public boolean isSolvable() {
        return solutionNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return (solutionNode != null) ? solutionNode.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        if (solutionNode == null) return null;
        ArrayList<Board> sol = new ArrayList<>();
        Node n = solutionNode;
        while (n != null) {
            sol.add(0, n.board);
            n = n.parent;
        }
        return sol;
    }

    private class Node implements Comparable<Node> {

        private Node parent;
        private Board board;
        private int moves;
        private int priority;

        private Node(Node parent, Board board) {
            this.parent = parent;
            this.board = board;
            this.moves = (parent != null) ? (parent.moves + 1) : 0;
            this.priority = moves + this.board.manhattan();
        }

        @Override
        public int compareTo(Node that) {

           if(this.priority == that.priority)
           {
               int ham1 = this.priority - this.moves;
               int ham2 = that.priority - that.moves;
                if(ham1 == ham2){
                   return this.board.hamming() - that.board.hamming();
                }else{
                    return  ham1 - ham2;
                }
           }

            return this.priority - that.priority;
        }


    }
}