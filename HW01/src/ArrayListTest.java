import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayListTest {

    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addAtIndex() {
    }

    @Test
    public void addToFront() {
    }

    @Test
    public void addToBack() {
    }

    @Test
    public void removeAtIndex() {
        list.addToFront("a");
        list.addToFront("b");
        list.addToFront("c");
        list.addToFront("d");

        assertEquals("d", list.removeAtIndex(0));
    }

    @Test
    public void removeFromFront() {
    }

    @Test
    public void removeFromBack() {
    }

    @Test
    public void get() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void getBackingArray() {
    }

    @Test
    public void size() {
    }
}