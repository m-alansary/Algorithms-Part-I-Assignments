public class ArrayQueue<Item> {
    private Item[] s;
    private int first = 0, last = 1;

    public ArrayQueue() {
        s = (Item[]) new String[2];
    }

    public boolean isEmpty() {
        return (first + 1) % s.length == last;
    }

    public void enqueue(Item item) {
        if (last == first) {
            resize(2 * s.length);
        }
        s[last] = item;
        last = (last + 1) % s.length;
    }

    public Item dequeue() {
        Item item = s[first];
        s[first] = null;
        first = (first + 1) % s.length;
        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new String[capacity];
        int N = s.length;
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
        last = N;
    }

}
