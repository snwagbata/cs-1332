import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of a ExternalChainingHashMap.
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
public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public ExternalChainingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ExternalChainingHashMap.
     * <p>
     * The backing array should have an initial capacity of capacity.
     * <p>
     * You may assume capacity will always be positive.
     *
     * @param capacity the initial capacity of the backing array
     */
    @SuppressWarnings("unchecked")
    public ExternalChainingHashMap(int capacity) {
        table = new ExternalChainingMapEntry[capacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * <p>
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     * <p>
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     * <p>
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. Resize if the load factor is greater than max LF (it is okay
     * if the load factor is equal to max LF). For example, let's say the
     * array is of length 5 and the current size is 3 (LF = 0.6). For this
     * example, assume that no elements are removed in between steps. If
     * another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {

        if (key == null || value == null) {
            throw new IllegalArgumentException("The provided key or value "
                    + "cannot be null.");
        }
        if (((size + 1) / (double) table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }

        int index = getHash(key);

        if (table[index] == null) {
            // best case scenario, no collision
            table[index] = new ExternalChainingMapEntry<>(key, value);
            size++;
            return null;
        } else if (table[index].getKey().equals(key)) {
            // duplicate key, replace value
            V oldValue = table[index].getValue();
            table[index].setValue(value);
            return oldValue;
        } else {
            // Two cases: 1) duplicate key, 2) key not in table, so add it
            ExternalChainingMapEntry<K, V> current = table[index];
            while (current.getNext() != null) {
                if (current.getKey().equals(key)) {
                    // Found the key
                    V oldValue = current.getValue();
                    current.setValue(value);
                    return oldValue;
                }
                current = current.getNext();
            }
            // Reached the end of the chain, so add the new entry to the front
            ExternalChainingMapEntry<K, V> newEntry = new ExternalChainingMapEntry<>(key, value);
            newEntry.setNext(table[index]);
            table[index] = newEntry;
            size++;
            return null;
        }
    }

    /**
     * Gets the hash code for the given key.
     *
     * @param key the key to get the hash code for
     * @return the hash code for the given key
     */
    private int getHash(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The provided key cannot be null.");
        }

        int index = getHash(key);
        if (table[index] == null) {
            throw new NoSuchElementException("The provided key is not in the map.");
        } else if (table[index].getKey().equals(key)) {
            // Found the key
            V oldValue = table[index].getValue();
            table[index] = table[index].getNext();
            size--;
            return oldValue;
        } else {
            // Search the chain for the key
            ExternalChainingMapEntry<K, V> current = table[index];
            while (current.getNext() != null) {
                if (current.getNext().getKey().equals(key)) {
                    // Found the key
                    V oldValue = current.getNext().getValue();
                    current.setNext(current.getNext().getNext());
                    size--;
                    return oldValue;
                }
                current = current.getNext();
            }
            throw new NoSuchElementException("The provided key is not in the map.");
        }

    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The provided key cannot be null.");
        }

        int index = getHash(key);
        if (table[index] == null) {
            throw new NoSuchElementException("The provided key is not in the map.");
        }
        if (table[index].getKey().equals(key)) {
            return table[index].getValue();
        }
        ExternalChainingMapEntry<K, V> current = table[index];
        while (current.getNext() != null) {
            if (current.getNext().getKey().equals(key)) {
                return current.getNext().getValue();
            }
            current = current.getNext();
        }
        throw new NoSuchElementException("The provided key is not in the map.");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The provided key cannot be null.");
        }

        int index = getHash(key);
        if (table[index] == null) {
            return false;
        }
        if (table[index].getKey().equals(key)) {
            return true;
        }
        ExternalChainingMapEntry<K, V> current = table[index];
        while (current.getNext() != null) {
            if (current.getNext().getKey().equals(key)) {
                return true;
            }
            current = current.getNext();
        }
        return false;

    }

    /**
     * Returns a Set view of the keys contained in this map.
     * <p>
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (ExternalChainingMapEntry<K, V> entry : table) {
            if (entry != null) {
                keys.add(entry.getKey());
                ExternalChainingMapEntry<K, V> current = entry;
                while (current.getNext() != null) {
                    keys.add(current.getNext().getKey());
                    current = current.getNext();
                }
            }
        }
        return keys;
    }

    /**
     * Returns a List view of the values contained in this map.
     * <p>
     * Use java.util.ArrayList or java.util.LinkedList.
     * <p>
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        LinkedList<V> list = new LinkedList<>();
        for (ExternalChainingMapEntry<K, V> entry : table) {
            if (entry != null) {
                ExternalChainingMapEntry<K, V> current = entry;
                while (current != null) {
                    list.add(current.getValue());
                    current = current.getNext();
                }
            }
        }
        return list;
    }

    /**
     * Resize the backing table to length.
     * <p>
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     * <p>
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     * <p>
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     * <p>
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    @SuppressWarnings("unchecked")
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("The length cannot be less than the number of items in the hash map.");
        }
        ExternalChainingMapEntry<K, V>[] oldTable = table;
        table = new ExternalChainingMapEntry[length];
        for (ExternalChainingMapEntry<K, V> entry : oldTable) {
            while (entry != null) {
                int index = getHash(entry.getKey());
                table[index] = entry;
                entry = entry.getNext();
            }
        }
    }

    /**
     * Clears the map.
     * <p>
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        table = new ExternalChainingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
