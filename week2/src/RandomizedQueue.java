import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by carvajal on 1/13/17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    // construct an empty randomized queue

    private Node first = null;
    private int size = 0;

    public RandomizedQueue() {

    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(18);
        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(4);
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(7);
        randomizedQueue.enqueue(8);
        for (Integer i : randomizedQueue) {
            StdOut.println(i);
        }
        StdOut.println("===");
        for (Integer i : randomizedQueue) {
            StdOut.println(i);
        }

    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (size > 1) {
            int rand = StdRandom.uniform(0, size);
            Node item = getNodeAt(rand - 1);
            Node res = item.next;
            item.next = res.next;
            size--;
            return res.item;
        } else {

            Node node = first;
            first = null;
            size--;
            return node.item;
        }
    }

    private Node getNodeAt(int pos) {
        Node item = first;
        for (int i = 0; i < pos; i++) {
            item = item.next;
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int rand = StdRandom.uniform(0, size);
        return getNodeAt(rand).item;

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RamdomizedQueueIterator();
    }

    private class Node {
        private Item item;
        private Node next;
    }

    private class RamdomizedQueueIterator implements Iterator<Item> {

        private RandomizedQueue<Item> queue;

        public RamdomizedQueueIterator() {
            queue = new RandomizedQueue<>();
            Node node = first;
            while (node != null) {
                queue.enqueue(node.item);
                node = node.next;
            }

        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Item next() {
            if (queue.isEmpty()) {
                throw new NoSuchElementException();
            }
            return queue.dequeue();
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}