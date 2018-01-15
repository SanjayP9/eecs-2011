package A1Q1;

import com.sun.deploy.util.ArrayUtil;
import static org.junit.Assert.*;
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
            long rand = smallValues ? ThreadLocalRandom.current().nextLong(1, numberOfElements + 1) : rnd.nextLong();
            while (Arrays.asList(unsorted).contains(rand) || rand < 1) {
                rand = smallValues ? ThreadLocalRandom.current().nextLong(1, numberOfElements + 1) : rnd.nextLong();
            }
            unsorted[i] = rand;
        }

        // Add random set to linked list
        for (long num : unsorted) {
            double val = rnd.nextDouble();
            while (val == 0) {
                val = rnd.nextDouble();
            }
            val *= 10d;
            val = Math.round (val * 100.0) / 100.0;
            vector.add(new SparseNumericElement(num, val));
        }

        Arrays.sort(unsorted);
        return unsorted;
    }

    public boolean verifyEqual(Long[] array, SparseNumericVector vector) {
        // Verify linked list is sorted
        SparseNumericIterator iterator = new SparseNumericIterator(vector);
        System.out.println("Head: " +  X.head.getElement().getIndex());
        for (long num : array) {
            if (!iterator.hasNext()) {
                fail("List is missing elements");
            }

            if (num != iterator.position.getElement().getIndex()) {
                fail("Expected: " + num + ", Actual: " + iterator.position.getElement().getIndex());
            } else {
                System.out.println("Expected: " +  num + " | Actual: " + iterator.position.getElement().getIndex());
            }
            iterator.next();
        }
        System.out.println("Tail: " +  X.tail.getElement().getIndex());
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
        Long[] testArray = generateArray(50, X, true);

        // Verify
        verifyEqual(testArray, X);
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
        X.add(new SparseNumericElement(15, 3));
        assertTrue(X.remove(15L));
        assertEquals(0, X.size);
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

        int elementsToRemove = rnd.nextInt(29) + 1;

        System.out.println("Keeping " + elementsToRemove + " from list");

        List<Long> testArray = new LinkedList<>(Arrays.asList(generateArray(30, X, false)));
        List<Long> removed = new LinkedList<>();
        removed.addAll(testArray);

        // Select elements to remove
        for (int i = 0; i < elementsToRemove; i++) {
            removed.remove(rnd.nextInt(removed.size() - 1));
        }

        testArray.removeAll(removed);

        // Remove elements from linked list
        for (Long num : removed) {
            X.remove(num);
        }

        assertEquals(testArray.size(), X.size);
        verifyEqual(testArray.toArray(new Long[testArray.size()]), X);
        System.out.print("PASS\n");
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
        Long[] testArray = generateArray(30, X, false);
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
                System.out.println("X: " + value + "Y: " + yVal + " | " + value * yVal + " | " + projection);
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

        /*
        double projection;

        X.add(new SparseNumericElement(150000, 3.1415));
        X.add(new SparseNumericElement(15, 3));
        X.add(new SparseNumericElement(1500, 3.14));
        X.add(new SparseNumericElement(150, 3.1));
        X.add(new SparseNumericElement(15000, 3.141));
        Y.add(new SparseNumericElement(150000, 1));
        Y.add(new SparseNumericElement(15, 1));
        X.remove((long) 150);

        projection = X.dot(Y);

        System.out.println("The inner product of");
        System.out.print(X);
        System.out.println("and");
        System.out.print(Y);
        System.out.println("is ");
        System.out.printf("%.5f\n\n",projection); //answer should be 3*1 + 3.1415*1 = 6.1415

        assertEquals(6.1415, projection, 0.0001);
        */
    }

    @Test
    public void testDot2() {
        // Size of 1, equivalent lists
        Long[] testArray = generateArray(1, X, true);
        Y.add(new SparseNumericElement(testArray[0], 25));
        double actual = X.dot(Y);
        computeProjection(new LinkedList<>(Arrays.asList(testArray)), new LinkedList<>(Arrays.asList(testArray)));

        System.out.println(X);
        System.out.println(Y);
        assertEquals(projection, actual, 0.0001);
    }

    @Test
    public void testDot3() {
        // Size of 1, not equivalent
        Long[] testArray = generateArray(1, X, true);
        Y.add(new SparseNumericElement(252525, 25));
        double actual = X.dot(Y);
        computeProjection(new LinkedList<>(Arrays.asList(testArray)), new LinkedList<>(Arrays.asList(testArray)));

        System.out.println(X);
        System.out.println(Y);
        System.out.println("X Dot Y = " + projection);
        assertEquals(projection, actual, 0.0001);
    }

    @Test
    public void testDot4() {
        // Randomized dot of same size
        Long[] testArray = generateArray(5, X, true);
        Long[] secondTestArray = generateArray(5, Y, true);
        double actual = X.dot(Y);
        computeProjection(new LinkedList<>(Arrays.asList(testArray)), new LinkedList<>(Arrays.asList(secondTestArray)));

        System.out.println(X);
        System.out.println(Y);
        System.out.println("X Dot Y = " + projection);
        assertEquals(projection, actual, 0.0001);
    }

    @Test
    public void testDot5() {
        // Randomized dot of different size
        Long[] testArray = generateArray(10, X, true);
        Long[] secondTestArray = generateArray(15, Y, true);
        double actual = X.dot(Y);
        computeProjection(new LinkedList<>(Arrays.asList(testArray)), new LinkedList<>(Arrays.asList(secondTestArray)));

        System.out.println(X);
        System.out.println(Y);
        System.out.println("X Dot Y = " + projection);
        assertEquals(projection, actual, 0.0001);
    }

    @Test
    public void testDot6() {
        // X dot Y = Y dot X
        Long[] testArray = generateArray(25, X, true);
        Long[] secondTestArray = generateArray(15, Y, true);
        double actual = X.dot(Y);
        computeProjection(new LinkedList<>(Arrays.asList(testArray)), new LinkedList<>(Arrays.asList(secondTestArray)));

        System.out.println(X);
        System.out.println(Y);
        System.out.println("X Dot Y = " + projection);
        assertEquals(projection, actual, 0.0001);
        actual = Y.dot(X);
        assertEquals(projection, actual, 0.0001);
    }
}
