package A1Q2;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OtherIntegralTest {

    /**
     * Generates a 2d array
     * @param col
     * @param row
     */
    public int[][] generateArray(int col, int row) {
        int[][] array = new int[row][col];
        Random rand = ThreadLocalRandom.current();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                array[i][j] = rand.nextInt(255);
            }
        }
        return array;
    }

    public double calculateMean(int[][] array, int top, int bottom, int left, int right) {
        double sum = 0;

        for (int i = top; i <= bottom; i++) {
            for (int j = left; j <= right; j++) {
                sum += array[i][j];
            }
        }

        return sum / ((bottom - top + 1) * (right - left + 1)) * 1.0d;
    }

    @Test
    public void boundsTest1() {
        // Jagged array
        for (int i = 0; i < 100; i ++) {
            int[][] jagged = generateArray(
                    ThreadLocalRandom.current().nextInt(2, 50),
                    ThreadLocalRandom.current().nextInt(2, 50)
            );

            int jagger = ThreadLocalRandom.current().nextInt(0, jagged.length);
            jagged[jagger] = new int[100];

            assertThrows(InvalidImageException.class, () -> new IntegralImage(jagged));
        }
    }


    @Test
    public void meanTest1() {
        int[][] array = generateArray(10, 10);
        IntegralImage image;
        try {
            image = new IntegralImage(array);
            assertThrows(BoundaryViolationException.class,
                    () -> image.meanSubImage(-1, 9, 0, 9));
            assertThrows(BoundaryViolationException.class,
                    () -> image.meanSubImage(0, array.length + 1, 0, 9));
            assertThrows(BoundaryViolationException.class,
                    () -> image.meanSubImage(0, 9, -1, 9));
            assertThrows(BoundaryViolationException.class,
                    () -> image.meanSubImage(0, 9, 0, array[0].length + 1));
        }
        catch (InvalidImageException e) {
            fail("Failed to generate array");
        }
    }

    @Test
    public void meanTest2() {
        int[][] array = generateArray(10, 10);
        IntegralImage image;
        try {
            image = new IntegralImage(array);

            assertThrows(NullSubImageException.class,
                    () -> image.meanSubImage(10, 0, 0, 10));
            assertThrows(NullSubImageException.class,
                    () -> image.meanSubImage(0, 10, 10, 0));

        } catch (InvalidImageException e) {
            fail("Failed to generate array");
        }
    }

    @Test
    public void meanTest3() {
        // Given test case
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        double mean = 0;

        IntegralImage image;
        try {
            image = new IntegralImage(array);
            mean = image.meanSubImage(1, 2, 1, 2); //should be 7.0
            double expected = calculateMean(array, 1, 2, 1, 2);
            assertEquals(expected, mean, 0.0001d);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void meanTest4() {
        for (int i = 0; i < 100; i++) {
            int[][] array = generateArray(
                    ThreadLocalRandom.current().nextInt(1, 50),
                    ThreadLocalRandom.current().nextInt(1, 50)
            );

            IntegralImage image;
            try {
                int top = ThreadLocalRandom.current().nextInt(0, array.length);
                int left = ThreadLocalRandom.current().nextInt(0, array[0].length);
                int bottom = ThreadLocalRandom.current().nextInt(0,array.length);
                int right = ThreadLocalRandom.current().nextInt(0, array[0].length);
                while (bottom < top) {
                    bottom = ThreadLocalRandom.current().nextInt(0, array.length);
                }

                while (right < left) {
                    right = ThreadLocalRandom.current().nextInt(0, array[0].length);
                }

                image = new IntegralImage(array);
                double expected = calculateMean(array, top, bottom, left, right);
                double mean = 0;
                try {
                    mean = image.meanSubImage(top, bottom, left, right);
                } catch (Exception e) {
                    fail("Failed to get mean with bounds " +  top + ", " + bottom +
                            ", " + left + ", " + right + "\n" + "Of array size " + array.length + ", " +
                            array[0].length);
                }

                assertEquals(expected, mean, 0.000001d);
            } catch (InvalidImageException e) {
                fail("Failed to generate array");
            }
        }
    }
}