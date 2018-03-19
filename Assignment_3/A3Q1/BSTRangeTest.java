package A3Q1;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BSTRangeTest {

    @Test
    public void hardTest() {
        BSTRange<Integer, Integer> tree = new BSTRange<>();
        PositionalList<Entry<Integer, Integer>> list;
        Iterator<Entry<Integer, Integer>> iter;

        //              38
        //      25              51
        //  17      31      42      63
        // 4  21   28 35   40 49   55 71

        tree.put(38, 38);
        tree.put(25, 25);
        tree.put(51, 51);
        tree.put(17, 17);
        tree.put(31, 31);
        tree.put(4, 4);
        tree.put(21, 21);
        tree.put(42, 42);
        tree.put(63, 63);
        tree.put(28, 28);
        tree.put(35, 35);
        tree.put(40, 40);
        tree.put(49, 49);
        tree.put(55, 55);
        tree.put(71, 71);

        // k1 = 20, k2 = 52, LCA = 38
        // k1 = 41, k2 = 75, LCA = 51
        int k1 = 20, k2 = 52;
        list = tree.findAllInRange(k1, k2);

        int[] firstResult = {21, 25, 28, 31, 35, 38, 40, 42, 49, 51};


        iter = list.iterator();


        /*System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        while(iter.hasNext())
        {
            System.out.println(iter.next().getValue());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");*/

        if (list.size() != firstResult.length) {
            fail("Expected " + firstResult.length + " entries, got: " + list.size());
        }



        for (int entry : firstResult) {
            Entry<Integer, Integer> e = iter.next();

            if (e.getKey() != entry) {
                fail("Expected " + entry + ", got: " + e.getKey());
            }
        }


    }

    @Test
    public void hardTest2() {
        BSTRange<Integer, Integer> tree = new BSTRange<>();
        PositionalList<Entry<Integer, Integer>> list;
        Iterator<Entry<Integer, Integer>> iter;

        //              38
        //      25              51
        //  17      31      42      63
        // 4  21   28 35   40 49   55 71

        tree.put(38, 38);
        tree.put(25, 25);
        tree.put(51, 51);
        tree.put(17, 17);
        tree.put(31, 31);
        tree.put(4, 4);
        tree.put(21, 21);
        tree.put(42, 42);
        tree.put(63, 63);
        tree.put(28, 28);
        tree.put(35, 35);
        tree.put(40, 40);
        tree.put(49, 49);
        tree.put(55, 55);
        tree.put(71, 71);

        // k1 = 20, k2 = 52, LCA = 38
        // k1 = 41, k2 = 75, LCA = 51

        int k1 = 41, k2 = 75;
        list = tree.findAllInRange(k1, k2);
        iter = list.iterator();
        int[] secondResult = {42, 49, 51, 55, 63, 71};
        if (list.size() != secondResult.length) {
            fail("Expected " + secondResult.length + " entries, got: " + list.size());
        }

        /*System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        while(iter.hasNext())
        {
            System.out.println(iter.next().getValue());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");*/

        for (int entry : secondResult) {
            Entry<Integer, Integer> e = iter.next();

            if (e.getKey() != entry) {
                fail("Expected " + entry + ", got: " + e.getKey());
            }
        }
    }

    private BSTRange<Integer, String> generate() {
        BSTRange<Integer, String> medals = new BSTRange<>();

        medals.put(1, "Norway");
        medals.put(2, "Germany");
        medals.put(3, "Canada");
        medals.put(4, "USA");
        medals.put(5, "Netherlands");
        medals.put(6, "Sweden");
        medals.put(7, "South Korea");
        medals.put(8, "Switzerland");
        medals.put(9, "France");
        medals.put(10, "Austria");

        return medals;
    }

    private PositionalList<Entry<Integer, String>> getMedalList(BSTRange<Integer, String> medals, int k1, int k2) {
        return medals.findAllInRange(k1, k2);
    }

    private boolean assertRange(int[] expected, PositionalList<Entry<Integer, String>> list) {
        Iterator<Entry<Integer, String>> iter = list.iterator();

        if (list.size() != expected.length) {
            fail("Expected " + expected.length + " entries, got: " + list.size());
        }

        for (int entry : expected) {
            Entry<Integer, String> e = iter.next();

            if (e.getKey() != entry) {
                fail("Expected " + entry + ", got: " + e.getKey());
            }
        }

        return true;
    }

    @Test
    public void hardTest3() {
        BSTRange<Integer, String> medals = new BSTRange<>();
        PositionalList<Entry<Integer, String>> medalList = medals.findAllInRange(0, 10);

        assertEquals(0, medalList.size());
    }

    @Test
    public void hardTest4() {
        // Canada, USA, Netherlands, Sweden, South Korea
        BSTRange<Integer, String> medals = generate();
        PositionalList<Entry<Integer, String>> medalList = getMedalList(medals, 3, 7);
        int[] expected = { 3, 4, 5, 6, 7};

        assertTrue(assertRange(expected, medalList));
    }

    @Test
    public void hardTest5() {
        // Canada
        BSTRange<Integer, String> medals = generate();
        PositionalList<Entry<Integer, String>> medalList = getMedalList(medals, 3, 3);
        int[] expected = { 3 };

        assertTrue(assertRange(expected, medalList));
    }

    @Test
    public void hardTest6() {
        BSTRange<Integer, String> medals = generate();
        PositionalList<Entry<Integer, String>> medalList = medals.findAllInRange(5, 4);

        assertEquals(0, medalList.size());
    }

    @Test
    public void hardTest7() {
        BSTRange<Integer, String> medals = generate();
        PositionalList<Entry<Integer, String>> medalList = medals.findAllInRange(-10, -5);

        assertEquals(0, medalList.size());
    }

    @Test
    public void hardTest8() {
        // Norway, Germany, Canada, USA, Netherlands, Sweden, South Korea, Switzerland, France, Austria
        BSTRange<Integer, String> medals = generate();
        PositionalList<Entry<Integer, String>> medalList = medals.findAllInRange(-10, 10);
        int[] expected = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

        assertTrue(assertRange(expected, medalList));
    }

    @Test
    public void hardTest9() {
        BSTRange<Integer, String> medals = generate();
        PositionalList<Entry<Integer, String>> medalList = medals.findAllInRange(21, 30);

        assertEquals(0, medalList.size());
    }

    @Test
    public void hardTest10() {
        BSTRange<Integer, String> medals = generate();
        PositionalList<Entry<Integer, String>> medalList = medals.findAllInRange(0,0);

        assertEquals(0, medalList.size());
    }

    @Test
    public void hardTest11() {
        BSTRange<Integer, String> medals = generate();
        PositionalList<Entry<Integer, String>> medalList = medals.findAllInRange(11,11);

        assertEquals(0, medalList.size());
    }
}
