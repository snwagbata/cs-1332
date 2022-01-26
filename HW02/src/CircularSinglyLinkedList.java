import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Somtochukwu Nwagbata
 * @version 1.0
 * @userid snwagbata3
 * @GTID 903685352
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into " + "the CircularSinglyLinkedList");
        }

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + size);
        }

        if (index == 0) {
            addToFront(data);
            return;
        }

        if (index == size) {
            addToBack(data);
            return;
        }

        CircularSinglyLinkedListNode<T> currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.getNext();
        }
        CircularSinglyLinkedListNode<T> nodeToAdd =
                new CircularSinglyLinkedListNode<>(data, currentNode.getNext());
        currentNode.setNext(nodeToAdd);
        size++;
    }

    /**
     * Adds the data to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into " + "the CircularSinglyLinkedList");
        }

        if (size == 0) {
            head = new CircularSinglyLinkedListNode<>(data, head);
        }
        CircularSinglyLinkedListNode<T> tempNode = new CircularSinglyLinkedListNode<>(head.getData());
        tempNode.setNext(head.getNext());
        head.setData(data);
        head.setNext(tempNode);
        size++;

        if (size == 1) {
            head.setNext(head);
        }
    }

    /**
     * Adds the data to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into " + "the CircularSinglyLinkedList");
        }

        if (size == 0) {
            head = new CircularSinglyLinkedListNode<>(data, head);
        }
        CircularSinglyLinkedListNode<T> tempNode = new CircularSinglyLinkedListNode<>(head.getData());
        tempNode.setNext(head.getNext());
        head.setData(data);
        head.setNext(tempNode);
        head = tempNode;
        size++;

        if (size == 1) {
            head.setNext(head);
        }
    }

    /**
     * Removes and returns the data at the specified index.
     * <p>
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index Provided. The " + "index should be between 0 and "
                    + (size - 1));
        }

        if (index == 0) {
            return removeFromFront();
        }

        CircularSinglyLinkedListNode<T> currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.getNext();
        }

        CircularSinglyLinkedListNode<T> removedNode = currentNode.getNext();
        currentNode.setNext(currentNode.getNext().getNext());
        size--;
        return removedNode.getData();
    }

    /**
     * Removes and returns the first data of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("The CircularSinglyLinkedList is"
                    + " empty. Cannot remove from front.");
        }
        T removedElement = head.getData();

        if (size > 1) {
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
        } else {
            head = null;
        }


        size--;
        return removedElement;
    }

    /**
     * Removes and returns the last data of the list.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The CircularSinglyLinkedList is"
                    + " empty. Cannot remove from back.");
        }

        CircularSinglyLinkedListNode<T> currentNode = head;

        for (int i = 0; i < size - 2; i++) {
            currentNode = currentNode.getNext();
        }
        T removedElement = currentNode.getNext().getData();
        currentNode.setNext(head);
        size--;

        return removedElement;
    }

    /**
     * Returns the data at the specified index.
     * <p>
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index Provided. The " + "index should be between 0 and "
                    + size);
        }

        if (index == 0) {
            return head.getData();
        }

        CircularSinglyLinkedListNode<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }

        return currentNode.getData();
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null && size == 0;
    }

    /**
     * Clears the list.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;

    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     * <p>
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data from the CircularSinglyLinkedList");
        }

        if (head == null) {
            throw new NoSuchElementException("Provided data is not found in the CircularSinglyLinkedList");
        }

        CircularSinglyLinkedListNode<T> lastOccurrence = null;
        CircularSinglyLinkedListNode<T> previousNode = null;
        CircularSinglyLinkedListNode<T> currentNode = head;

        // loop below doesn't work for head
        for (int i = 0; i < size - 1; i++) {
            if (currentNode.getNext().getData().equals(data)) {
                lastOccurrence = currentNode.getNext();
                previousNode = currentNode;
            }
            currentNode = currentNode.getNext();
        }

        if (head.getData().equals(data) && lastOccurrence == null) {
            T removedElement = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
            return removedElement;
        }

        if (lastOccurrence == null) {
            throw new NoSuchElementException("Provided data is not found in the CircularSinglyLinkedList");
        }

        previousNode.setNext(lastOccurrence.getNext());
        size--;

        return lastOccurrence.getData();
    }

    /**
     * Returns an array representation of the linked list.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] circularLinkedListArray = (T[]) new Object[size];

        CircularSinglyLinkedListNode<T> currentNode = head;
        int index = 0;

        while (index < size) {
            circularLinkedListArray[index] = currentNode.getData();
            currentNode = currentNode.getNext();
            index++;
        }

        return circularLinkedListArray;
    }

    /**
     * Returns the head node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
