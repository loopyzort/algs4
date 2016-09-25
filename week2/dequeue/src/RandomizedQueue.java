import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // is the queue empty?
    public boolean isEmpty() {
        return false;
    }

    // return the number of items on the queue
    public int size() {
        return 0;
    }

    // add the item
    public void enqueue(Item item) {
    }

    // remove and return a random item
    public Item dequeue() {
        return null;
    }

    // return (but do not remove) a random item
    public Item sample() {
        return null;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return null;
    }

    // unit testing
    public static void main(String[] args) {
    }

    static void testQueueOperations() {
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
    }

    static void testQueueExceptions() {
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
            foundException  = true;
        }
        assert foundException;

        foundException = false;
        try {
            subject.sample();
        } catch (NoSuchElementException ex) {
            foundException  = true;
        }
        assert foundException;

    }

    static void testIteratorOperations() {

    }

    static void testIteratorExceptions() {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        Iterator<String> subject = queue.iterator();
        boolean foundException = false;
        try {
            subject.remove();
        } catch(UnsupportedOperationException ex) {
            foundException = true;
        }
        assert foundException;
        assert !subject.hasNext();
        foundException = false;
        try {
            subject.next();
        } catch(NoSuchElementException ex) {
            foundException = true;
        }
        assert foundException;
    }
}
