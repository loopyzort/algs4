import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 */
public class BruteCollinearPoints {
    private LineSegment[] segments;

    private void checkForNull(Point p) {
        if (p == null) {
            throw new NullPointerException("Null point found");
        }
    }
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("constructor argument is null");
        }
        Arrays.sort(points);
        // initialize it to be too big, but shrink when done
        LineSegment[] tmp = new LineSegment[points.length];
        int i = 0;
        for (int p = 0; p < points.length - 3; p++) {
            Point one = points[p];
            checkForNull(one);
            Comparator<Point> comparator = one.slopeOrder();
            for (int q = p + 1; q < points.length - 2; q++) {
                Point two = points[q];
                checkForNull(two);
                if (two.compareTo(one) == 0) {
                    throw new IllegalArgumentException("Repeated point at index: " + p);
                }
                for (int r = q + 1; r < points.length - 1; r++) {
                    Point three = points[r];
                    checkForNull(three);
                    // if two and three have the same slope, then go through 4
                    if (comparator.compare(two, three) == 0) {
                        for (int s = r + 1; s < points.length; s++) {
                            Point four = points[s];
                            checkForNull(four);
                            if (comparator.compare(three, four) == 0) {
                                Point[] vals = { one, two, three, four };
                                Arrays.sort(vals);
                                tmp[i] = new LineSegment(vals[0], vals[3]);
                                i++;
                            }
                        }
                    }
                }
            }
        }
        segments = java.util.Arrays.copyOf(tmp, i);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
