import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 */
public class FastCollinearPoints {
    private static final int MIN_ADDL_POINTS_FOR_SEGMENT = 3;
    private List<LineSegment> segments = new ArrayList<LineSegment>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("Invalid points");
        }
        Point[] orderedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(orderedPoints);
        Point lastPoint = null;
        // validate the input data
        for (Point p : orderedPoints) {
            checkForNull(p);
            if (lastPoint != null && lastPoint.compareTo(p) == 0) {
                throw new IllegalArgumentException("Repeated point at index: " + p);
            }
            lastPoint = p;
        }

        for (int i = 0; i < orderedPoints.length; i++) {
            Point p = orderedPoints[i];
            // keep track of the index of the first point to have the current slope
            int first = i + 1;
            Arrays.sort(orderedPoints, first, orderedPoints.length, p.slopeOrder());
            // for each remaining entry in the array, q, calculate the slope
            for (int q = first + 1; q <= orderedPoints.length; q++) {
                // if we hit the end or the slopes are different
                if (q == orderedPoints.length ||
                        p.slopeTo(orderedPoints[first]) != p.slopeTo(orderedPoints[q])) {
                    // see if we have enough orderedPoints to create a segment
                    if (q - first >= MIN_ADDL_POINTS_FOR_SEGMENT) {
                        //int last = q == orderedPoints.length ? q - 1 : q;
                        segments.add(createMaxLineSegment(orderedPoints, p, first, q));
                    }
                    first = q;
                }
            }
        }
    }

    private LineSegment createMaxLineSegment(Point[] points, Point point, int start, int end) {
        Point[] candidates = new Point[end - start + 1];
        // copy the contents to a new array
        int k = 0;
        for (int i = start; i < end; i++) {
            candidates[k++] = points[i];
        }
        candidates[k] = point;
        Arrays.sort(candidates);
        return new LineSegment(candidates[0], candidates[candidates.length - 1]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    private void checkForNull(Point p) {
        if (p == null) {
            throw new NullPointerException("Null point found");
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

