import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null, last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;
        first = newFirst;
        if (isEmpty()) {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;
        if (!isEmpty()) {
            last.next = newLast;
        }
        last = newLast;
        if (isEmpty()) {
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = last.item;
        if (size == 1) {
            first = null;
            last = null;
            size--;
            return item;
        }
        Node temp = first;
        while (temp.next != last) {
            temp = temp.next;
        }
        last = temp;
        last.next = null;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
//        Deque<Integer> deque = new Deque<Integer>();
//        deque.addFirst(1);
//        deque.addFirst(2);
//        deque.removeLast();
//        deque.addFirst(4);
//        for (int i : deque) {
//            StdOut.println(i);
//        }
//        deque.removeLast();
        Deque<Integer> integers = new Deque<Integer>();
        integers.addLast(1);        // 1
        integers.addFirst(3);       // 3 1
        integers.addLast(5);        // 3 1 5
        integers.addLast(9);        // 3 1 5 9
        StdOut.println(integers.size);
        StdOut.println(integers.removeFirst());
        StdOut.println(integers.removeLast());
        StdOut.println(integers.size);
        for (int i : integers) {
            StdOut.println(i);
        }
    }
}
