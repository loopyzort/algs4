import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class KdTree {
    private Node rootNode;
    private int size;

    private static class Node {
        private Point2D key;
        private Node left;
        private Node right;
        private RectHV boundingRect;

        Node(Point2D key, RectHV boundingRect) {
            this.key = key;
            this.boundingRect = boundingRect;
        }
    }

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return rootNode == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Cannot insert null value");
        }
        rootNode = put(rootNode, p, true, 0, 0, 1, 1);
    }

    private Node put(Node location, Point2D point, boolean compareX, double xmin,
                     double ymin, double xmax, double ymax) {
        if (location == null) {
            size++;
            return new Node(point, new RectHV(xmin, ymin, xmax, ymax));
        } else if (location.key.equals(point)) {
            return location;
        } else if (compareX) {
            if (point.x() < location.key.x()) {
                location.left = put(location.left, point, false, xmin, ymin,
                        location.key.x(), ymax);
            } else {
                location.right =
                        put(location.right, point, false, location.key.x(), ymin,
                                xmax, ymax);
            }
        } else {
            if (point.y() < location.key.y()) {
                location.left = put(location.left, point, true, xmin, ymin, xmax,
                        location.key.y());
            } else {
                location.right = put(location.right, point, true, xmin, location
                        .key.y(), xmax, ymax);
            }
        }
        return location;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node x = rootNode;
        boolean compareX = true;
        while (x != null) {
            if (p.equals(x.key)) {
                return true;
            } else if (compareX) {
                if (p.x() < x.key.x()) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            } else {
                if (p.y() < x.key.y()) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
            compareX = !compareX;
        }
        return false;
    }

    private Iterable<Point2D> allPoints() {
        Queue<Point2D> points = new Queue<>();
        inorder(rootNode, points);
        return points;
    }

    // draw all points to standard draw
    public void draw() {
        drawNode(rootNode, true);
    }

    private void drawNode(Node node, boolean vertical) {
        if (node == null) {
            return;
        } else {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.circle(node.key.x(), node.key.y(), 0.01);
            if (vertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.key.x(), node.boundingRect.ymin(), node.key.x(),
                        node.boundingRect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.boundingRect.xmin(), node.key.y(),
                        node.boundingRect.xmax(), node.key.y());
            }
            drawNode(node.left, !vertical);
            drawNode(node.right, !vertical);
        }

    }

    private void inorder(Node x, Queue<Point2D> q) {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Set<Point2D> result = new TreeSet<>();
        findRange(result, rootNode, rect, true);
        return result;
    }

    private void findRange(Set<Point2D> points, Node node, RectHV rect, boolean
            vert) {
        if (node == null) {
            return;
        }

        if (vert) {
            RectHV line = new RectHV(node.key.x(), node.boundingRect.ymin(), node.key
                    .x(), node.boundingRect.ymax());
            if (rect.intersects(line)) {
                findRange(points, node.left, rect, !vert);
                findRange(points, node.right, rect, !vert);
            } else if (rect.xmin() <= node.key.x()) {
                findRange(points, node.left, rect, !vert);
            } else if (rect.xmax() >= node.key.x()) {
                findRange(points, node.right, rect, !vert);
            }
        } else {
            RectHV line = new RectHV(node.boundingRect.xmin(), node.key.y(),
                    node.boundingRect.xmax(), node.key.y());
            if (rect.intersects(line)) {
                findRange(points, node.left, rect, !vert);
                findRange(points, node.right, rect, !vert);
            } else if (rect.ymin() <= node.key.y()) {
                findRange(points, node.left, rect, !vert);
            } else if (rect.ymax() >= node.key.y()) {
                findRange(points, node.right, rect, !vert);
            }
        }

        if (rect.contains(node.key)) {
            points.add(node.key);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return findNearest(p, rootNode, true);
    }

    private Point2D findNearest(Point2D searchPoint, Node node, boolean compareX) {
        if (node == null) {
            return null;
        }
        Point2D nearest = node.key;
        Node first;
        Node second;
        if (compareX) {
            if (searchPoint.x() < nearest.x()) {
                first = node.left;
                second = node.right;
            } else {
                first = node.right;
                second = node.left;
            }
        } else {
            if (searchPoint.y() < nearest.y()) {
                first = node.left;
                second = node.right;
            } else {
                first = node.right;
                second = node.left;
            }
        }
        Point2D comp = null;
        if (first != null &&
                first.boundingRect.distanceSquaredTo(searchPoint) < nearest
                        .distanceSquaredTo(searchPoint)) {
            comp = findNearest(searchPoint, first, !compareX);
        }
        if (comp != null &&
                comp.distanceSquaredTo(searchPoint) <
                        nearest.distanceSquaredTo(searchPoint)) {
            nearest = comp;
        }
        if (second != null &&
                second.boundingRect.distanceSquaredTo(searchPoint) < nearest
                        .distanceSquaredTo(searchPoint)) {
            comp = findNearest(searchPoint, second, !compareX);
        }
        if (comp != null &&
                comp.distanceSquaredTo(searchPoint) <
                        nearest.distanceSquaredTo(searchPoint)) {
            nearest = comp;
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree subject = new KdTree();
        assert subject.isEmpty();
        assert subject.size() == 0;
        boolean foundNPE = false;
        try {
            subject.insert(null);
        } catch (NullPointerException ex) {
            foundNPE = true;
        }
        assert foundNPE;

        Point2D point = new Point2D(0.1, 0.1);
        subject.insert(point);
        assert !subject.isEmpty();
        assert subject.size() == 1;
        assert subject.contains(point);

        Point2D point2 = new Point2D(0.9, 0.9);
        subject.insert(point2);
        RectHV rect = new RectHV(0, 0, 0.5, 0.5);
        Iterator<Point2D> it = subject.range(rect).iterator();
        assert it.next().equals(point);
        assert !it.hasNext();

        Point2D nearest = subject.nearest(new Point2D(0.8, 0.8));
        assert nearest.equals(point2);

        subject = new KdTree();
        assert subject.nearest(point) == null;
    }
}

