import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segs;
    private ArrayList<Segment> segmentArrayList;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new NullPointerException();
        }


        segmentArrayList = new ArrayList<>();
        segs = new ArrayList<>();
        int n = points.length;
        for (Point p : points) {
            if (p == null) throw new NullPointerException();
        }
        Point[] originalPoints = new Point[n];

        for (int i = 0; i < n; i++) {
            originalPoints[i] = points[i];
        }

        Arrays.sort(originalPoints);

        for (int i = 1; i < n; i++) {
            if (points[i - 1].slopeTo(points[i]) == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException();
            }
        }


        for (Point p : originalPoints) {

            Arrays.sort(points, p.slopeOrder());
//            for (int i = 0; i < points.length; i++) {
//                StdOut.print(" " + p.slopeTo(points[i]));
//            }
//            StdOut.print("\n");
            int count = 1;
            double slope = p.slopeTo(points[0]);
            int i = 1;
            for (; i < points.length; i++) {
                double currentSlope = p.slopeTo(points[i]);
                if (currentSlope == slope) {
                    count++;
                } else {
                    if (count > 2) break;
                    count = 1;
                }
                slope = currentSlope;
            }
            if (count > 2) {
                Arrays.sort(points, i - count, i);
                Point start = min(p, points[i - count]);
                Point end = max(p, points[i - 1]);

                LineSegment ls = new LineSegment(start, end);
                Segment s = new Segment();
                s.start = start;
                s.end = end;
                s.count = count;
                s.slope = s.start.slopeTo(s.end);

                boolean canAdd = true;

                for (int j = 0; j < segmentArrayList.size(); j++) {
                    Segment e = segmentArrayList.get(j);
                    if (s.slope == e.slope) {
                        if (s.slope == 0) {
                            boolean sameLine = (e.start.slopeTo(start) == 0
                                    || e.start.slopeTo(start) == Double.NEGATIVE_INFINITY);
                            if (sameLine && e.count < count) {
                                segmentArrayList.set(j, s);
                                segs.set(j, ls);
                            }
                            canAdd = !sameLine;

                        } else if (s.slope == Double.POSITIVE_INFINITY || s.slope == Double.NEGATIVE_INFINITY) {
                            boolean sameLine = (e.start.slopeTo(start) == Double.POSITIVE_INFINITY)
                                    || (e.start.slopeTo(start) == Double.NEGATIVE_INFINITY
                                    || e.start.slopeTo(start) == 0);
                            if (sameLine && e.count < count) {
                                segmentArrayList.set(j, s);
                                segs.set(j, ls);
                            }
                            canAdd = !sameLine;

                        } else {
                            // parallel lines or same line
                            boolean sameLine = (e.start.slopeTo(start) == s.slope)
                                    || (e.start.slopeTo(start) == Double.NEGATIVE_INFINITY)
                                    || (e.start.slopeTo(start) == -s.slope);
                            if (sameLine && e.count < count) {
                                segmentArrayList.set(j, s);
                                segs.set(j, ls);
                            }
                            canAdd = !sameLine;
                        }

                    }
                    if (!canAdd) break;
                }
                if (canAdd) {
                    segmentArrayList.add(s);
                    segs.add(ls);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.segments().length);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


    private Point min(Point p1, Point p2) {

        return (p1.compareTo(p2) < 0) ? p1 : p2;
    }

    private Point max(Point p1, Point p2) {

        return (p1.compareTo(p2) > 0) ? p1 : p2;
    }

    public int numberOfSegments() {
        return segs.size();
    }        // the number of line segments

    public LineSegment[] segments() {

        LineSegment[] segments = new LineSegment[segs.size()];
        for (int i = 0; i < segs.size(); i++) {
            segments[i] = segs.get(i);
            Segment segment = segmentArrayList.get(i);
            // StdOut.println(segment.start + " -> " + segment.end + " [ " + segment.count + "] " + segment.slope);
        }
        return segments;
    }
    // the line segments

    private class Segment {
        private int count;
        private Point start;
        private Point end;
        private double slope;
    }
}
