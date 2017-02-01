/**
 * Created by carvajal on 1/29/17.
 */
public class RedBlackTree {

    private char BLACK = 0;
    private char RED = 1;

    private Node rotateLeft(Node x) {

        Node h = x;
        return x;

    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;

    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private boolean isRed(Node h) {
        if (h == null) return  false;
        return  h.color == RED;
    }

    private class Node {


        Node left, right;

        private char color;
    }
}
