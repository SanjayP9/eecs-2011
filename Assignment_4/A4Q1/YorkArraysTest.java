package A4Q1;
import org.testng.annotations.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class YorkArraysTest {

    @Test
    public void hardTest1() {
        Random r = new Random();
        int n = 20;
        Integer[] a = new Integer[n];
        Integer[] b = null;
        long startTime, endTime;

        try {
            YorkArrays.mergeSort(b);
        } catch (NullPointerException x) {
            System.out.println("mergeSort correctly detects null pointer");
        }
        try {
            YorkArrays.heapSort(b);
        } catch (NullPointerException x) {
            System.out.println("heapSort correctly detects null pointer");
        }

        for (int i=0; i < n; i++) {
            a[i] = r.nextInt(n);
        }
        System.out.println("Input Array: " + Arrays.toString(a));

        startTime = System.nanoTime();
        b = YorkArrays.mergeSort(a);
        endTime = System.nanoTime();
        System.out.println("Output Array (MergeSort)," + (endTime - startTime) / 1000 + " microseconds: " + Arrays.toString(b));

        b = a.clone();
        startTime = System.nanoTime();
        YorkArrays.heapSort(a);
        endTime = System.nanoTime();
        System.out.println("Output Array (HeapSort):" + (endTime - startTime) / 1000 + " microseconds: " + Arrays.toString(a));

        startTime = System.nanoTime();
        Arrays.sort(b);
        endTime = System.nanoTime();
        System.out.println("Output Array (QuickSort):" + (endTime - startTime) / 1000 + " microseconds: " + Arrays.toString(b));
    }

    @Test
    public void randomHashMapTest1() {
        long startTime, endTime;

        int n = 20;
        Random r = new Random();
        Integer[] a = new Integer[n];
        for (int i=0; i < n; i++) {
            a[i] = r.nextInt(n);
        }

        Integer[] b = a.clone();
        Arrays.sort(a);

        startTime = System.nanoTime();
        YorkArrays.heapSort(b);
        endTime = System.nanoTime();
        System.out.println("HeapSort: " + (endTime - startTime) / 1000 + "ms");

        for (int i = 0; i < b.length; i++) {
            assertEquals(a[i], b[i]);
        }

    }

    @Test
    public void randomMergeSortTest1() {
        long startTime, endTime;

        int n = 31;
        Random r = new Random();
        Integer[] a = new Integer[n];
        for (int i=0; i < n; i++) {
            a[i] = r.nextInt(n);
        }
        Integer[] sorted = a.clone();
        Arrays.sort(sorted);

        Integer[] b;

        startTime = System.nanoTime();
        b = YorkArrays.mergeSort(a);
        endTime = System.nanoTime();

        System.out.println("Merge Sort: " + (endTime - startTime) / 1000 + " microseconds");

        b = a.clone();
        MergeSort sorter = new MergeSort();

        startTime = System.nanoTime();
        sorter.sort(b,0, b.length - 1);
        endTime = System.nanoTime();

        System.out.println("Merge Sort (Online): " + (endTime - startTime) / 1000 + " microseconds");

        b = a.clone();
        startTime = System.nanoTime();
        Arrays.sort(b);
        endTime = System. nanoTime();
        System.out.println("Quicksort: " + (endTime - startTime) / 1000  + " microseconds");


        for (int i = 0; i < sorted.length; i++) {
            assertEquals(sorted[i], b[i]);
        }
    }
}