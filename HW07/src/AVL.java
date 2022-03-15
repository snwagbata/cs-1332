import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Somtochukwu Nwagbata
 * @version 1.0
 * @userid snwagbata3
 * @GTID 903685352
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into " + "the Tree");
        }

        for (T element : data) {
            // add the element to the tree
            add(element);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * AVL and then rotate the tree as needed.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @param data the data to be added
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        root = addHelper(root, data);

    }

    /**
     * Helper method for adding.
     *
     * @param node root of the tree
     * @param data data to be added
     * @return root of the tree with data added
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<T>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(node.getRight(), data));
        } else {
            return node;
        }
        node.setHeight(calcHeight(node.getLeft(), node.getRight()));
        node.setBalanceFactor(calcBalanceFactor(node.getLeft(), node.getRight()));

        return rebalance(node);
    }

    /**
     * Helper method for rebalancing.
     *
     * @param left  left child of the node
     * @param right right child of the node
     * @return root of the tree with data added
     */
    private int calcBalanceFactor(AVLNode<T> left, AVLNode<T> right) {
        if (left == null && right == null) {
            return 0;
        } else if (left == null) {
            return -1 - right.getHeight();
        } else if (right == null) {
            return left.getHeight() + 1;
        } else {
            return left.getHeight() - right.getHeight();
        }
    }

    /**
     * Helper method for calculating node height.
     *
     * @param left  left child of the node
     * @param right right child of the node
     * @return root of the tree with data added
     */
    private int calcHeight(AVLNode<T> left, AVLNode<T> right) {
        if (left == null && right == null) {
            return 0;
        } else if (left == null) {
            return right.getHeight() + 1;
        } else if (right == null) {
            return left.getHeight() + 1;
        } else {
            return Math.max(left.getHeight(), right.getHeight()) + 1;
        }
    }

    /**
     * Rebalances the tree if necessary.
     *
     * @param node root of the tree
     * @return root of the tree after rebalancing
     */
    private AVLNode<T> rebalance(AVLNode<T> node) {
        if (node.getBalanceFactor() == 2) {
            if (node.getLeft().getBalanceFactor() == 1 || node.getLeft().getBalanceFactor() == 0) {
                node = rotateRight(node);
            } else {
                node = leftRight(node);
            }
        } else if (node.getBalanceFactor() == -2) {
            if (node.getRight().getBalanceFactor() == -1 || node.getRight().getBalanceFactor() == 0) {
                node = rotateLeft(node);
            } else {
                node = rightLeft(node);
            }
        }
        return node;
    }

    /**
     * Rotates the tree to the right.
     *
     * @param node root of the tree
     * @return root of the tree after rotation
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        AVLNode<T> temp = node.getLeft();

        node.setLeft(temp.getRight());
        temp.setRight(node);

        node.setHeight(calcHeight(node.getLeft(), node.getRight()));
        node.setBalanceFactor(calcBalanceFactor(node.getLeft(), node.getRight()));

        temp.setHeight(calcHeight(temp.getLeft(), temp.getRight()));
        temp.setBalanceFactor(calcBalanceFactor(temp.getLeft(), temp.getRight()));

        return temp;
    }

    /**
     * Rotates the tree to the left.
     *
     * @param node root of the tree
     * @return root of the tree after rotation
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        AVLNode<T> temp = node.getRight();

        node.setRight(temp.getLeft());
        temp.setLeft(node);

        node.setHeight(calcHeight(node.getLeft(), node.getRight()));
        node.setBalanceFactor(calcBalanceFactor(node.getLeft(), node.getRight()));

        temp.setHeight(calcHeight(temp.getLeft(), temp.getRight()));
        temp.setBalanceFactor(calcBalanceFactor(temp.getLeft(), temp.getRight()));

        return temp;
    }

    /**
     * Performs a left-right rotation.
     *
     * @param node root of the tree
     * @return root of the tree after rotation
     */
    private AVLNode<T> leftRight(AVLNode<T> node) {
        node.setLeft(rotateLeft(node.getLeft()));
        return rotateRight(node);
    }

    /**
     * Performs a right-left rotation.
     *
     * @param node root of the tree
     * @return root of the tree after rotation
     */
    private AVLNode<T> rightLeft(AVLNode<T> node) {
        node.setRight(rotateRight(node.getRight()));
        return rotateLeft(node);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * <p>
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     * <p>
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove invalid data from the Tree");
        }

        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();

    }

    /**
     * Helper method for removing.
     *
     * @param node  root of the tree
     * @param data  data to be removed
     * @param dummy dummy node to store the data
     * @return root of the tree with data removed
     */
    private AVLNode<T> removeHelper(AVLNode<T> node, T data, AVLNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException("Could not remove the data because it is not in the tree.");
        }

        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(node.getLeft(), data, dummy));
            node.setHeight(calcHeight(node.getLeft(), node.getRight()));
            node.setBalanceFactor(calcBalanceFactor(node.getLeft(), node.getRight()));

        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(node.getRight(), data, dummy));
            node.setHeight(calcHeight(node.getLeft(), node.getRight()));
            node.setBalanceFactor(calcBalanceFactor(node.getLeft(), node.getRight()));
        } else {
            dummy.setData(node.getData());
            size--;

            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() != null && node.getRight() == null) {
                return node.getLeft();
            } else if (node.getRight() != null && node.getLeft() == null) {
                return node.getRight();
            } else {
                // handle case where parent has 2 children
                AVLNode<T> dummy2 = new AVLNode<>(null);
                node.setRight(removeSuccessor(node.getRight(), dummy2));
                node.setData(dummy2.getData());

                node.setHeight(calcHeight(node.getLeft(), node.getRight()));
                node.setBalanceFactor(calcBalanceFactor(node.getLeft(), node.getRight()));
            }
        }
        return rebalance(node);
    }

    /**
     * Helper method for removing.
     *
     * @param node      root of the tree
     * @param storeNode dummy node to store the data
     * @return root of the tree with data removed
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> node, AVLNode<T> storeNode) {
        if (node.getLeft() == null) {
            storeNode.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(removeSuccessor(node.getLeft(), storeNode));
            return node;
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get invalid data from the Tree");
        }

        T storedData = getHelper(root, data);
        if (storedData == null) {
            throw new NoSuchElementException("The provided data does not exist in the tree.");
        }
        return storedData;
    }

    /**
     * Helper method for get.
     *
     * @param node root of the tree
     * @param data data to be searched for
     * @return data in the tree equal to the parameter
     */
    private T getHelper(AVLNode<T> node, T data) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.getData()) < 0) {
            return getHelper(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return getHelper(node.getRight(), data);
        } else {
            return node.getData();
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     * @throws IllegalArgumentException if the data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get invalid data from the Tree");
        }

        return containsHelper(root, data);
    }

    /**
     * Helper method for contains.
     *
     * @param node root of the tree
     * @param data data to be searched for
     * @return true if the parameter is contained within the tree, false otherwise
     */
    private boolean containsHelper(AVLNode<T> node, T data) {
        if (node == null) {
            return false;
        }
        if (data.compareTo(node.getData()) < 0) {
            return containsHelper(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return containsHelper(node.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     * <p>
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     * <p>
     * This should NOT be used in the remove method.
     * <p>
     * Ex:
     * Given the following AVL composed of Integers
     * 76
     * /    \
     * 34      90
     * \    /
     * 40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        return predecessorHelper(root, data);
    }

    /**
     * Helper method for predecessor.
     *
     * @param node root of the tree
     * @param data data to be searched for
     * @return predecessor of the parameter
     */
    private T predecessorHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("The data is not in the tree.");
        }
        if (data.compareTo(node.getData()) < 0) {
            return predecessorHelper(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return predecessorHelper(node.getRight(), data);
        } else {
            if (node.getLeft() != null) {
                return findMax(node.getLeft());
            } else {
                AVLNode<T> dummy = new AVLNode<>(null);
                return findPredecessor(root, data, dummy);
            }
        }
    }

    /**
     * Helper method for predecessor without access to parent.
     *
     * @param node root of the tree
     * @param data data to be searched for
     * @param dummy dummy node to be used in the recursive calls
     * @return predecessor of the parameter
     */
    private T findPredecessor(AVLNode<T> node,
                              T data, AVLNode<T> dummy) {
        if (node == null) {
            return null;
        }

        if (node.getData().compareTo(data) < 0) {
            dummy.setData(node.getData());
            return findPredecessor(node.getRight(), data, dummy);
        } else if (node.getData().compareTo(data) > 0) {
            return findPredecessor(node.getLeft(), data, dummy);
        }

        return dummy.getData();
    }

    /**
     * Finds the max of the left subtree
     *
     * @param node root of the tree
     * @return the max of the left subtree
     */
    private T findMax(AVLNode<T> node) {
        if (node.getRight() == null) {
            return node.getData();
        }
        return findMax(node.getRight());
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     * <p>
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     * <p>
     * Ex:
     * Given the following AVL composed of Integers
     * 50
     * /    \
     * 25      75
     * /  \     / \
     * 13   37  70  80
     * /  \    \      \
     * 12  15    40    85
     * /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("k cannot be negative or greater than the size of the tree");
        }

        List<T> list = new ArrayList<>();

        kSmallestHelper(root, k, list);

        return list;
    }

    /**
     * Helper method for kSmallest.
     *
     * @param node root of the tree
     * @param k    number of smallest elements to return
     * @param list list to be returned
     */
    private void kSmallestHelper(AVLNode<T> node, int k, List<T> list) {
        if (node == null) {
            return;
        }

        kSmallestHelper(node.getLeft(), k, list);

        if (list.size() == k) {
            return;
        }

        list.add(node.getData());

        kSmallestHelper(node.getRight(), k, list);
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }


    /**
     * Returns the height of the root of the tree.
     * <p>
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}