import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;

public class PointSET {
    // construct an empty set of points
    public PointSET() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return false;
    }

    // number of points in the set
    public int size() {
        return 0;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return false;
    }

    // draw all points to standard draw
    public void draw() {
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET subject = new PointSET();
        assert subject.isEmpty();
        assert subject.size() == 0;
        boolean foundNPE = false;
        try {
            subject.insert(null);
        } catch (NullPointerException ex) {
            foundNPE = true;
        }
        assert foundNPE;

        Point2D point = new Point2D(.1, .1);
        subject.insert(point);
        assert !subject.isEmpty();
        assert subject.size() == 1;
        assert subject.contains(point);

        Point2D point2 = new Point2D(.9, .9);
        subject.insert(point2);
        RectHV rect = new RectHV(0, 0, .5, .5);
        Iterator<Point2D> it = subject.range(rect).iterator();
        assert it.next().equals(point);
        assert !it.hasNext();

        Point2D nearest = subject.nearest(new Point2D(.8, .8));
        assert nearest.equals(point2);

        subject = new PointSET();
        assert subject.nearest(point) == null;
    }

}
