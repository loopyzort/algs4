import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    TreeSet<Point2D> treeSet = new TreeSet<>();

    // construct an empty set of points
    public PointSET() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return treeSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        treeSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return treeSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : treeSet) {
            StdDraw.point(point.x(), point.y());
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Set<Point2D> result = new HashSet<>();
        for (Point2D point : treeSet) {
            if (rect.contains(point)) {
                result.add(point);
            }
        }
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D neighbor = null;
        double neighborDistance = Double.POSITIVE_INFINITY;
        double currentDistance;
        for (Point2D point : treeSet) {
            if (neighbor == null) {
                neighbor = point;
            } else {
                currentDistance = point.distanceTo(p);
                if (currentDistance < neighborDistance) {
                    neighbor = point;
                    neighborDistance = currentDistance;
                }
            }
        }
        return neighbor;
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
