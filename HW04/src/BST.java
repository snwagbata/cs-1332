import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into " + "the Tree");
        }

        for (T element : data) {
            // add the element to the tree
            add(element);
        }

    }

    /**
     * Adds the data to the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into " + "the Tree");
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
    private BSTNode<T> addHelper(BSTNode<T> node, T data) {
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(node.getRight(), data));
        }
        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove invalid data " + "from the Tree");
        }

        BSTNode<T> dummy = new BSTNode<>(null);
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
    private BSTNode<T> removeHelper(BSTNode<T> node, T data, BSTNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException("Could not remove the data because it is not in the tree.");
        }

        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(node.getLeft(), data, dummy));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(node.getRight(), data, dummy));
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
                BSTNode<T> dummy2 = new BSTNode<>(null);
                node.setRight(removeSuccessor(node.getRight(), dummy2));
                node.setData(dummy2.getData());
            }
        }
        return node;
    }

    /**
     * Helper method for removing.
     *
     * @param node      root of the tree
     * @param storeNode dummy node to store the data
     * @return root of the tree with data removed
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> node, BSTNode<T> storeNode) {
        if (node.getLeft() == null) {
            storeNode.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(removeSuccessor(node.getLeft(), storeNode));
            return node;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get invalid data " + "from the Tree");
        }

        T storedData = getHelper(root, data);
        if (storedData == null) {
            throw new NoSuchElementException("The provided data does not exist in" + " the tree.");
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
    private T getHelper(BSTNode<T> node, T data) {
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
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get invalid data " + "from the Tree");
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
    private boolean containsHelper(BSTNode<T> node, T data) {
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
     * Generate a pre-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        return preorderHelper(root);
    }

    /**
     * Helper method for preorder.
     *
     * @param node root of the tree
     * @return the pre-order traversal of the tree
     */
    private List<T> preorderHelper(BSTNode<T> node) {
        List<T> list = new ArrayList<>();
        if (node == null) {
            return list;
        }
        list.add(node.getData());
        list.addAll(preorderHelper(node.getLeft()));
        list.addAll(preorderHelper(node.getRight()));

        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        return inorderHelper(root);
    }

    /**
     * Helper method for inorder.
     *
     * @param node root of the tree
     * @return the in-order traversal of the tree
     */
    private List<T> inorderHelper(BSTNode<T> node) {
        List<T> list = new ArrayList<>();
        if (node == null) {
            return list;
        }
        list.addAll(inorderHelper(node.getLeft()));
        list.add(node.getData());
        list.addAll(inorderHelper(node.getRight()));
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        return postorderHelper(root);
    }

    /**
     * Helper method for postorder.
     *
     * @param node root of the tree
     * @return the post-order traversal of the tree
     */
    private List<T> postorderHelper(BSTNode<T> node) {
        List<T> list = new ArrayList<>();
        if (node == null) {
            return list;
        }
        list.addAll(postorderHelper(node.getLeft()));
        list.addAll(postorderHelper(node.getRight()));
        list.add(node.getData());

        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * <p>
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int level = queue.size();
            for (int i = 0; i < level; i++) {
                BSTNode<T> node = queue.remove();
                list.add(node.getData());
                if (node.getLeft() != null) {
                    queue.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    queue.add(node.getRight());
                }
            }
        }

        return list;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * <p>
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Helper method for height.
     *
     * @param node root of the tree
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        int leftHeight = heightHelper(node.getLeft());
        int rightHeight = heightHelper(node.getRight());

        if (leftHeight > rightHeight) {
            return leftHeight + 1;
        } else {
            return rightHeight + 1;
        }

    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     * <p>s
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     * <p>
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * <p>
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     * <p>
     * Ex:
     * Given the following BST composed of Integers
     * 50
     * /        \
     * 25         75
     * /    \
     * 12    37
     * /  \    \
     * 11   15   40
     * /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Cannot find path for invalid " + "data");
        }

        if (!contains(data1) || !contains(data2)) {
            throw new NoSuchElementException("The data is not in the tree");
        }

        LinkedList<T> list = new LinkedList<>();
        LinkedList<T> leftList = new LinkedList<>();
        LinkedList<T> rightList = new LinkedList<>();


        if (data1.equals(data2)) {
            list.add(data1);
            return list;
        }

        // find the deepest common ancestor
        BSTNode<T> ancestor = findDeepestCommonAncestor(data1, data2, root);

        if (ancestor == null) {
            throw new NoSuchElementException("The data is not in the tree");
        }

        traverseHelper(ancestor, data1, leftList, rightList, ancestor.getData());
        traverseHelper(ancestor, data2, leftList, rightList, ancestor.getData());

System.out.println("deb left: " + leftList);
System.out.println("deb right: " + rightList);
        int indexOfAncestor = leftList.size();
        leftList.addAll(rightList);
        list.addAll(leftList);




        // this is done because leftList and rightList are empty when there is
        // no node in between data1 and data2
        if (!leftList.contains(ancestor.getData())
                && !rightList.contains(ancestor.getData()) && !ancestor.getData().equals(data1) && !ancestor.getData().equals(data2)) {
            System.out.println("deb ancestor: " + indexOfAncestor);
            list.add(indexOfAncestor, ancestor.getData());
        }




            list.addFirst(data1);


            list.addLast(data2);




        return list;
//
//        if (data1.compareTo(data2) < 0) {
//            tempList = traverseHelper(ancestor, data1, leftList, rightList);
//            if (tempList == null) {
//                throw new NoSuchElementException("The data is not in the tree");
//            }
//
//            tempList = traverseTo(ancestor, data2, rightList);
//            if (tempList == null) {
//                throw new NoSuchElementException("The data is not in the tree");
//            }
//        } else {
//            tempList = traverseToBackwards(ancestor, data1, rightList);
//            if (tempList == null) {
//                throw new NoSuchElementException("The data is not in the tree");
//            }
//
//            tempList = traverseToBackwards(ancestor, data2, list);
//            if (tempList == null) {
//                throw new NoSuchElementException("The data is not in the tree");
//            }
//            rightList.addAll(list);
//            rightList.removeFirstOccurrence(ancestor.getData());
//            rightList.addFirst(data1);
//            rightList.addLast(data2);
//            return rightList;
//        }
//
//        list.addAll(rightList);
//        // ancestor is added twice due to the way traverseTo works
//        // remove the duplicate ancestor
//        // since we don't worry about repeated data, we can just remove
//        // last occurrence
//        list.removeLastOccurrence(ancestor.getData());
//        list.addFirst(data1);
//        list.addLast(data2);
//
//        return list;

    }

    /**
     * For forwards traversal
     * Traverses from the given node to the given data and adds the data to
     * the list.
     *
     * @param node     the node to start traversing from
     * @param data     the data to traverse to
     * @param leftList the list to add the data to
     * @return the list of data traversed to
     */
    private LinkedList<T> traverseHelper(BSTNode<T> node, T data, LinkedList<T> leftList, LinkedList<T> rightList, T ancestor) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.getData()) < 0 && data.compareTo(ancestor) < 0) {
            System.out.println("<Node: " + node.getData());
            if (node.getData().compareTo(ancestor) != 0) {
                leftList.addFirst(node.getData());
            }
            return traverseHelper(node.getLeft(), data, leftList, rightList, ancestor);
        } else if (data.compareTo(node.getData()) > 0 && data.compareTo(ancestor) < 0) {
            System.out.println(">Node: " + node.getData());
            if (node.getData().compareTo(ancestor) != 0) {
                leftList.addLast(node.getData());
            }
            return traverseHelper(node.getRight(), data, leftList, rightList, ancestor);
        } else if (data.compareTo(node.getData()) < 0 && data.compareTo(ancestor) > 0) {
            System.out.println("d<Node: " + node.getData());
            if (node.getData().compareTo(ancestor) != 0) {
                rightList.add(node.getData());
            }
            return traverseHelper(node.getLeft(), data, leftList, rightList, ancestor);
        } else if (data.compareTo(node.getData()) > 0 && data.compareTo(ancestor) > 0) {
            System.out.println("d>Node: " + node.getData());
            if (node.getData().compareTo(ancestor) != 0) {
                leftList.add(node.getData());
            }
            return traverseHelper(node.getRight(), data, leftList, rightList, ancestor);
        } else {
            if (node.getData() == null) {
                throw new NoSuchElementException("The data is not in the tree");
            }
            return null;
        }
    }

    /**
     * For forwards traversal
     * Traverses from the given node to the given data and adds the data to
     * the list.
     *
     * @param node the node to start traversing from
     * @param data the data to traverse to
     * @param list the list to add the data to
     * @return the list of data traversed to
     */
    private LinkedList<T> traverseToBackwards(BSTNode<T> node, T data, LinkedList<T> list) {
        if (node == null) {
            return null;
        }

        if (data.compareTo(node.getData()) > 0 && data.compareTo(node.getData()) > 0) {
            list.addLast(node.getData());
            return traverseToBackwards(node.getRight(), data, list);
        } else if (data.compareTo(node.getData()) < 0) {
            list.addFirst(node.getData());
            return traverseToBackwards(node.getLeft(), data, list);
        }
        return list;
    }

    /**
     * Finds the deepest common ancestor of two elements in the tree.
     *
     * @param data1 the first data to find the common ancestor of
     * @param data2 the second data to find the common ancestor of
     * @param node  the node to start searching from
     * @return the deepest common ancestor of the two data
     */
    private BSTNode<T> findDeepestCommonAncestor(T data1, T data2, BSTNode<T> node) {

        if (node == null) {
            return null;
        }
        if (node.getData().equals(data1) || node.getData().equals(data2)) {
            return node;
        }

        if (data1 == null || data2 == null) {
            throw new NoSuchElementException("The data is not in the tree");
        }

        BSTNode<T> left = findDeepestCommonAncestor(data1, data2, node.getLeft());
        BSTNode<T> right = findDeepestCommonAncestor(data1, data2, node.getRight());

        if (left != null && right != null) {
            return node;
        }

        if (left != null) {
            return left;
        } else {
            return right;
        }
    }


    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
