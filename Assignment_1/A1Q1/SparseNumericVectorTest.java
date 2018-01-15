package A1Q1;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SparseNumericVectorTest {

    SparseNumericVector X, Y;
    double projection;

    @Before
    public void start() {
        X = new SparseNumericVector();
        Y = new SparseNumericVector();
    }

    /**
     * Generates an unsorted array, inserts into specified vector, then returns a sorted version of it
     * @param numberOfElements Number of elements for the array
     * @param vector Vector to add the elements to
     * @return
     */
    public Long[] generateArray(int numberOfElements, SparseNumericVector vector, boolean smallValues) {
        Long[] unsorted = new Long[numberOfElements];

        Random rnd = ThreadLocalRandom.current();

        for (int i = 0; i < unsorted.length; i++) {
            long rand = smallValues ? ThreadLocalRandom.current().nextLong(1, numberOfElements + 25) : rnd.nextLong();
            while (Arrays.asList(unsorted).contains(rand) || rand < 1) {
                rand = smallValues ? ThreadLocalRandom.current().nextLong(1, numberOfElements + 25) : rnd.nextLong();
            }
            unsorted[i] = rand;
        }

        // Add random set to linked list
        for (long num : unsorted) {
            double val = rnd.nextDouble() + 0.01;
            while (val == 0.0d) {
                val = rnd.nextDouble();
            }
            val *= 10.0d;
            val = (double)Math.round(val * 100.0d) / 100.0d;

            if (val == 0.0d) {
                fail("This isn't supposed to happen");
            }

            vector.add(new SparseNumericElement(num, val));
        }

        Arrays.sort(unsorted);
        return unsorted;
    }

    public boolean verifyEqual(Long[] array, SparseNumericVector vector) {
        // Verify linked list is sorted
        SparseNumericIterator iterator = new SparseNumericIterator(vector);
        for (long num : array) {
            if (!iterator.hasNext()) {
                System.out.println("Head: " +  X.head.getElement().getIndex());
                System.out.println("Tail: " +  X.tail.getElement().getIndex());
                System.out.println(array);
                System.out.println(X);
                fail("List is missing elements");
            }

            if (num != iterator.position.getElement().getIndex()) {
                System.out.println("Head: " +  X.head.getElement().getIndex());
                System.out.println("Tail: " +  X.tail.getElement().getIndex());
                System.out.println(array);
                System.out.println(X);
                fail("Expected: " + num + ", Actual: " + iterator.position.getElement().getIndex());
            }
            iterator.next();
        }
        return true;
    }

    @Test
    public void testAdd1() {
        // Add vector with value of 0
        assertThrows(UnsupportedOperationException.class, () -> X.add(new SparseNumericElement(10, 0)));
    }

    @Test
    public void testAdd2() {
        // Add vector with negative index
        assertThrows(IndexOutOfBoundsException.class, () -> X.add(new SparseNumericElement(-1, 1)));
    }

    @Test
    public void testAdd3() {
        // Add vector with 0 index
        assertThrows(IndexOutOfBoundsException.class, () -> X.add(new SparseNumericElement(0, 1)));

    }

    @Test
    public void testAdd5() {
        // Generate Random Set
        // Works for sizes from 1 to 100, Alternating between using small numbers and large ones
        for (int i = 1; i < 100; i++) {
            X = new SparseNumericVector();
            Long[] testArray = generateArray(i, X, i % 2 == 0);

            // Verify
            verifyEqual(testArray, X);
        }
    }

    @Test
    public void testAdd6() {
        // Add duplicate
        Long[] testArray = generateArray(1, X, false);

        assertThrows(UnsupportedOperationException.class, () -> {
            X.add(new SparseNumericElement(testArray[0], 0.25));
            X.add(new SparseNumericElement(testArray[0], 0.24));
        });
    }

    @Test
    public void testRemove1() {
        assertFalse(X.remove(0L));
    }

    @Test
    public void testRemove2() {
        // Add and remove one
        X.add(new SparseNumericElement(15, 3));
        assertTrue(X.remove(15L));
        assertEquals(0, X.size);
        assertTrue(X.head == null);
        assertTrue(X.tail == null);
    }

    @Test
    public void testRemove3() {
        // Test remove head
        generateArray(5, X, false);
        long secondIndex = X.head.getNext().getElement().getIndex();

        X.remove(X.head.getElement().getIndex());
        assertEquals(secondIndex, X.head.getElement().getIndex());
    }

    @Test
    public void testRemove4() {
        // Test remove tail
        List<Long> testArray = new LinkedList<>(Arrays.asList(generateArray(10, X, false)));

        // Get parent of tail to be removed
        SparseNumericIterator iterator = new SparseNumericIterator(X);
        while (iterator.position.getNext().getNext() != null) {
            iterator.next();
        }
        SparseNumericNode newTail = iterator.position;
        Long secondLast = iterator.position.getNext().getElement().getIndex();

        testArray.remove(secondLast);
        X.remove(secondLast);

        // Verify linked list is equivalent
        assertTrue(verifyEqual(testArray.toArray(new Long[testArray.size()]), X));

        // Make sure the new the new tail is correct
        assertTrue(newTail.getElement().getIndex() == X.tail.getElement().getIndex());
        assertTrue(X.tail.getNext() == null);
    }

    @Test
    public void testRemove5() {
        // Test randomized
        Random rnd = ThreadLocalRandom.current();

        // Removal for linked list size 1 to 100, alternating between small and large numbers
        for (int i = 1; i < 100; i++) {
            X = new SparseNumericVector();
            int elementsToRemove = rnd.nextInt(i);

            List<Long> testArray = new LinkedList<>(Arrays.asList(generateArray(i, X, i % 2 == 0)));
            List<Long> removed = new LinkedList<>();
            removed.addAll(testArray);

            // Select elements to remove
            for (int j = 0; j < elementsToRemove; j++) {
                removed.remove(rnd.nextInt(removed.size() - 1));
            }

            testArray.removeAll(removed);

            // Remove elements from linked list
            for (Long num : removed) {
                X.remove(num);
            }

            assertEquals(testArray.size(), X.size);
            verifyEqual(testArray.toArray(new Long[testArray.size()]), X);
        }
    }

    @Test
    public void testRemove6() {
        // Remove list size of 1
        Long[] testArray = generateArray(1, X, false);
        for (Long num : testArray) {
            X.remove(num);
        }

        assertEquals(0, X.size);
        assertTrue(X.head == null);
        assertTrue(X.tail == null);
    }

    @Test
    public void testRemove7() {
        // Remove everything
        Long[] testArray = generateArray(ThreadLocalRandom.current().nextInt(100), X, false);
        for (Long num : testArray) {
            X.remove(num);
        }

        assertEquals(0, X.size);
        assertTrue(X.head == null);
        assertTrue(X.tail == null);
    }

    /**
     * Computes the dot product of X and Y
     * @return
     */
    public void computeProjection(LinkedList<Long> xArray, LinkedList<Long> yArray) {
        projection = 0;

        if (xArray.size() == 0 || yArray.size() == 0) {
            return;
        }

        // Get all equal values from both arrays
        xArray.retainAll(yArray);

        if (xArray.size() == 0 ) {
            return;
        }

        // Get all values from X
        List<Double> xValues = new LinkedList<>();

        SparseNumericIterator iterator = new SparseNumericIterator(X);
        while (iterator.hasNext()) {
            if (xArray.contains(iterator.position.getElement().getIndex())) {
                xValues.add(iterator.position.getElement().getValue());
            }
            iterator.next();
        }

        iterator = new SparseNumericIterator(Y);
        Iterator xArrayIterator = xArray.iterator();
        Iterator xValuesIterator = xValues.iterator();

        Long index = (Long)xArrayIterator.next();
        Double value = (Double)xValuesIterator.next();
        while (iterator.hasNext()) {
            if (iterator.position.getElement().getIndex() == index) {
                double yVal = iterator.position.getElement().getValue();;
                projection += value * yVal;
                if (xArrayIterator.hasNext()) {
                    index = (Long) xArrayIterator.next();
                    value = (Double) xValuesIterator.next();
                }
            }
            iterator.next();
        }
    }

    @Test
    public void testDot1() {
        // One is empty
        Long[] testArray = generateArray(10, X, true);
        double actual = X.dot(Y);
        computeProjection( new LinkedList<>(Arrays.asList(testArray)), new LinkedList<>());

        assertEquals(projection, actual, 0.0001);
    }

    @Test
    public void testDot2() {
        // Size of 1, equivalent lists
        Long[] testArray = generateArray(1, X, true);
        Y.add(new SparseNumericElement(testArray[0], 25));
        double actual = X.dot(Y);
        computeProjection(new LinkedList<>(Arrays.asList(testArray)), new LinkedList<>(Arrays.asList(testArray)));

        assertEquals(projection, actual, 0.0001);
    }

    @Test
    public void testDot3() {
        // Size of 1, not equivalent
        Long[] testArray = generateArray(1, X, true);
        Y.add(new SparseNumericElement(252525, 25));
        double actual = X.dot(Y);
        computeProjection(new LinkedList<>(Arrays.asList(testArray)), new LinkedList<>(Arrays.asList(testArray)));

        assertEquals(projection, actual, 0.0001);
    }

    @Test
    public void testDot4() {
        // Randomized dot of same size
        for (int i = 0; i < 100; i++) {
            X = new SparseNumericVector();
            Y = new SparseNumericVector();

            Long[] testArray = generateArray(i, X, true);
            Long[] secondTestArray = generateArray(i, Y, true);
            double actual = X.dot(Y);
            computeProjection(new LinkedList<>(Arrays.asList(testArray)), new LinkedList<>(Arrays.asList(secondTestArray)));

            assertEquals(projection, actual, 0.0001);
        }
    }

    @Test
    public void testDot5() {
        // Randomized dot of different size
        for (int i = 1; i < 100; i++) {
            for (int j = 99; j > 0; j--) {
                X = new SparseNumericVector();
                Y = new SparseNumericVector();

                Long[] testArray = generateArray(i, X, true);
                Long[] secondTestArray = generateArray(j, Y, true);
                computeProjection(new LinkedList<>(Arrays.asList(testArray)), new LinkedList<>(Arrays.asList(secondTestArray)));
                double actual = X.dot(Y);
                assertEquals(projection, actual, 0.0001);
            }
        }
    }

    @Test
    public void testDot6() {
        // X dot Y = Y dot X
        for (int i = 1; i < 100; i++) {
            for (int j = 99; j > 0; j--) {
                X = new SparseNumericVector();
                Y = new SparseNumericVector();

                Long[] testArray = generateArray(i, X, true);
                Long[] secondTestArray = generateArray(j, Y, true);
                double actual = X.dot(Y);
                computeProjection(new LinkedList<>(Arrays.asList(testArray)), new LinkedList<>(Arrays.asList(secondTestArray)));

                assertEquals(projection, actual, 0.0001);
                actual = Y.dot(X);
                assertEquals(projection, actual, 0.0001);
            }
        }
    }
}