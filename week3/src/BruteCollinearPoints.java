import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by carvajal on 1/19/17.
 */
public class BruteCollinearPoints {


    private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {


        if (points == null) {
            throw new NullPointerException();
        }
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i - 1].slopeTo(points[i]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) throw new NullPointerException();
        }
        int n = points.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p1 = points[i];
                        Point p2 = points[j];
                        Point p3 = points[k];
                        Point p4 = points[l];
                        Point first = min(min(p1, p2), min(p3, p4));
                        Point last = max(max(p1, p2), max(p3, p4));
                        double slope = first.slopeTo(last);

                        if (!inSegment(first, p1, slope)) continue;
                        if (!inSegment(first, p2, slope)) continue;
                        if (!inSegment(first, p3, slope)) continue;
                        if (!inSegment(first, p4, slope)) continue;
                        LineSegment segment = new LineSegment(first, last);
                        if (!segs.contains(segment)) {
                            segs.add(segment);
                        }

                    }
                }
            }
        }


    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println(collinear.segments().length);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private boolean equals(Point p1, Point p2) {
        return p1.compareTo(p2) == 0;
    }

    private boolean inSegment(Point first, Point p1, double slope) {
        if (first == p1) return true;
        return first.slopeTo(p1) == slope;
    }

    private Point min(Point p1, Point p2) {

        return (p1.compareTo(p2) < 0) ? p1 : p2;
    }

    private Point max(Point p1, Point p2) {

        return (p1.compareTo(p2) > 0) ? p1 : p2;
    }

    public int numberOfSegments() {
        return segs.size();
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segs.size()];
        for (int i = 0; i < segs.size(); i++) {
            segments[i] = segs.get(i);
        }
        return segments;
    }


}
