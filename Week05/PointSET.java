import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> tree;

    public PointSET() {
        tree = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    public int size() {
        return tree.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        tree.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return tree.contains(p);
    }

    public void draw() {
        for (Point2D point : tree)
            point.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        for (Point2D point : tree)
            if (rect.contains(point))
                points.add(point);
        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (isEmpty())
            return null;
        Point2D pointMin = tree.first();
        for (Point2D point : tree) {
            if (p.distanceSquaredTo(point) < p.distanceSquaredTo(pointMin))
                pointMin = point;
        }
        return pointMin;
    }

    public static void main(String[] args) {
        // UncommentedEmptyMethodBody
    }
}
