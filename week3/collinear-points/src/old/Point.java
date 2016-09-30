package old;

import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            return slopeTo(o1) < slopeTo(o2) ? -1 : 1;
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else if (y == that.y) {
            return 0;
        } else {
            return (that.y - y) / (double)(that.x - x);
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that == null) {
            return 1;
        }
        if (y == that.y) {
            if (x == that.x) {
                return 0;
            } else {
                return x > that.x ? 1 : -1;
            }
        } else {
            return y > that.y ? 1 : -1;
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        // test Point equality
        Point one = new Point(0, 0);
        Point two = new Point(0, 0);
        assert one.compareTo(two) == 0;
        two = new Point(0, 1);
        assert one.compareTo(two) < 0;
        assert two.compareTo(one) > 0;
        one = new Point(1, 1);
        two = new Point(2, 1);
        assert one.compareTo(two) < 0;
        assert two.compareTo(one) > 0;

        // test slope values
        one = new Point(0, 0);
        two = new Point(1, 1);
        assert one.slopeTo(two) == 1;
        assert two.slopeTo(one) == 1;

        two = new Point(1, 2);
        assert one.slopeTo(two) == 2;
        two = new Point(2, 1);
        assert one.slopeTo(two) == .5;

        // test slope fringe cases
        two = new Point(1, 0);
        assert one.slopeTo(two) == 0;
        two = new Point(0, 1);
        assert one.slopeTo(two) == Double.POSITIVE_INFINITY;
        assert one.slopeTo(one) == Double.NEGATIVE_INFINITY;

        // test SLOPE_ORDER
        two = new Point(1, 1);
        Point three = new Point(1, 2);
        assert one.SLOPE_ORDER.compare(two, three) < 0;
        assert one.SLOPE_ORDER.compare(three, two) > 0;
        two = new Point(1, 0);
        assert one.SLOPE_ORDER.compare(three, two) > 0;
    }
}