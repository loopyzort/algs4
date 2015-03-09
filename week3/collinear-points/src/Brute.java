import java.util.Arrays;

/**
 * Created by loopyzort on 3/8/15.
 */
public class Brute {
    private void collinear(Point[] values) {
        for (int i = 0; i < values.length; i++) {
            StdOut.print(values[i]);
            StdOut.println();
            Point p = values[i];
            for (int j = 1; j < values.length; j++) {
                Point q = values[j];
                for (int k = 2; k < values.length; k++) {
                    Point r = values[k];
                    for (int l = 3; l < values.length; l++) {
                        Point s = values[l];
                        double pq = p.slopeTo(q);
                        if (pq == p.slopeTo(r) && pq == p.slopeTo(s) &&
                                s.compareTo(r) > 0 && r.compareTo(q) > 0 && q
                                .compareTo(p) > 0) {
                            Point[] points = {p, q, r, s};
                            Arrays.sort(points);
                            p.draw();
                            q.draw();
                            r.draw();
                            s.draw();
                            p.drawTo(s);
                            //StdOut.printf("%s -> %s -> %s -> %s", points[0],
                                    //points[1], points[2], points[3]);
                            //StdOut.println();
                        }

                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] values = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            values[i] = new Point(x, y);
            //p.draw();
        }
        Brute brute = new Brute();
        brute.collinear(values);

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }
}
