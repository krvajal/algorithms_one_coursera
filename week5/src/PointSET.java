import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;


/**
 * Created by carvajal on 1/30/17.
 */
public class PointSET {

    private SET<Point2D> tree;

    public PointSET() {
        tree = new SET<>();

    }

    public static void main(String[] args) {

    }

    public boolean isEmpty() {

        return tree.isEmpty();
    }

    public int size() {
        return tree.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        tree.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return tree.contains(p);
    }

    public void draw() {
        for (Point2D p : tree) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        ArrayList<Point2D> points = new ArrayList<>();
        for (Point2D p : tree) {
            if (rect.contains(p)) {
                points.add(p);
            }
        }

        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        Point2D nearestPoint = null;
        double nearestDistance = 2.0;
        for (Point2D pp : tree) {
            double dist = pp.distanceSquaredTo(p);
            if (dist < nearestDistance) {
                nearestDistance = dist;
                nearestPoint = pp;
            }
        }
        return nearestPoint;
    }
}
