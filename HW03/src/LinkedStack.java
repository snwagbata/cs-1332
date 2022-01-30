import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedStack. It should NOT be circular.
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
public class LinkedStack<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private LinkedNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the top of the stack.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the top of the stack
     * @throws IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into "
                    + "the Stack");
        }
        LinkedNode<T> newNode = new LinkedNode<>(data);
        newNode.setNext(head);
        head = newNode;
        size++;
    }

    /**
     * Removes and returns the data from the top of the stack.
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

        T data = head.getData();
        head = head.getNext();
        size--;

        return data;
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
        return head.getData();
    }

    /**
     * Returns the head node of the stack.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the stack
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
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
