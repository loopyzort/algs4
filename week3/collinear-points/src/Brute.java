import java.util.Arrays;

/**
 * Created by loopyzort on 3/8/15.
 */
public class Brute {
    private void collinear(Point[] values) {
        for (int i = 0; i < values.length - 3; i++) {
            Point p = values[i];
            p.draw();
            for (int j = i + 1; j < values.length - 2; j++) {
                Point q = values[j];
                for (int k = j + 1; k < values.length - 1; k++) {
                    Point r = values[k];
                    for (int l = k + 1; l < values.length; l++) {
                        Point s = values[l];
                        double pq = p.slopeTo(q);
                        if (pq == p.slopeTo(r) && pq == p.slopeTo(s)) {
                            Point[] points = {p, q, r, s};
                            Arrays.sort(points);
                            p.drawTo(s);
                            StdOut.printf("%s -> %s -> %s -> %s", points[0],
                                    points[1], points[2], points[3]);
                            StdOut.println();
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
        }
        Brute brute = new Brute();
        brute.collinear(values);

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }
}
