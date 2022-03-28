import java.util.Comparator;
import java.util.Queue;
import java.util.Random;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement selection sort.
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator cannot be null");
        }

        for (int i = arr.length - 1; i > 1; i--) {
            int swapIndex = i;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], arr[swapIndex]) > 0) {
                    swapIndex = j;
                }
            }
            swap(arr, i, swapIndex);
        }
    }

    /**
     * Swap two elements in an array.
     *
     * @param arr    the array to swap elements in
     * @param index1 the index of the first element to swap
     * @param index2 the index of the second element to swap
     * @param <T>    the type of the elements in the array
     */
    private static <T> void swap(T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator cannot be null");
        }

        int start = 0;
        int end = arr.length - 1;
        int swapped;

        while (end > start) {
            swapped = start;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    swapped = i;
                }
            }
            end = swapped;
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    swap(arr, i, i - 1);
                    swapped = i;
                }
            }
            start = swapped;
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    @SuppressWarnings("unchecked")
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator cannot be null");
        }

        if (arr.length <= 1) {
            return;
        }


        int length = arr.length;
        int mid = length / 2;
        T[] left = (T[]) new Object[mid];
        T[] right = (T[]) new Object[length - mid];

        for (int i = 0; i < mid; i++) {
            left[i] = arr[i];
        }

        for (int i = mid; i < length; i++) {
            right[i - mid] = arr[i];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        int leftIndex = 0;
        int rightIndex = 0;
        int index = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            if (comparator.compare(left[leftIndex], right[rightIndex]) <= 0) {
                arr[index] = left[leftIndex];
                leftIndex++;
            } else {
                arr[index] = right[rightIndex];
                rightIndex++;
            }
            index++;
        }

        while (leftIndex < left.length) {
            arr[index] = left[leftIndex];
            index++;
            leftIndex++;
        }

        while (rightIndex < right.length) {
            arr[index] = right[rightIndex];
            index++;
            rightIndex++;
        }
    }


    /**
     * Implement quick sort.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Array or comparator or rand cannot be null");
        }

        quickSortHelper(arr, 0, arr.length, comparator, rand);

    }

    /**
     * Helper method for quick sort.
     *
     * @param <T>        datatype to sort
     * @param arr        the array that must be sorted after the method runs
     * @param start      the start index of the array
     * @param end        the end index of the array
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     */
    private static <T> void quickSortHelper(T[] arr, int start, int end, Comparator<T> comparator, Random rand) {
        if (end - start <= 1) {
            return;
        }

        int pivotIndex = rand.nextInt(end - start) + start;
        T pivot = arr[pivotIndex];
        swap(arr, start, pivotIndex);


        int left = start + 1;
        int right = end - 1;

        while (left <= right) {

            while (left <= right && comparator.compare(arr[left], pivot) <= 0) {
                left++;
            }

            while (left <= right && comparator.compare(arr[right], pivot) >= 0) {
                right--;
            }

            if (left <= right) {
                swap(arr, left, right);
                left++;
                right--;
            }
        }

        swap(arr, start, right);
        quickSortHelper(arr, start, right, comparator, rand);
        quickSortHelper(arr, right + 1, end, comparator, rand);

    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    @SuppressWarnings("unchecked")
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        int max = 0;
        int digit = 0;
        for (int k : arr) {
            if (Math.abs(k) > max) {
                max = Math.abs(k);
            }
        }
        while (max > 0) {
            digit++;
            max /= 10;
        }

        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }

        int base = 1;

        for (int i = 0; i < digit; i++) {
            for (int k : arr) {
                int curDigit = (Math.abs(k) / base) % 10;
                if (k < 0) {
                    curDigit = -curDigit;
                }
                buckets[curDigit + 9].add(k);
            }

            int index = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[index++] = bucket.removeFirst();
                }
            }
            base *= 10;
        }
    }

    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list in sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        Queue<Integer> heap = new PriorityQueue<>(data);
        int[] sorted = new int[data.size()];

        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = heap.remove();
        }

        return sorted;
    }
}
