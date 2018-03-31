package A4Q1;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Provides two static methods for sorting Integer arrays (heapSort and mergeSort)
 *
 * @author jameselder
 */
public class YorkArrays {

    /* Sorts the input array of Integers a using HeapSort.  Result is returned in a.
     * Makes use of java.util.PriorityQueue.  
       Sorting is NOT in place - PriorityQueue allocates a separate heap-based priority queue. 
       Not a stable sort, in general.  
       Throws a null pointer exception if the input array is null.  */
    public static void heapSort(Integer[] a) throws NullPointerException {
        //implement this method.
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        if (a == null) {
            throw new NullPointerException();
        } else {
            Collections.addAll(queue, a);

            for (int i = 0; i < a.length; i++) {
                a[i] = queue.poll();
            }
        }
    }

    /* Sorts the input array of Integers a using mergeSort and returns result.
     * Sorting is stable.
       Throws a null pointer exception if the input array is null. */
    public static Integer[] mergeSort(Integer[] a) throws NullPointerException {
        return (mergeSort(a, 0, a.length - 1));
    }

    /* Sorts the input subarray of Integers a[p...q] using MergeSort and returns result.
     * Sorting is stable.
     */
    private static Integer[] mergeSort(Integer[] a, int p, int q) {
        //implement this method.
        return (p < q) ? (merge(mergeSort(a, p, (p + q) / 2), mergeSort(a, ((p + q) / 2) + 1, q))) : (new Integer[]{a[p]});
    }

    /* Merges two sorted arrays of Integers into a single sorted array.  Given two
     * equal elements, one in a and one in b, the element in a precedes the element in b.
     */
    private static Integer[] merge(Integer[] a, Integer[] b) {
        //implement this method.
        Integer[] result = new Integer[a.length + b.length];
        int resultIndex = 0, j = 0, i = 0;


        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                result[resultIndex] = a[i];
                i++;
            } else {
                result[resultIndex] = b[j];
                j++;
            }
            resultIndex++;
        }

        while (i < a.length) {
            result[resultIndex] = a[i];
            i++;
            resultIndex++;
        }

        while (j < b.length) {
            result[resultIndex] = b[j];
            j++;
            resultIndex++;
        }

        return result;
    }


    public static void main(String[] args) {
        Integer[] a = {10, 45, 55, 78, 93};
        Integer[] b = {22, 23, 25, 25, 41};

        Integer[] sort = {15, 12, 15, 28, 46, 13, 56, 74};

        //Integer[] result = (merge(a, b));
        Integer[] result = (mergeSort(a));

        System.out.println("\n\n\nANSWER:\n");

        for (Integer i : result) {
            System.out.print(i + " ");
        }
    }
}