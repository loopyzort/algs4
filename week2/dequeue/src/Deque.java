import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Basic implementation of a Deque class.
 */
public class Deque<Item> implements Iterable<Item> {
    private int size = 0;
    private Node<Item> first;
    private Node<Item> last;
    private static class Node<Item> {
        private final Item value;
        private Node<Item> next;
        private Node<Item> prev;
        public Node(Item val) {
            value = val;
        }
    }
    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    private void checkArgument(Item arg) {
        if (arg == null) {
            throw new NullPointerException();
        }
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkArgument(item);
        Node<Item> oldFirst = first;
        first = new Node<>(item);
        first.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.prev = first;
        }
        if (last == null) {
            last = first;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        checkArgument(item);
        Node<Item> node = new Node<>(item);
        if (first == null) {
            first = node;
            first.next = node;
            last = node;
        }
        last.next = node;
        node.prev = last;
        last = node;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        Item result = first.value;
        if (first == last) {
            last = null;
            first = null;
        } else {
            first = first.next;
            if (first != null) {
                first.prev = null;
            }
        }
        size--;
        return result;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (last == null || last == first) {
            return removeFirst();
        }
        Item result = last.value;
        last = last.prev;
        size--;
        return result;
    }

    private class DequeIterator implements Iterator<Item> {
        Node<Item> currentNode = first;
        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item result = currentNode.value;
            currentNode = currentNode.next;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing
    public static void main(String[] args) {
        testDequeOperations();
        testDequeExceptions();
        testIteratorOperations();
        testIteratorExceptions();
    }

    static void testDequeOperations() {
        Deque<String> subject = new Deque<>();
        assert subject != null;
        // isEmpty
        assert subject.isEmpty();
        assert subject.size() == 0;
        // addFirst
        subject.addFirst("first");
        assert !subject.isEmpty();
        assert subject.size() == 1;
        // addLast
        subject.addLast("last");
        assert !subject.isEmpty();
        assert subject.size() == 2;
        // removeFirst
        String removedValue = subject.removeFirst();
        assert removedValue.equals("first");
        assert !subject.isEmpty();
        assert subject.size() == 1;
        // removeLast
        removedValue = subject.removeLast();
        assert removedValue.equals("last");
        assert subject.isEmpty();
    }

    static void testIteratorOperations() {
        Deque<String> subject = new Deque<>();
        subject.addFirst("dealer");
        subject.addFirst("acid");
        subject.addFirst("every");
        subject.addLast("gets");
        subject.addLast("busted");
        subject.addLast("eventually");
        StringBuilder builder = new StringBuilder();
        for (String string : subject) {
            builder.append(string);
        }
        assert builder.toString().equals("everyaciddealergetsbustedeventually");
    }

    static void testDequeExceptions() {
        Deque<String> subject = new Deque<>();
        // throws exception adding null
        boolean foundException = false;
        try {
            subject.addFirst(null);
        } catch (NullPointerException ex) {
            foundException = true;
        }
        assert foundException;

        foundException = false;
        try {
            subject.addLast(null);
        } catch (NullPointerException ex) {
            foundException = true;
        }
        assert foundException;

        subject = new Deque<>();
        subject.addFirst("tmp");
        subject.removeFirst();
        foundException = false;
        try {
            subject.removeFirst();
        } catch(NoSuchElementException ex) {
            foundException = true;
        }
        assert foundException;

        subject.addLast("tmp");
        subject.removeLast();
        foundException = false;
        try {
            subject.removeLast();
        } catch(NoSuchElementException ex) {
            foundException = true;
        }
        assert foundException;
    }

    static void testIteratorExceptions() {
        Deque<String> deque = new Deque<>();
        Iterator<String> subject = deque.iterator();
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
