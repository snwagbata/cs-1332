import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PuckhaberTests {
    private ArrayList<String> list;

    public static final int TIMEOUT = 200;

    @Before
    public void setup() {
        list = new ArrayList<>();
    }

    private void assertArrayListEquals(String method, Object[] expected, ArrayList<String> actual) {
        if (expected.length == 0) {
            assertTrue(actual.isEmpty());
        } else {
            assertFalse(actual.isEmpty());
        }
        Object[] actualArray = actual.getBackingArray();
        assertEquals("Did " + method + " update size?", expected.length, actual.size());
        assertEquals(getExpectedLength(expected.length), actualArray.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actualArray[i]);
        }
        for (int i = expected.length; i < actualArray.length; i++) {
            assertNull(actualArray[i]);
        }
    }

    private int getExpectedLength(int i) {
        int expectedLength = ArrayList.INITIAL_CAPACITY;
        if (i > 0) {
            expectedLength = ArrayList.INITIAL_CAPACITY *
                    (int) Math.ceil(Integer.valueOf(i).doubleValue() /
                            ArrayList.INITIAL_CAPACITY);
        }
        return expectedLength;
    }

    @Test(timeout = TIMEOUT)
    public void test_11_constructor() {
        assertEquals("A new empty ArrayList should have a backing array of size " +
                        ArrayList.INITIAL_CAPACITY + ".",
                ArrayList.INITIAL_CAPACITY, list.getBackingArray().length);
        assertEquals("A new empty ArrayList should be size 0.", 0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_12_addToFront() {
        for (int i = 1; i <= ArrayList.INITIAL_CAPACITY + 1; i++) {
            String[] expected = new String[i];
            for (int j = i; j > 0; j--) {
                expected[i - j] = "" + j;
            }
            list.addToFront(i + "");
            assertArrayListEquals("addToFront", expected, list);
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_13_addToBack() {
        for (int i = 1; i <= ArrayList.INITIAL_CAPACITY + 1; i++) {
            String[] expected = new String[i];
            for (int j = i; j > 0; j--) {
                expected[j - 1] = "" + j;
            }
            list.addToBack(i + "");
            assertArrayListEquals("addToBack", expected, list);
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_14_addAtIndex() {
        // test addAtIndex(0, data) for empty list
        list.addAtIndex(0, "foo");
        assertArrayListEquals("addAtIndex", new String[]{"foo"}, list);
        list = new ArrayList<>(); // reset
        // test addAtIndex(0, data) for partial list
        list.addToFront("2");
        list.addAtIndex(0, "1");
        assertArrayListEquals("addAtIndex", new String[]{"1", "2"}, list);
        // test addAtIndex(size, data) for partial list
        list.addAtIndex(2, "4");
        assertArrayListEquals("addAtIndex", new String[]{"1", "2", "4"}, list);
        // test addAtIndex(size / 2, data) for partial list
        list.addAtIndex(2, "3");
        assertArrayListEquals("addAtIndex", new String[]{"1", "2", "3", "4"}, list);
        list = new ArrayList<>(); // reset
        // test addAtIndex(0, data) for full list
        for (int i = 1; i <= ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack("" + i);
        }
        list.addAtIndex(0, "" + 0);
        assertArrayListEquals("addAtIndex", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}, list);
        list = new ArrayList<>(); // reset
        // test addAtIndex(size, data) for full list
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack("" + i);
        }
        list.addAtIndex(10, "" + 10);
        System.out.println(Arrays.toString(list.getBackingArray()));
        assertArrayListEquals("addAtIndex", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}, list);
        list = new ArrayList<>(); // reset
        // test addAtIndex(size / 2, data) for full list
        for (int i = 0; i <= ArrayList.INITIAL_CAPACITY; i++) {
            if (i != 6) {
                list.addToBack("" + i);
            }
        }
        list.addAtIndex(6, "" + 6);
        assertArrayListEquals("addAtIndex", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}, list);
    }

    @Test(timeout = TIMEOUT)
    public void test_15_removeFromFront() {
        assertNull(list.removeFromFront());
        list.addToFront("0");
        assertEquals("0", list.removeFromFront());
        assertArrayListEquals("removeFromFront", new String[]{}, list);
        list.addToFront("0");
        list.addToFront("foo");
        assertEquals("foo", list.removeFromFront());
        assertArrayListEquals("removeFromFront", new String[]{"0"}, list);
    }

    @Test(timeout = TIMEOUT)
    public void test_16_removeFromBack() {
        assertNull(list.removeFromBack());
        list.addToFront("0");
        assertEquals("0", list.removeFromBack());
        assertArrayListEquals("removeFromBack", new String[]{}, list);
        list.addToFront("foo");
        list.addToFront("0");
        assertEquals("foo", list.removeFromBack());
        assertArrayListEquals("removeFromBack", new String[]{"0"}, list);
    }

    @Test(timeout = TIMEOUT)
    public void test_17_removeAtIndex() {
        // test removeAtIndex(0, data) for partial list
        list.addToBack("0");
        list.addToBack("1");
        assertEquals("0", list.removeAtIndex(0));
        assertArrayListEquals("removeAtIndex", new String[]{"1"}, list);
        // test removeAtIndex(0 == size, data) for partial list
        assertEquals("1", list.removeAtIndex(0));
        assertArrayListEquals("removeAtIndex", new String[]{}, list);
        // test removeAtIndex(size, data) for partial list
        list.addToBack("0");
        list.addToBack("1");
        assertEquals("1", list.removeAtIndex(1));
        assertArrayListEquals("removeAtIndex", new String[]{"0"}, list);
        // test removeAtIndex(size / 2, data) for partial list
        list.addToBack("foo");
        list.addToBack("1");
        assertEquals("foo", list.removeAtIndex(1));
        assertArrayListEquals("removeAtIndex", new String[]{"0", "1"}, list);
    }

    @Test(timeout = TIMEOUT)
    public void test_18_get() {
        list.addToBack("0");
        assertEquals("0", list.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void test_19_clear() {
        list.addToBack("0");
        list.clear();
        assertArrayListEquals("clear", new String[]{}, list);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_00_addAtIndex_exception() {
        list.addAtIndex(-1, "");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_01_addAtIndex_exception() {
        list.addAtIndex(1, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_02_addAtIndex_exception() {
        list.addAtIndex(0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_03_addToFront_exception() {
        list.addToFront(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_04_addToBack_exception() {
        list.addToBack(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_05_removeAtIndex_exception() {
        list.removeAtIndex(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_06_removeAtIndex_exception() {
        list.removeAtIndex(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_07_removeAtIndex_exception() {
        list.removeAtIndex(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_08_get_exception() {
        list.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_09_get_exception() {
        list.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_10_get_exception() {
        list.get(1);
    }
}
