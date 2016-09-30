import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 */
public class FastCollinearPoints {
    private static final int MIN_ADDL_POINTS_FOR_SEGMENT = 3;
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        LineSegment[] tmp = new LineSegment[points.length];
        int pointCount = 0;
        Point lastPoint = null;
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            if (p == null) {
                throw new NullPointerException("Null value at index: " + i);
            }
            if (lastPoint != null && p.compareTo(lastPoint) == 0) {
                throw new IllegalArgumentException("Two identical points exist in the array");
            }
            lastPoint = p;
            // keep track of the index of the first point to have the current slope
            int first = i + 1;
            Arrays.sort(points, first, points.length, p.slopeOrder());
            // for each remaining entry in the array, q, calculate the slope
            for (int q = first + 1; q <= points.length; q++) {
                // if we hit the end or found a change in slope
                if (q == points.length || p.slopeTo(points[first]) != p.slopeTo(points[q])) {
                    // see if we have enough points to createt a segment
                    if (q - first >= MIN_ADDL_POINTS_FOR_SEGMENT) {
                        tmp[pointCount++] = createMaxLineSegment(points, p, first, q - 1);
                    }
                    first = q;
                }
            }
            segments = java.util.Arrays.copyOf(tmp, pointCount);
        }
    }

    private LineSegment createMaxLineSegment(Point[] points, Point point, int start, int end) {
        Point[] candidates = new Point[end - start + 1];
        // copy the contents to a new array
        int k = 0;
        for (int i = start; i < end; i++) {
            candidates[k++] = points[i];
        }
        candidates[k++] = point;
        Arrays.sort(candidates);
        return new LineSegment(candidates[0], candidates[candidates.length - 1]);
           /*
        Arrays.sort(points, start, end + 1);
        if (point.compareTo(points[start]) < 0) {
            return new LineSegment(point, points[end]);
        } else if (point.compareTo(points[end]) > 0) {
            return new LineSegment(points[start], point);
        } else {
            return new LineSegment(points[start], points[end]);
        }
        */
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
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

