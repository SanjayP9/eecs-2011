package A2Q1;

import java.util.*;

/**
 * Represents a sorted integer array.  Provides a method, kpairSum, that
 * determines whether the array contains two elements that sum to a given
 * integer k.  Runs in O(n) time, where n is the length of the array.
 *
 * @author jameselder
 */
public class SortedIntegerArray {

    protected int[] sortedIntegerArray;

    public SortedIntegerArray(int[] integerArray) {
        sortedIntegerArray = integerArray.clone();
        Arrays.sort(sortedIntegerArray);
    }

    /**
     * Determines whether the array contains two elements that sum to a given
     * integer k. Runs in O(n) time, where n is the length of the array.
     *
     * @author jameselder
     */
    public boolean kPairSum(Integer k) {
        //implement this method
        if (this.sortedIntegerArray.length <= 1) {
            return false;
        } else if (this.sortedIntegerArray[1] > k) {
            return false;
        } else {
            return kPairSumInterval(k, 0, this.sortedIntegerArray.length - 1);
        }
    }

    private boolean kPairSumInterval(Integer k, int i, int j) {
        if (i == j) {
            return false;
        }

        Long sum = (long) this.sortedIntegerArray[i] + (long) this.sortedIntegerArray[j];
        if (sum == (long) k) {
            return true;
        } else {
            return (sum > k) ? (kPairSumInterval(k, i, j - 1)) : (kPairSumInterval(k, i + 1, j));
        }
    }
}