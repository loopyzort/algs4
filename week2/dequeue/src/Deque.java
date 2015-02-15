import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node firstNode;
    private Node lastNode;
    private int size;

    private class Node {
        private Item mValue;
        private Node mPrevious;
        private Node mNext;

        Node(Item value) {
            this.mValue = value;
        }

        public Item getValue() {
            return mValue;
        }

        public void setValue(Item value) {
            this.mValue = value;
        }

        public Node getPrevious() {
            return mPrevious;
        }

        public void setPrevious(Node previous) {
            this.mPrevious = previous;
        }

        public Node getNext() {
            return mNext;
        }

        public void setNext(Node next) {
            this.mNext = next;
        }
    }

    // construct an empty deque
    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }                // return the number of items on the
    // deque
    private void checkForNullItem(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add null item to Deque");
        }
    }

    public void addFirst(Item item) {
        checkForNullItem(item);
        Node node = new Node(item);
        if (firstNode != null) {
            firstNode.setPrevious(node);
            node.setNext(firstNode);
        }
        firstNode = node;
        if (lastNode == null) {
            lastNode = firstNode;
        }
        size++;
    }

    public void addLast(Item item) {
        checkForNullItem(item);
        Node node = new Node(item);
        if (lastNode != null) {
            lastNode.setNext(node);
            node.setPrevious(lastNode);
        }
        lastNode = node;
        if (firstNode == null) {
            firstNode = lastNode;
        }
        size++;
    }        // add the item to the end

    public Item removeFirst() {
        if (firstNode == null) {
            throw new NoSuchElementException("Cannot remove item from empty Deque");
        }
        Node resultNode = firstNode;
        firstNode = resultNode.getNext();
        if (firstNode == null) {
            lastNode = null;
        }
        size--;
        return resultNode.getValue();
    }

    public Item removeLast() {
        if (lastNode == null) {
            throw new NoSuchElementException("Cannot remove item from empty Deque");
        }
        Node resultNode = lastNode;
        lastNode = resultNode.getPrevious();
        size--;
        if (lastNode == null) {
            firstNode = null;
        }
        return resultNode.getValue();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node currentNode;

        DequeIterator() {
            currentNode = new Node(null);
            currentNode.setNext(firstNode);
        }

        @Override
        public boolean hasNext() {
            return currentNode.getNext() != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Cannot call 'next' when the are "
                        + "no next items");
            }
            Node valueNode = currentNode;
            currentNode = valueNode.getNext();
            return valueNode.getValue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        assert deque.isEmpty();
        boolean hitException = false;
        try {
            deque.addFirst(null);
        } catch (NullPointerException ex) {
            hitException = true;
        }
        assert hitException;
        hitException = false;
        try {
            deque.addLast(null);
        } catch (NullPointerException ex) {
            hitException = true;
        }
        assert hitException;
        hitException = false;
        try {
            deque.removeFirst();
        } catch (NoSuchElementException ex) {
            hitException = true;
        }
        assert hitException;
        hitException = false;
        try {
            deque.removeLast();
        } catch (NoSuchElementException ex) {
            hitException = true;
        }
        assert hitException;
        hitException = false;
        try {
            deque.iterator().next();
        } catch (NoSuchElementException ex) {
            hitException = true;
        }
        assert hitException;
        hitException = false;
        try {
            deque.iterator().remove();
        } catch (UnsupportedOperationException ex) {
            hitException = true;
        }
        assert hitException;
        String testString = "testString";
        deque.addFirst(testString);
        assert !deque.isEmpty();
        assert deque.size() == 1;
        assert deque.iterator().hasNext();
        assert deque.removeFirst().equalsIgnoreCase(testString);
        deque.addLast(testString);
        assert !deque.isEmpty();
        assert deque.size() == 1;
        assert deque.iterator().hasNext();
        assert deque.removeLast().equalsIgnoreCase(testString);
        deque.addFirst("last");
        deque.addFirst("first");
        assert deque.size() == 2;
        assert deque.removeLast().equalsIgnoreCase("last");
        assert deque.removeLast().equalsIgnoreCase("first");
    }

}