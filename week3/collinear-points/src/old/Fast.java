package old;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class Fast {
    private static void collinear(Point[] values) {
        for (int i = 0; i < values.length - 4; i++) {
            Point p = values[i];
            p.draw();
            int j = i + 1;
            Arrays.sort(values, j, values.length, p.SLOPE_ORDER);
            int pointsWithSameSlope = 0;
            double currentSlope = 0;
            for (; j < values.length; j++) {
                Point q = values[j];
                double pqSlope = p.slopeTo(q);
                if (pqSlope != currentSlope) {
                    currentSlope = pqSlope;
                    if (pointsWithSameSlope > 3) {
                        p.drawTo(values[j-1]);
                    }
                    pointsWithSameSlope = 0;
                } else {
                    pointsWithSameSlope++;
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
        collinear(values);

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();

    }
}
