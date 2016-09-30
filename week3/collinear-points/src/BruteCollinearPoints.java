import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 */
public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("constructor argument is null");
        }
        // initialize it to be too big, but shrink when done
        LineSegment[] tmp = new LineSegment[points.length];
        int i = 0;
        for (int p = 0; p < points.length - 3; p++) {
            Point one = points[p];
            if (one == null) {
                throw new NullPointerException("Null point at index: " + p);
            }
            for (int q = p + 1; q < points.length - 2; q++) {
                Point two = points[q];
                if (two == null) {
                    throw new NullPointerException("Null point at index: " + q);
                }
                for (int r = q + 1; r < points.length - 1; r++) {
                    Point three = points[r];
                    if (three == null) {
                        throw new NullPointerException("Null point at index: " + r);
                    }
                    for (int s = r + 1; s < points.length; s++) {
                        Point four = points[s];
                        if (four == null) {
                            throw new NullPointerException("Null point at index: " + s);
                        }
                        if (one.slopeTo(two) == one.slopeTo(three) &&
                                one.slopeTo(two) == one.slopeTo(four)) {
                            Point smallest = one.compareTo(two) < 0 ? one : two;
                            smallest = smallest.compareTo(three) < 0 ? smallest : three;
                            smallest = smallest.compareTo(four) < 0 ? smallest : four;
                            Point biggest = one.compareTo(two) > 0 ? one : two;
                            biggest = biggest.compareTo(three) > 0 ? biggest : three;
                            biggest = biggest.compareTo(four) > 0 ? biggest : four;
                            tmp[i] = new LineSegment(smallest, biggest);
                            i++;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
