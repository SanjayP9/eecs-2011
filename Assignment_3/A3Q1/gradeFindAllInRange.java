package A3Q1;
import java.util.*;

/**
 * Tests BSTRange
 *
 * @author jameselder
 */
public class gradeFindAllInRange {

    public static void main(String[] args) {
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
