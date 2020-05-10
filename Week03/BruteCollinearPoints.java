public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    private int size = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int len = points.length;
        lineSegments = new LineSegment[len];
        sort(points);

        for (int i = 0; i < len; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < len; j++) {
                if (points[j] == null || points[j] == points[i])
                    throw new IllegalArgumentException();
                for (int k = j + 1; k < len; k++) {
                    if (points[k] == null || points[k] == points[j] || points[k] == points[i])
                        throw new IllegalArgumentException();
                    for (int l = k + 1; l < len; l++) {
                        if (points[l] == null || points[l] == points[k] || points[l] == points[j] || points[l] == points[i])
                            throw new IllegalArgumentException();
                        double slope1 = points[i].slopeTo(points[j]);
                        double slope2 = points[i].slopeTo(points[k]);
                        double slope3 = points[i].slopeTo(points[l]);
                        if (slope1 == slope2 && slope1 == slope3) {
                            lineSegments[size] = new LineSegment(points[i], points[l]);
                            size++;
                        }
                    }
                }
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return size;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[size];
        for (int i = 0; i < size; i++) {
            segments[i] = lineSegments[i];
        }
        return segments;
    }

    private static void merge(Point[] a, Point[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            if (a[k] == null)
                throw new IllegalArgumentException();
            aux[k] = a[k];
        }
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
