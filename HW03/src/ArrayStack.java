import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayStack.
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
public class ArrayStack<T> {

    /*
     * The initial capacity of the ArrayStack.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the data to the top of the stack.
     * <p>
     * If sufficient space is not available in the backing array, resize it to
     * double the current length.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the top of the stack
     * @throws IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into "
                    + "the Stack");
        }

        if (size == backingArray.length) {
            resize();
        }

        backingArray[size] = data;
        size++;
    }

    /**
     * Resizes the Stack's backing array.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newArray = (T[]) new Object[backingArray.length * 2];
        for (int i = 0; i < backingArray.length; i++) {
            newArray[i] = backingArray[i];
        }
        backingArray = newArray;
    }

    /**
     * Removes and returns the data from the top of the stack.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * Replace any spots that you pop from with null.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("The Stack is"
                    + " empty. Cannot perform a pop.");
        }
        T removed = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return removed;
    }

    /**
     * Returns the data from the top of the stack without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("The Stack is"
                    + " empty. Cannot perform a peek.");
        }

        return backingArray[size - 1];
    }

    /**
     * Returns the backing array of the stack.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the stack
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the stack.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
