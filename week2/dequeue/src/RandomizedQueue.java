import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N = 0;
    private Item[] array;

    public RandomizedQueue() {
        array = (Item[]) new Object[1];
    }

    // queue
    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }                 // return the number of items on

    private void checkForEmptyOperationAttempt() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot access item in empty Queue");
        }
    }

    private void resizeArray(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < N; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    // the queue
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add null item to Queue");
        }
        // check if we need to expand the array
        if (N == array.length) {
            resizeArray(array.length * 2);
        }
        array[N++] = item;
    }

    public Item dequeue() {
        checkForEmptyOperationAttempt();
        // start with a non-random queue
        Item result = array[--N];
        array[N] = null;
        if (N > 0 && N == array.length / 4) {
            resizeArray(array.length / 2);
        }
        return result;
    }          // remove and return a random item

    public Item sample() {
        checkForEmptyOperationAttempt();
        int sampleIndex = StdRandom.uniform(N);
        return array[sampleIndex];
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int N = -1;
        private Item[] values;

        RandomizedIterator(int size) {
            values = (Item[]) new Object[size];
            for (int i = 0; i < values.length; i++) {
                values[i] = array[i];
            }
            N = values.length - 1;
        }

        @Override
        public boolean hasNext() {
            return N >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Cannot call 'next' when the are "
                        + "no next items");
            }
            return values[N--];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }
    }

    // random item
    public Iterator<Item> iterator() {
        return new RandomizedIterator(N);
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
