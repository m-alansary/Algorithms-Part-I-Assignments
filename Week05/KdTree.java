import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    private Node root = null;
    private int size = 0;
    private ArrayList<Point2D> points;

    private class Node {
        private Point2D point = null;
        private Node left = null;
        private Node right = null;

        public Node(Point2D point) {
            this.point = point;
        }
    }

    public KdTree() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        root = insert(p, root, VERTICAL);
    }


    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        Node node = root;
        boolean position = VERTICAL;
        while (node != null) {
            if (p.equals(node.point))
                return true;
            if ((position == VERTICAL && p.x() < node.point.x()) || (position == HORIZONTAL && p.y() < node.point.y()))
                node = node.left;
            else
                node = node.right;
            position = !position;
        }
        return false;
    }

    public void draw() {
        draw(root, VERTICAL, 0.0, 1.0, 0.0, 1.0);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        points = new ArrayList<Point2D>();
        range(rect, root, VERTICAL);
        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return nearest(p, root, root.point, VERTICAL);
    }

    private Node insert(Point2D p, Node node, boolean position) {
        if (node == null) {
            size++;
            return new Node(p);
        }
        if (p.equals(node.point))
            return node;
        if ((position == VERTICAL && p.x() < node.point.x()) || (position == HORIZONTAL && p.y() < node.point.y())) {
            node.left = insert(p, node.left, !position);
            return node;
        }
        node.right = insert(p, node.right, !position);
        return node;
    }


    private void draw(Node node, boolean position, double left, double right, double bottom, double top) {
        if (node == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        node.point.draw();
        if (position == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), bottom, node.point.x(), top);
            draw(node.left, HORIZONTAL, left, node.point.x(), bottom, top);
            draw(node.right, HORIZONTAL, node.point.x(), right, bottom, top);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(left, node.point.y(), right, node.point.y());
            draw(node.left, VERTICAL, left, right, bottom, node.point.y());
            draw(node.right, VERTICAL, left, right, node.point.y(), top);
        }
    }

    private void range(RectHV rect, Node node, boolean position) {
        if (node == null)
            return;
        if (rect.contains(node.point)) {
            points.add(node.point);
            range(rect, node.left, !position);
            range(rect, node.right, !position);
            return;
        }
        double max, min;
        if (position == VERTICAL) {
            double x = node.point.x();
            max = Math.max(rect.xmax(), rect.xmin());
            min = Math.min(rect.xmax(), rect.xmin());
            if (x <= max && x >= min) {
                range(rect, node.left, !position);
                range(rect, node.right, !position);
            } else if (x > min)
                range(rect, node.left, !position);
            else
                range(rect, node.right, !position);
        } else {
            double y = node.point.y();
            max = Math.max(rect.ymax(), rect.ymin());
            min = Math.min(rect.ymax(), rect.ymin());
            if (y <= max && y >= min) {
                range(rect, node.left, !position);
                range(rect, node.right, !position);
            } else if (y > min)
                range(rect, node.left, !position);
            else
                range(rect, node.right, !position);
        }
    }

    private Point2D nearest(Point2D p, Node node, Point2D minPoint, boolean position) {
        if (node == null)
            return minPoint;
        if (p.distanceSquaredTo(node.point) < p.distanceSquaredTo(minPoint))
            minPoint = node.point;
        Point2D left = nearest(p, node.left, minPoint, !position);
        Point2D right = nearest(p, node.right, minPoint, !position);
        return (p.distanceSquaredTo(left) < p.distanceSquaredTo(right)) ? left : right;
//        if (p.distanceSquaredTo(node.point) < p.distanceSquaredTo(minPoint))
//            minPoint = node.point;
//        Point2D newMin;
//        if ((position == VERTICAL && p.x() == node.point.x()) || (position == HORIZONTAL && p.y() == node.point.y())) {
//            Point2D left = nearest(p, node.left, minPoint, !position);
//            Point2D right = nearest(p, node.right, minPoint, !position);
//            return (p.distanceSquaredTo(left) < p.distanceSquaredTo(right)) ? left : right;
//        } else if ((position == VERTICAL && p.x() < node.point.x()) || (position == HORIZONTAL && p.y() < node.point.y())) {
//            newMin = nearest(p, node.left, minPoint, !position);
//            if (newMin.equals(minPoint))
//                return nearest(p, node.right, minPoint, !position);
//            return newMin;
//        }
//        newMin = nearest(p, node.right, minPoint, !position);
//        if (newMin.equals(minPoint))
//            return nearest(p, node.left, minPoint, !position);
//        return newMin;
    }

    public static void main(String[] args) {
        // UncommentedEmptyMethodBody
//        int n = StdIn.readInt();
//        double x, y;
//        KdTree kdTree = new KdTree();
//        kdTree.draw();
//        StdDraw.show();
//        for (int i = 0; i < n; i++) {
//            x = StdIn.readDouble();
//            y = StdIn.readDouble();
//            kdTree.insert(new Point2D(x, y));
//        }
//        StdOut.println(kdTree.nearest(new Point2D(0.655, 0.626)).toString());
    }
}
