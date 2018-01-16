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
     *
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
        double sum = 0, count = 0;

        for (int i = top; i <= bottom; i++) {
            for (int j = left; j <= right; j++) {
                sum += array[i][j] * 1.0d;
                count += 1.0d;
            }
        }

        return (sum / count);
    }

    @Test
    public void boundsTest1() {
        // Jagged array
        int[][] jagged = new int[2][2];
        jagged[0] = new int[5];
        jagged[1] = new int[1];

        assertThrows(InvalidImageException.class, () -> new IntegralImage(jagged));
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
        } catch (InvalidImageException e) {
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
    public void meanTest5() {
        for (int i = 0; i < 100; i++) {
            int[][] array = generateArray(
                    ThreadLocalRandom.current().nextInt(1, 50),
                    ThreadLocalRandom.current().nextInt(1, 50)
            );

            IntegralImage image;
            try {
                int top = ThreadLocalRandom.current().nextInt(array.length);
                int left = ThreadLocalRandom.current().nextInt(array[0].length);
                int bottom = ThreadLocalRandom.current().nextInt(array.length);
                int right = ThreadLocalRandom.current().nextInt(array[0].length);
                while (bottom < top) {
                    bottom = ThreadLocalRandom.current().nextInt(array.length);
                }

                while (right < left) {
                    right = ThreadLocalRandom.current().nextInt(array[0].length);
                }

                System.out.println("i = " + i + " row nums: " + array.length + " | " + "col nums: " + array[0].length);
                System.out.println("top " + top + " | " + "bottom " + bottom + " : " + "left " + left + " | " + "right " + right);
                System.out.println();

                image = new IntegralImage(array);
                double mean = 0;
                try {
                    mean = image.meanSubImage(top, bottom, left, right);
                } catch (Exception e) {
                    fail("This ain't supposed to happen: " + e.toString() + "\ntop " + top + "\nbottom " + bottom + "\nleft " + left + "\nright " + right + "\n\nheight " + array.length + "\nwidth " + array[0].length);
                }

                double expected = calculateMean(array, top, bottom, left, right);
                assertEquals(expected, mean, 0.000001d);
            } catch (InvalidImageException e) {
                fail("Failed to generate array");
            }
        }
    }
}
