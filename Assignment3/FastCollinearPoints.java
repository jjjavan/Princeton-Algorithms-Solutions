
import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Arrays;



public class FastCollinearPoints
{
    private final LineSegment[] ray;
    private final int n;

    public FastCollinearPoints(Point[] points)
    {

        validate(points);
        ArrayList<Point> perm = new ArrayList<>();
        Point[] ptsCopy;
        ArrayList<Double> slopes = new ArrayList<>();
        // Shallow copy is fine since Point only contains immutable, primitive types.
        ptsCopy = points.clone();
        ArrayList<LineSegment> segs = new ArrayList<>();
        Arrays.sort(ptsCopy);
        for (int i = 0; i < ptsCopy.length-1; ++i)
            if (ptsCopy[i].slopeTo(ptsCopy[i+1]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException("Degenerate points found.");
        // How it works:
        // 1. Sort copy of Points[](line 22)
        // 2. Select origin in outer for loop. 3. Make a copy of points {origin+1,end} 4. Sort points {origin+1, end}(line 40)
        // 5. Examine sorted list comparing slope of origin to remaining sorted slopes in inner for loops(line 50-73)
        // 6. if 4 or more collinear points are found instantiate a segment and search remaining points in inner for loop for more collinear points.
        // 7. else if no collinear point found in step 6 increment j(line 50) and see if any collinear points.
        // 8. Guarantees that every iteration will find collinear points(if they exist) between p[i] and [p[i+1]...p[n]] with time
        // proportional to n^2log(n)

        for (int i = 0; i < ptsCopy.length - 1; i++) {
            Point origin = ptsCopy[i];
            StdOut.println("Iteration: " + i);
           ArrayList<Point> pts = new ArrayList<>();
            for (int j = i+1; j < ptsCopy.length; ++j)
               pts.add(ptsCopy[j]);
            Point[] ptsArr = pts.toArray(new Point[pts.size()]);
            MergeX.sort(ptsArr, origin.slopeOrder());
            for (int j = 0; j < ptsArr.length - 1;  ++j) {
                double s1 = origin.slopeTo(ptsArr[j]);
               Point endPoint = null;
                int counter = 1;
                StdOut.println(s1);
                for (int k = j+1; k < ptsArr.length; k++) {
                    double s2 = origin.slopeTo(ptsArr[k]);
                    StdOut.println(s2);
                    if (Double.compare(s1,s2) == 0) {
                        perm.add(ptsArr[j]);
                        perm.add(ptsArr[k]);
                        ++counter;
                        if (counter >= 3) {
                            endPoint = ptsArr[k];
                            perm.add(endPoint);
                            slopes.add(s1);
                            j=k;
                        }
                        else{slopes.clear();perm.clear(); }
                    }
                    else
                        break;

                }// for
                if (endPoint != null) {

                    segs.add(new LineSegment(origin,endPoint));
                }
                StdOut.println("========================================================");
            }// for

        }//for


        n = segs.size();
        ray = segs.toArray(new LineSegment[n]);
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

    public int numberOfSegments() { return n; }

    public LineSegment[] segments()
    {
        LineSegment[] tmp = new LineSegment[n];

        for (int i = 0; i < n ; i++)
            tmp[i] = ray[i];

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        in.close();
    }
}

/*StdOut.println(origin);
            for (Point tmp:ptsArr) {
                StdOut.println(tmp);
            }
            StdOut.println("End of iteration: i = " + i);
           for (int j = 0; j < ptsArr.length; j++) {
                Double s1 = origin.slopeTo(ptsArr[j]);
                StdOut.println(s1);
            }
            StdOut.println("End of iteration: i = " + i);*/