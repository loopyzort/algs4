package old;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int currentIndex = 0;
    private Item[] dataArray;

    public RandomizedQueue() {
        dataArray = (Item[]) new Object[1];
    }

    // queue
    public boolean isEmpty() {
        return currentIndex == 0;
    }

    public int size() {
        return currentIndex;
    }                 // return the number of items on

    private void checkForEmptyOperationAttempt() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot access item in empty Queue");
        }
    }

    private void resizeArray(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < currentIndex; i++) {
            newArray[i] = dataArray[i];
        }
        dataArray = newArray;
    }

    // the queue
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add null item to Queue");
        }
        // check if we need to expand the array
        if (currentIndex == dataArray.length) {
            resizeArray(dataArray.length * 2);
        }
        dataArray[currentIndex++] = item;
    }

    public Item dequeue() {
        checkForEmptyOperationAttempt();
        // start with a non-random queue
        int index = StdRandom.uniform(currentIndex--);
        Item result = dataArray[index];
        dataArray[index] = dataArray[currentIndex];
        dataArray[currentIndex] = null;
        if (currentIndex > 0 && currentIndex == dataArray.length / 4) {
            resizeArray(dataArray.length / 2);
        }
        return result;
    }

    public Item sample() {
        checkForEmptyOperationAttempt();
        int sampleIndex = StdRandom.uniform(currentIndex);
        return dataArray[sampleIndex];
    }

    private class RandomizedIterator implements Iterator<Item> {
        private RandomizedQueue<Item> mData;

        RandomizedIterator() {
            mData = new RandomizedQueue<Item>();
            for (int i = 0; i < currentIndex; i++) {
                mData.enqueue(dataArray[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !mData.isEmpty();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Cannot call 'next' when there are "
                        + "no next items");
            }
            return mData.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }
    }

    // random item
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        assert queue.isEmpty();
        boolean hitException = false;
        try {
            queue.enqueue(null);
        } catch (NullPointerException ex) {
            hitException = true;
        }
        assert hitException;
        hitException = false;
        try {
            queue.dequeue();
        } catch (NoSuchElementException ex) {
            hitException = true;
        }
        assert hitException;
        hitException = false;
        try {
            queue.sample();
        } catch (NoSuchElementException ex) {
            hitException = true;
        }
        assert hitException;
        hitException = false;
        try {
            queue.iterator().next();
        } catch (NoSuchElementException ex) {
            hitException = true;
        }
        assert hitException;
        hitException = false;
        try {
            queue.iterator().remove();
        } catch (UnsupportedOperationException ex) {
            hitException = true;
        }
        assert hitException;
        String testString = "testString";
        queue.enqueue(testString);
        assert !queue.isEmpty();
        assert queue.size() == 1;
        assert queue.iterator().hasNext();
        assert queue.sample().equalsIgnoreCase(testString);
        assert !queue.isEmpty();
        assert queue.dequeue().equalsIgnoreCase(testString);
        assert queue.size() == 0;
        assert queue.isEmpty();
        queue.enqueue("string1");
        queue.enqueue("string2");
        queue.enqueue("string3");
        assert queue.size() == 3;
    }
}
