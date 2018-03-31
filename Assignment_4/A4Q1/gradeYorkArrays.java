package A4Q1;
import java.util.*;
import java.lang.Math.*;

/**
 *
 * @author jameselder
 */
public class gradeYorkArrays {

    public static void main(String[] args) {
        int n = 100;
        Integer[] a = new Integer[n];
        Integer[] b,c = null;
        long startTime, endTime;
        int[] marks = new int[5];
        int testNum = 0;
        int nCorrect = 0;
        
        for (int i = 0; i < n; i++) {
            a[i] = (int) Math.round(n* Math.random());
        }
        System.out.println("Input Array: " + Arrays.toString(a));
        
        //Test Case 1
        //Mergesort
        System.out.print("Test Case " + ++testNum + ": ");
        try {
            startTime = System.nanoTime();
            b = YorkArrays.mergeSort(a);
            endTime = System.nanoTime();
            System.out.println("Output Array (MergeSort)," + (endTime - startTime) / 1000 + " microseconds: " + Arrays.toString(b));
            int i = 0;
            boolean failed = false;
            while (i < n - 1 && !failed) {
                failed = (b[i + 1] < b[i]);
                i++;
            }
            if (!failed) {
                System.out.println("Outcome:  Passed.");
                marks[testNum] = 1;
            } else {
                System.out.println("Output list is not sorted.");
                System.out.println("Outcome:  Failed.");
            }
        } catch (Exception x) {
            System.out.println("Exception thrown");
            System.out.println("Outcome:  Failed.");
        }

        //Test Case 2
        //HeapSort
        System.out.print("\n\nTest Case " + ++testNum + ": ");
        b = a.clone();
        try {
            startTime = System.nanoTime();
            YorkArrays.heapSort(a);
            endTime = System.nanoTime();
            System.out.println("Output Array (HeapSort)," + (endTime - startTime) / 1000 + " microseconds: " + Arrays.toString(a));
            int i = 0;
            boolean failed = false;
            while (i < n - 1 && !failed) {
                failed = (a[i + 1] < a[i]);
                i++;
            }
            if (!failed) {
                System.out.println("Outcome:  Passed.");
                marks[testNum] = 1;
            } else {
                System.out.println("Output list is not sorted.");
                System.out.println("Outcome:  Failed.");
            }
        } catch (Exception x) {
            System.out.println("Exception thrown");
            System.out.println("Outcome:  Failed.");
        }

       try {
            startTime = System.nanoTime();
            Arrays.sort(b);
            endTime = System.nanoTime();
            System.out.println("Output Array (QuickSort)," + (endTime - startTime) / 1000 + " microseconds: " + Arrays.toString(b));

        } catch (Exception x) {
            System.out.println("Quicksort Fails");
        }

        //Test Case 3
        //should throw exception
        System.out.print("\n\nTest Case " + ++testNum + ": ");
        try {
            YorkArrays.mergeSort(c);
            System.out.println("mergeSort fails to detect null pointer");
            System.out.println("Outcome:  Failed.");
        } catch (NullPointerException x) {
            System.out.println("mergeSort correctly detects null pointer");
            System.out.println("Outcome:  Passed.");
            marks[testNum] = 1;
        } catch (Exception x) {
            System.out.println("Incorrect: mergeSort threw an exception but not the Null Pointer Exception that was expected");
            System.out.println("Outcome:  Failed.");
        }
       
        //Test Case 4
        //should throw exception
        System.out.print("\n\nTest Case " + ++testNum + ": ");
        try {
            YorkArrays.heapSort(c);
            System.out.println("heapSort fails to detect null pointer");
            System.out.println("Outcome:  Failed.");
        } catch (NullPointerException x) {
            System.out.println("heapSort correctly detects null pointer");
            System.out.println("Outcome:  Passed.");
            marks[testNum] = 1;
        } catch (Exception x) {
            System.out.println("Incorrect: heapSort threw an exception but not the Null Pointer Exception that was expected");
            System.out.println("Outcome:  Failed.");
        }
        
        System.out.print("\n\nTest Case Summary: ");
        for (int i = 1; i < marks.length; i++) {
            System.out.print(marks[i] + " ");
            nCorrect += marks[i];
        }
        System.out.println();
        System.out.println("Test Case Grade: " + (double) nCorrect / (marks.length - 1));

    }
}
