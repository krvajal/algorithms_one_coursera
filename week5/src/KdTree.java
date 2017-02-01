import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.ArrayList;


/**
 * Created by carvajal on 1/31/17.
 */
public class KdTree {

    private static final char HORZ = 0, VERT = 1;
    private Node root = null;
    private int size = 0;

    public KdTree() {

    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        StdOut.println(tree.size());
        tree.insert(new Point2D(1, 1));
        tree.insert(new Point2D(1.01, 2));
        StdOut.println(tree.size());
        StdOut.println(tree.contains(new Point2D(1, 2)));
        StdOut.println(tree.contains(new Point2D(1, 1)));
        StdOut.println(tree.contains(new Point2D(0, 1)));
        StdOut.println(tree.contains(new Point2D(1, 0.5)));
        StdOut.println(tree.nearest(new Point2D(1, 2.01)));
        StdOut.println(tree.nearest(new Point2D(1, 0.9)));
    }

    public boolean isEmpty() {

        return size == 0;
    }

    public int size() {
        return size;
    }

    private void insert(Node n, Point2D p) {
        if (p == null) throw new NullPointerException();
        if (n.point.x() == p.x() && n.point.y() == p.y()) return; // the point existed already
        if (n.orient == VERT) {
            if (p.x() < n.point.x()) {
                if (n.left != null) {
                    // go left
                    insert(n.left, p);

                } else {

                    // create node
                    n.left = new Node();
                    n.left.point = p;
                    n.left.orient = HORZ;
                    n.left.parent = n;
                    // remove a piece of the rectagle from the right
                    n.left.rect = new RectHV(n.rect.xmin(), n.rect.ymin(), n.point.x(), n.rect.ymax());

                    size++;
                }

            } else {
                if (n.right != null) {

                    // go right
                    insert(n.right, p);
                } else {
                    n.right = new Node();
                    n.right.point = p;
                    n.right.orient = HORZ;
                    n.right.parent = n;
                    n.right.rect = new RectHV(n.point.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());
                    size++;
                }

            }
        } else {
            if (p.y() < n.point.y()) {
                if (n.left != null) {
                    // go left
                    insert(n.left, p);

                } else {
                    // create node
                    n.left = new Node();
                    n.left.point = p;
                    n.left.orient = VERT;
                    n.left.parent = n;
                    // remove a piece of the parent rectangle from the right
                    n.left.rect = new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.point.y());
                    size++;
                }
            } else {
                if (n.right != null) {
                    // go left
                    insert(n.right, p);

                } else {
                    // create node
                    n.right = new Node();
                    n.right.point = p;
                    n.right.orient = VERT;
                    n.right.parent = n;
                    // up
                    n.right.rect = new RectHV(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.rect.ymax());
                    size++;
                }
            }

        }
    }

    public void insert(Point2D p) {

        if (p == null) throw new NullPointerException();

        if (root == null) {
            root = new Node();
            root.point = p;
            root.orient = VERT;
            root.rect = new RectHV(0, 0, 1, 1);
            size++;
        } else {
            insert(root, p);
        }

    }

    private boolean search(Node n, Point2D p) {
        if (n == null) return false;
        if (n.point.x() == p.x() && n.point.y() == p.y()) return true;
        if (n.orient == VERT) {
            if (p.x() < n.point.x()) {
                return search(n.left, p);
            } else {
                return search(n.right, p);
            }
        } else {
            if (p.y() < n.point.y()) {
                return search(n.left, p);
            } else {
                return search(n.right, p);
            }
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return search(root, p);
    }

    private void draw(Node n) {

        if (n == null) return;
        Node parent = n.parent;
        if (n.orient == HORZ) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.line(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.point.y());

        } else {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.line(n.point.x(), n.rect.ymin(), n.point.x(), n.rect.ymax());

        }
        StdDraw.circle(n.point.x(), n.point.y(), 0.01);
        draw(n.left);
        draw(n.right);
    }

    public void draw() {
        draw(root);

    }

    private void range(RectHV rect, Node n, ArrayList<Point2D> points) {

        if (n == null) return;
        if (!n.rect.intersects(rect)) return;
        if (rect.contains(n.point)) points.add(n.point);
        range(rect, n.left, points);
        range(rect, n.right, points);

    }

    public Iterable<Point2D> range(RectHV rect) {

        ArrayList<Point2D> points = new ArrayList<>();
        range(rect, root, points);
        return points;
    }

    // return new nearest
    private Point2D nearest(Node n, Point2D searchPoint, Point2D currentNearest, double currentMinDist) {


        if (n == null) return currentNearest;
        if (currentNearest == null) {
            currentNearest = n.point;
        }


        double currentDist = n.point.distanceSquaredTo(searchPoint);

        if (currentDist < currentMinDist) {
            currentNearest = n.point;
            currentMinDist = currentDist;
        }
        Point2D nearestLeft = nearest(n.left, searchPoint, currentNearest, currentMinDist);
        double distl = nearestLeft.distanceSquaredTo(searchPoint);
        if (distl < currentMinDist) {
            currentMinDist = distl;
            currentNearest = nearestLeft;

        }
        Point2D nearestRight = nearest(n.right, searchPoint, currentNearest, currentMinDist);
        double distr = nearestRight.distanceSquaredTo(searchPoint);
        if (distr < currentMinDist) {

            currentNearest = nearestRight;
        }
        return currentNearest;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();

        return nearest(root, p, null, 2);

    }

    private class Node {
        private RectHV rect;
        private char orient;
        private Point2D point;
        private Node left = null;
        private Node right = null;
        private Node parent = null;
    }
}
