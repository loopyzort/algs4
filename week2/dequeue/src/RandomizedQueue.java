import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] data;
    private int endOfQueueIndex = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        data = (Item[]) new Object[2];
    }

    private void resizeArray(int newCapacity) {
        Item[] original = data;
        data = (Item[]) new Object[newCapacity];
        int smaller = data.length > original.length ? original.length : data.length;
        for (int i = 0; i < smaller; i++) {
            data[i] = original[i];
        }
    }

    // is the queue empty?
    public boolean isEmpty() {
        return endOfQueueIndex == 0;
    }

    // return the number of items on the queue
    public int size() {
        return endOfQueueIndex;
    }

    // add the item
    public void enqueue(Item item) {
        checkArgument(item);
        if (endOfQueueIndex == data.length - 1) {
            resizeArray(data.length * 2);
        }
        data[endOfQueueIndex++] = item;
    }

    private void swap(int index1, int index2) {
        Item tmp = data[index1];
        data[index1] = data[index2];
        data[index2] = tmp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (endOfQueueIndex == 0) {
            throw new NoSuchElementException();
        }
        // swap anything in the array with the last value
        swap(StdRandom.uniform(0, endOfQueueIndex), --endOfQueueIndex);
        Item result = data[endOfQueueIndex];
        data[endOfQueueIndex] = null;
        if (data.length > 0 && endOfQueueIndex < (data.length / 4)) {
            resizeArray(data.length / 2);
        }
        return result;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (endOfQueueIndex == 0) {
            throw new NoSuchElementException();
        }
        return data[StdRandom.uniform(0, endOfQueueIndex)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private void checkArgument(Item arg) {
        if (arg == null) {
            throw new NullPointerException();
        }
    }

    private class RandomIterator implements Iterator<Item> {
        private final int[] order;
        private int currentIndex = 0;

        public RandomIterator() {
            order = new int[endOfQueueIndex];
            for (int i = 0; i < order.length; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }

        @Override
        public boolean hasNext() {
            return currentIndex < order.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return data[order[currentIndex++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
        testQueueOperations();
        testQueueExceptions();
        testIteratorOperations();
        testIteratorExceptions();
    }

    private static void testQueueOperations() {
        RandomizedQueue<String> subject = new RandomizedQueue<>();
        assert subject != null;
        // isEmpty
        assert subject.isEmpty();
        assert subject.size() == 0;
        // add item
        subject.enqueue("one");
        assert !subject.isEmpty();
        assert subject.size() == 1;
        // addLast
        subject.enqueue("two");
        assert !subject.isEmpty();
        assert subject.size() == 2;

        // dequeue an item
        String item = subject.dequeue();
        assert item != null;
        assert !subject.isEmpty();
        assert subject.size() == 1;

        // dequeue other item
        String anotherItem = subject.dequeue();
        assert !item.equals(anotherItem);
        assert subject.isEmpty();
        assert subject.size() == 0;

        subject.enqueue("solo");
        assert subject.sample().equals("solo");
        assert !subject.isEmpty();
        assert subject.size() == 1;
    }

    private static void testQueueExceptions() {
        RandomizedQueue<String> subject = new RandomizedQueue<>();
        boolean foundException = false;
        try {
            subject.enqueue(null);
        } catch (NullPointerException ex) {
            foundException = true;
        }
        assert foundException;

        foundException = false;
        try {
            subject.dequeue();
        } catch (NoSuchElementException ex) {
            foundException = true;
        }
        assert foundException;

        foundException = false;
        try {
            subject.sample();
        } catch (NoSuchElementException ex) {
            foundException = true;
        }
        assert foundException;

        subject.enqueue("tmp");
        subject.dequeue();
        foundException = false;
        try {
            subject.dequeue();
        } catch (NoSuchElementException ex) {
            foundException = true;
        }
        assert foundException;
    }

    private static void testIteratorOperations() {
        RandomizedQueue<String> subject = new RandomizedQueue<>();
        subject.enqueue("one");
        subject.enqueue("two");
        StringBuilder builder = new StringBuilder();
        for (String string : subject) {
            builder.append(string);
        }
        String comp = builder.toString();
        assert "onetwo".equals(comp) || "twoone".equals(comp);
    }

    private static void testIteratorExceptions() {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        Iterator<String> subject = queue.iterator();
        boolean foundException = false;
        try {
            subject.remove();
        } catch (UnsupportedOperationException ex) {
            foundException = true;
        }
        assert foundException;
        assert !subject.hasNext();
        foundException = false;
        try {
            subject.next();
        } catch (NoSuchElementException ex) {
            foundException = true;
        }
        assert foundException;
    }
}
