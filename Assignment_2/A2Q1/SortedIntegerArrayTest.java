package A2Q1;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SortedIntegerArrayTest {

    public int[] generateArray(int size, int lowerBound, int upperBound) {
        int[] array = new int[size];

        for (int i = 0; i < array.length; i++) {
            array[i] = ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
        }

        return array;
    }

    public int[] getPair(int[] array, Integer k) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (i != j) {
                    if ((long) array[i] + (long) array[j] == k) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    @Test
    public void boundsTest1() {
        int[] empty = new int[0];
        SortedIntegerArray array = new SortedIntegerArray(empty);

        assertFalse(array.kPairSum(0));
    }

    @Test
    public void boundsTest2() {
        int[] array = {1, 2};
        SortedIntegerArray sorted = new SortedIntegerArray(array);

        assertFalse(sorted.kPairSum(0));
        assertTrue(sorted.kPairSum(3));
    }

    @Test
    public void pairSumRandomizedTest() {
        int passes = 0, fails = 0;
        for (int i = 0; i < 200; i++) {
            int[] array = generateArray(
                    ThreadLocalRandom.current().nextInt(2, 51),
                    0, i < 100 ? 25 : Integer.MAX_VALUE);
            Integer k;

            if (ThreadLocalRandom.current().nextBoolean()) {
                k = array[ThreadLocalRandom.current().nextInt(0, (array.length) / 2)] +
                        array[ThreadLocalRandom.current().nextInt((array.length) / 2, array.length)];
            } else {
                k = Integer.MIN_VALUE;
            }

            int[] pair = getPair(array, k);

            SortedIntegerArray sorted = new SortedIntegerArray(array);
            boolean result = sorted.kPairSum(k);
            if (i < 100) {
                if (result) {
                    passes++;
                } else {
                    fails++;
                }
            }
            assertEquals(pair != null, result);
        }
        System.out.println(passes + " Arrays with sums, " + fails + " Arrays without sums");
    }
}
