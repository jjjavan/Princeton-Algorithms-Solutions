

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;


public class BruteCollinearPoints
{
    private  LineSegment[] rays;
    private  int n;

    public BruteCollinearPoints(Point[] points)
    {
        // Precondition: N is >= 4 points.

        validate(points);

        int size = points.length, sizeI = size - 3,
                sizeJ = size - 2, sizeK = size - 1;

        Point[] pCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            pCopy[i] = points[i];

        Arrays.sort(pCopy);


        rays = new LineSegment[points.length*2];
        n = 0;
        double slope1, slope2, slope3, slope4;
        for (int i = 0; i < sizeI; i++)
            for (int j = i + 1; j < sizeJ; j++)
                for (int k = j + 1; k < sizeK; k++)
                    for (int l = k + 1; l < size; l++)
                    {
                        slope1 = pCopy[i].slopeTo(pCopy[j]);
                        slope2 = pCopy[i].slopeTo(pCopy[k]);
                        slope3 = pCopy[i].slopeTo(pCopy[k]);
                        slope4 = pCopy[i].slopeTo(pCopy[l]);

                        if ( (Double.compare(slope1,slope2) == Double.compare(slope2,slope3) && Double.compare(slope2,slope3) == Double.compare(slope3,slope4)))
                        {
                            if (n == rays.length) resize(2 * rays.length);
                            rays[n++] = new LineSegment(pCopy[i], pCopy[l]);
                        }
                    }


    }

    private void resize(int size)
    {
        LineSegment[] tmp = new LineSegment[size];
        for (int i = 0; i < n; i++)
            tmp[i] = rays[i];
        rays = tmp;
    }
    private void validate(Point[] points)
    {
        if (points == null)
            throw new IllegalArgumentException("Point array is null.");
        for (Point test: points)
            if (test == null)
                throw new IllegalArgumentException("Null point.");
        for (int i = 0; i < points.length - 1; i++)
            for (int j = i + 1; j < points.length ; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Points are equal.");
    }

    public int numberOfSegments()
    {
        return n;
    }
    public LineSegment[] segments()
    {
        LineSegment[] tmp = new LineSegment[n];
        for(int i = 0; i < n; ++i )
            tmp[i] = rays[i];
        return tmp;
    }

    public static void main(String[] args)
    {

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
        StdDraw.show();// print and draw the line segments

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

