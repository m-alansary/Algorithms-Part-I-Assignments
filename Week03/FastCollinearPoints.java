import java.util.Comparator;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private int size = 0;
    private Point[] end;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int len = points.length, counter = 2;
        lineSegments = new LineSegment[len];
        end = new Point[len];
        Point[] points2 = new Point[len];
        for (int i = 0; i < len; i++) {
            points2[i] = points[i];
            if (points[i] == null)
                throw new IllegalArgumentException();
        }
        double slope1 = 0, slope2 = 0;
        for (int i = 0; i < len; i++) {
            sort(points);
            sort(points, points2[i].slopeOrder());
//            for (int k = 0; k < len; k++) {
//                StdOut.println(points2[i].slopeTo(points[k]));
//            }
//            StdOut.println();
            if (len > 1) {
                slope1 = points2[i].slopeTo(points[1]);
                if (slope1 == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException();
            }
            int j;
            for (j = 2; j < len; j++) {
                slope2 = points2[i].slopeTo(points[j]);
                if (slope1 == slope2)
                    counter++;
                else if (counter >= 4) {
                    if (!(points[j - counter + 1].compareTo(points2[i]) < 0)) {
                        lineSegments[size] = new LineSegment(points2[i], points[j - 1]);
                        end[size] = points[j - 1];
                        size++;
                    }
                    slope1 = slope2;
                    counter = 2;
                } else {
                    slope1 = slope2;
                    counter = 2;
                }
            }
            if (counter >= 4) {
                if (!(points[j - counter + 1].compareTo(points2[i]) < 0)) {
                    lineSegments[size] = new LineSegment(points2[i], points[j - 1]);
                    end[size] = points[j - 1];
                    size++;
                    slope1 = slope2;
                }
            }
            counter = 2;
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return size;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[size];
        for (int i = 0; i < size; i++)
            segments[i] = lineSegments[i];
        return segments;
    }

    private static void sort(Point[] a, Comparator<Point> comparator) {
        int N = a.length;
        for (int i = 0; i < N; i++)
            for (int j = i; j > 0 && less(comparator, a[j], a[j - 1]); j--)
                exch(a, j, j - 1);
    }

    private static void exch(Point[] a, int j, int i) {
        Point temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean less(Comparator<Point> comparator, Point p1, Point p2) {
        return comparator.compare(p1, p2) < 0;
    }

    private static boolean isExist(Point[] points, Point point, int size) {
        for (int i = 0; i < size; i++)
            if (points[i] == point)
                return true;
        return false;
    }

    private static void merge(Point[] a, Point[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    private static boolean less(Comparable<Point> a, Point b) {
        return a.compareTo(b) < 0;
    }

    private static void sort(Point[] a) {
        int N = a.length;
        Point[] aux = new Point[N];
        for (int sz = 1; sz < N; sz = sz + sz)
            for (int lo = 0; lo < N - sz; lo += sz + sz)
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
    }
}
