import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
 *
 * @author Somtochukwu Nwagbata
 * @version 1.0
 * @userid snwagbata3
 * @GTID 9003685352
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     * <p>
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        // TODO test that I can only add elements in order i.e I cannot add
        //  element at index 3 if index 2 has not been initialized
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid Index Provided.");
        }

        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        // resize backingArray if needed
        if (size + 1 > backingArray.length) {
            this.growListCapacity();
        }

        if (index == size) {
            this.addToBack(data);
            return;
        }

        if (index == 0) {
            this.addToFront(data);
            return;
        }

        // Rather than using temporary values and changing every single value
        // in the array, I could try moving the backing Array values 1 index
        // up, starting from the back and then assigning the backingArray's
        // value at index to be equal to data

        for (int i = size - 1; i >= index; i--) {
            backingArray[i + 1] = backingArray[i];
        }
        backingArray[index] = data;
        size++;
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        // resize backingArray if needed
        if (size + 1 > backingArray.length) {
            this.growListCapacity();
        }

        for (int i = size - 1; i >= 0; i--) {
            backingArray[i + 1] = backingArray[i];
        }
        backingArray[0] = data;
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        // resize backingArray if needed
        if (size + 1 > backingArray.length) {
            this.growListCapacity();
        }

        this.backingArray[size] = data;
        size++;
    }

    /**
     * Doubles the capacity of the backingArray
     */
    @SuppressWarnings("unchecked")
    private void growListCapacity() {
        T[] tempBackingList =
                (T[]) new Object[backingArray.length * 2];

        for (int i = 0; i < backingArray.length; i++) {
            tempBackingList[i] = backingArray[i];
        }
        backingArray = tempBackingList;
    }

    /**
     * Removes and returns the element at the specified index.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index Provided.");
        }

        if (index == size - 1) {
            return removeFromBack();
        }

        T removedElement = backingArray[index];

        for (int i = index; i < size; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        size--;

        return removedElement;
    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("The ArrayList is empty.");
        }

        T removedElement = backingArray[0];
        for (int i = 0; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        size--;

        return removedElement;
    }

    /**
     * Removes and returns the last element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("The ArrayList is empty.");
        }

        T removed = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;

        return removed;
    }

    /**
     * Returns the element at the specified index.
     * <p>
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index Provided.");
        }

        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     * <p>
     * Must be O(1).
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
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
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
