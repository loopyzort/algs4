import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

/******************************************************************************
 * Compilation:  javac Point.java Execution:    java Point Dependencies: none An immutable data type
 * for points in the plane. For use on Coursera, Algorithms Part I programming assignment.
 ******************************************************************************/

public class Point implements Comparable<Point> {
    private static final int FLOAT_MULT_FACTOR = 100000;

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    private static void testSlopeTo() {
        assert new Point(1, 1).slopeTo(new Point(1, 1)) == Double.NEGATIVE_INFINITY;
        assert new Point(3, 1).slopeTo(new Point(3, 4)) == Double.POSITIVE_INFINITY;
        assert new Point(1, 3).slopeTo(new Point(4, 3)) == +0.0;
        assert (int) new Point(0, 0).slopeTo(new Point(1, 1)) == 1;
        assert (int) (new Point(0, 0).slopeTo(new Point(2, 1)) * 10) == 5;
        assert (int) (new Point(2, -1).slopeTo(new Point(0, 0)) * 10) == -5;

    }

    /**
     * Returns the slope between this point and the specified point. Formally, if the two points are
     * (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 - x0). For completeness, the slope
     * is defined to be +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical; and Double.NEGATIVE_INFINITY if
     * (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else if (y == that.y) {
            return +0.0;
        } else {
            return (double) (that.y - y) / (that.x - x);
        }
    }

    private static void testCompareTo() {
        assert new Point(0, 0).compareTo(new Point(0, 0)) == 0;
        assert new Point(0, 1).compareTo(new Point(0, 2)) < 0;
        assert new Point(0, 2).compareTo(new Point(0, 1)) > 0;
        assert new Point(1, 1).compareTo(new Point(2, 1)) < 0;
        assert new Point(2, 1).compareTo(new Point(1, 1)) > 0;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
     * point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if
     * y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point (x0 = x1 and y0 =
     * y1); a negative integer if this point is less than the argument point; and a positive integer
     * if this point is greater than the argument point
     */
    public int compareTo(Point that) {
        if (y > that.y) {
            return 1;
        } else if (y < that.y) {
            return -1;
        } else if (x < that.x) {
            return -1;
        } else if (x > that.x) {
            return 1;
        }
        return 0;
    }

    private static void testSlopeOrder() {
        Comparator<Point> subject = new Point(1, 1).slopeOrder();
        assert subject.compare(new Point(2, 2), new Point(2, 2)) == 0;
        assert subject.compare(new Point(6, 2), new Point(2, 2)) < 0;
        assert subject.compare(new Point(2, 2), new Point(6, 2)) > 0;
        // test the NEGATIVE_INFINITY (same point) case
        assert subject.compare(new Point(1, 1), new Point(2, 2)) < 0;
        assert subject.compare(new Point(2, 1), new Point(1, 1)) > 0;
        // test the 0 slope case
        assert subject.compare(new Point(2, 1), new Point(2, 2)) < 0;
        assert subject.compare(new Point(2, 2), new Point(2, 1)) > 0;
        // test 2 points that are on the same horizontal line as the reference point
        assert subject.compare(new Point(4, 1), new Point(5, 1)) == 0;
        // test the POSITIVE_INFINITY (vertical line) case
        assert subject.compare(new Point(1, 2), new Point(2, 2)) > 0;
        assert subject.compare(new Point(2, 2), new Point(1, 2)) < 0;
    }

    /**
     * Compares two points by the slope they make with this point. The slope is defined as in the
     * slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                double first = slopeTo(o1);
                double second = slopeTo(o2);
                // let's remove any floating point issues here
                if ((int) (first * FLOAT_MULT_FACTOR) == (int) (second * FLOAT_MULT_FACTOR)) {
                    return 0;
                } else if (first > second) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
    }

    /**
     * Returns a string representation of this point. This method is provide for debugging; your
     * program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        testCompareTo();
        testSlopeTo();
        testSlopeOrder();
        /* YOUR CODE HERE */
    }
}
