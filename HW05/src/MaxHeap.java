import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
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
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    @SuppressWarnings("unchecked")
    public MaxHeap() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];

    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     * <p>
     * Consider how to most efficiently determine if the list contains null data.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    @SuppressWarnings("unchecked")
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The ArrayList cannot be null");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Data inside the "
                        + "ArrayList cannot be null");
            }
            backingArray[i + 1] = data.get(i);
        }

        size = data.size();
        for (int i = size / 2; i > 0; i--) {
            downHeap(i);
        }

    }

    /**
     * Downheaps the backing array to maintain the heap property.
     * @param i the index of the node to downheap
     */
    private void downHeap(int i) {
        int left = 2 * i;
        int right = 2 * i + 1;
        int max = i;
        if (left <= size && backingArray[left].compareTo(backingArray[max]) > 0) {
            max = left;
        }
        if (right <= size && backingArray[right].compareTo(backingArray[max]) > 0) {
            max = right;
        }
        if (max != i) {
            swap(i, max);
            downHeap(max);
        }
    }

    /**
     * Adds the data to the heap.
     * <p>
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to add cannot be null");
        }
        if (size == backingArray.length - 1) {
            resize();
        }
        backingArray[++size] = data;


        //Up-heap starting at the last index to fix order property
        int index = size;
        while (index > 1 && backingArray[index].compareTo(backingArray[index / 2]) > 0) {
            swap(index, index / 2);
            index = index / 2;
        }
    }

    /**
     * Resizes the backing array to double the current length.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        T[] temp = (T[]) new Comparable[2 * backingArray.length];
        for (int i = 0; i < backingArray.length; i++) {
            temp[i] = backingArray[i];
        }
        backingArray = temp;
    }

    /**
     * Swaps the data at the two indices.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    private void swap(int index1, int index2) {
        T temp = backingArray[index1];
        backingArray[index1] = backingArray[index2];
        backingArray[index2] = temp;
    }

    /**
     * Removes and returns the root of the heap.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (backingArray == null || size == 0) {
            throw new NoSuchElementException("The heap is empty");
        }
        T removedElement = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downHeap(1);

        return removedElement;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0 || backingArray == null) {
            throw new NoSuchElementException("The heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;

    }

    /**
     * Clears the heap.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
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
     * Returns the size of the heap.
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
