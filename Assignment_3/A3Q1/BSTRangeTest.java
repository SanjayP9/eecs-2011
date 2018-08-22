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
    public void hardTest_grade() {
        BSTRange<Integer, String> medals = new BSTRange<Integer, String>();
        PositionalList<Entry<Integer, String>> medalList;
        Iterator<Entry<Integer, String>> entryIter;

        int[] marks = new int[7];
        int testNum = 0;
        int nCorrect = 0;

        //Test Case 1
        //should output nothing
        int k1 = 2;
        int k2 = 6;
        System.out.print("Test Case " + ++testNum + ": ");
        try {
            medalList = medals.findAllInRange(k1, k2);
            if (medalList.isEmpty()) {
                marks[testNum] = 1;
                System.out.println("Output list is null, since tree is empty.");
                System.out.println("Outcome:  Passed.");
            }
        } catch (Exception x) {
            System.out.println("Outcome:  Failed");
        }
        System.out.println("");

        String[] medalRankings = new String[15];

        medalRankings[1] = "Norway";
        medalRankings[2] = "Germany";
        medalRankings[3] = "Canada";
        medalRankings[4] = "USA";
        medalRankings[5] = "Netherlands";
        medalRankings[6] = "Sweden";
        medalRankings[7] = "South Korea";
        medalRankings[8] = "Switzerland";
        medalRankings[9] = "France";
        medalRankings[10] = "Austria";
        medalRankings[11] = "Japan";
        medalRankings[12] = "Italy";
        medalRankings[13] = "Olympic Athletes from Russia";
        medalRankings[14] = "Czech Republic";

        medals.put(6, medalRankings[6]);
        medals.put(12, medalRankings[12]);
        medals.put(3, medalRankings[3]);
        medals.put(14, medalRankings[14]);
        medals.put(2, medalRankings[2]);
        medals.put(10, medalRankings[10]);
        medals.put(4, medalRankings[4]);
        medals.put(9, medalRankings[9]);
        medals.put(1, medalRankings[1]);
        medals.put(13, medalRankings[13]);
        medals.put(5, medalRankings[5]);
        medals.put(7, medalRankings[7]);
        medals.put(8, medalRankings[8]);
        medals.put(11, medalRankings[11]);

        long startTime = System.nanoTime();  //Start measurement of execution time once we have something in the tree

        //Test Case 2
        //should output Germany, Canada, USA, Netherlands, Sweden
        System.out.print("Test Case " + ++testNum + ": ");
        try {
            medalList = medals.findAllInRange(k1, k2);
            entryIter = medalList.iterator();
            System.out.println("The countries ranked from " + k1 + " to " + k2 + " in medal standings are:");
            int i = k1;
            boolean mismatch = false;
            while (entryIter.hasNext()) {
                String country = entryIter.next().getValue();
                if (!country.equals(medalRankings[i++])) {
                    mismatch = true;
                }
                System.out.println(country);
            }
            if (medalList.size() == k2 - k1 + 1 && !mismatch) {
                marks[testNum] = 1;
                System.out.println("Outcome:  Passed.");
            }
            if (marks[testNum] == 0) {
                System.out.println("Outcome:  Failed.");
            }
        } catch (Exception x) {
            System.out.println("Outcome:  Failed.");
        }
        System.out.println("");

        //Test Case 3
        //should output nothing
        System.out.print("Test Case " + ++testNum + ": ");
        k1 = -10;
        k2 = -5;
        try {
            medalList = medals.findAllInRange(k1, k2);
            if (medalList.isEmpty()) {
                marks[testNum] = 1;
                System.out.println("Output list is null, since tree has no keys in range.");
                System.out.println("Outcome:  Passed.");
            }
        } catch (Exception x) {
            System.out.println("Outcome:  Failed.");
        }
        System.out.println("");

        //Test Case 4
        //should output nothing
        System.out.print("Test Case " + ++testNum + ": ");
        k1 = 5;
        k2 = 4;
        try {
            medalList = medals.findAllInRange(k1, k2);
            if (medalList.isEmpty()) {
                marks[testNum] = 1;
                System.out.println("Output list is null, since key range is null.");
                System.out.println("Outcome:  Passed.");
            }
        } catch (Exception x) {
            System.out.println("Outcome:  Failed.");
        }
        System.out.println("");

        //Test Case 5
        //should output Canada
        System.out.print("Test Case " + ++testNum + ": ");
        k1 = 3;
        k2 = 3;
        try {
            medalList = medals.findAllInRange(k1, k2);
            entryIter = medalList.iterator();
            System.out.println("The countries ranked from " + k1 + " to " + k2 + " in medal standings are:");
            int i = k1;
            boolean mismatch = false;
            while (entryIter.hasNext()) {
                String country = entryIter.next().getValue();
                if (!country.equals(medalRankings[i++])) {
                    mismatch = true;
                }
                System.out.println(country);
            }
            if (medalList.size() == k2 - k1 + 1 && !mismatch) {
                marks[testNum] = 1;
                System.out.println("Outcome:  Passed.");
            }
            else {
                System.out.println("Outcome:  Failed.");
            }
        } catch (Exception x) {
            System.out.println("Outcome:  Failed.");
        }
        System.out.println("");

        //Test Case 6
        //Should output Norway, Germany, Canada, USA, Netherlands, Sweden,
        //South Korea, Switzerland, France, Austria
        System.out.print("Test Case " + ++testNum + ": ");
        k1 = -10;
        k2 = 10;
        try {
            medalList = medals.findAllInRange(k1, k2);
            entryIter = medalList.iterator();
            System.out.println("The countries ranked from " + k1 + " to " + k2 + " in medal standings are:");
            int i = 1;
            boolean mismatch = false;
            while (entryIter.hasNext()) {
                String country = entryIter.next().getValue();
                if (!country.equals(medalRankings[i++])) {
                    mismatch = true;
                }
                System.out.println(country);
            }
            if (medalList.size() == k2 - 1 + 1 && !mismatch) {
                marks[testNum] = 1;
                System.out.println("Outcome:  Passed.");
            }
            else {
                System.out.println("Outcome:  Failed.");
            }
        } catch (Exception x) {
            System.out.println("Outcome:  Failed.");
        }
        System.out.println("");

        long stopTime = System.nanoTime();
        double elapsedTime = (double) (stopTime - startTime) / 1000000; //in msec
        System.out.println("Execution Time (msec): " + elapsedTime);

        System.out.print("Test Case Summary: ");
        for (int i = 1; i < marks.length; i++) {
            System.out.print(marks[i] + " ");
            nCorrect += marks[i];
        }
        System.out.println();
        System.out.println("Test Case Grade: " + (double) nCorrect / (marks.length - 1));

    }
}
