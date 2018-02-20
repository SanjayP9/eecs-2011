package A2Q2;

import java.util.*;

/**
 * Adaptible priority queue using location-aware entries in a min-heap, based on
 * an extendable array.  The order in which equal entries were added is preserved.
 *
 * @param <E> The entry type.
 * @author jameselder
 */
public class APQ<E> {

    private final ArrayList<E> apq; //will store the min heap
    private final Comparator<E> comparator; //to compare the entries
    private final Locator<E> locator;  //to locate the entries within the queue

    /**
     * Constructor
     *
     * @param comparator used to compare the entries
     * @param locator    used to locate the entries in the queue
     * @throws NullPointerException if comparator or locator parameters are null
     */
    public APQ(Comparator<E> comparator, Locator<E> locator) throws NullPointerException {
        if (comparator == null || locator == null) {
            throw new NullPointerException();
        }
        apq = new ArrayList<>();
        apq.add(null); //dummy value at index = 0
        this.comparator = comparator;
        this.locator = locator;
    }

    /**
     * Inserts the specified entry into this priority queue.
     *
     * @param e the entry to insert
     * @throws NullPointerException if parameter e is null
     */
    public void offer(E e) throws NullPointerException {
        //implement this method
        if (e == null) {
            throw new NullPointerException();
        } else {
            this.apq.add(e);
            locator.set(e, this.apq.size() - 1);
            upheap(this.apq.size() - 1);
        }
    }

    /**
     * Removes the entry at the specified location.
     *
     * @param pos the location of the entry to remove
     * @throws BoundaryViolationException if pos is out of range
     */
    public void remove(int pos) throws BoundaryViolationException {
        //implement this method
        if (pos <= 0 || pos >= this.apq.size()) {
            throw new BoundaryViolationException();
        } else if (pos == 1) {
            poll();
        } else if (pos == this.size()) {
            this.apq.remove(pos);
        } else {
            swap(pos, 1);
            poll();
        }
    }

    /**
     * Removes the first entry in the priority queue.
     */
    public E poll() {
        //implement this method
        if (isEmpty()) {
            return null;
        } else {
            E result = this.apq.get(1);
            swap(1, this.apq.size() - 1);
            this.apq.remove(this.apq.size() - 1);
            downheap(1);
            return result;
        }
    }

    /**
     * Returns but does not remove the first entry in the priority queue.
     */
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return apq.get(1);
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public int size() {
        return apq.size() - 1; //dummy node at location 0
    }


    /**
     * Shift the entry at pos upward in the heap to restore the minheap property
     *
     * @param pos the location of the entry to move
     */
    private void upheap(int pos) {
        //implement this method
        int parentIndex = getParentIndex(pos);

        if (parentIndex >= 0 && parentIndex < this.apq.size()) {
            if (this.apq.get(parentIndex) != null) {
                if (comparator.compare(this.apq.get(pos), this.apq.get(parentIndex)) < 0) {
                    swap(pos, parentIndex);
                    upheap(getParentIndex(pos));
                }
            }
        }
    }

    /**
     * Shift the entry at pos downward in the heap to restore the minheap property
     *
     * @param pos the location of the entry to move
     */
    private void downheap(int pos) {
        //implement this method
        int leftChild = getLeftChildIndex(pos);
        int rightChild = getRightChildIndex(pos);

        if (leftChild >= this.apq.size() || leftChild <= 0) {
            return;
        } else if (rightChild >= this.apq.size() || rightChild <= 0) {
            if (comparator.compare(this.apq.get(leftChild), this.apq.get(pos)) < 0) {
                swap(leftChild, pos);
                downheap(leftChild);
            }
        } else { // If both child's are available
            // if left child is smaller then store in lesserChild else store right child
            int lesserChildIndex = (comparator.compare(this.apq.get(leftChild), this.apq.get(rightChild)) < 0) ? (leftChild) : (rightChild);

            if (comparator.compare(this.apq.get(lesserChildIndex), this.apq.get(pos)) < 0) {
                swap(pos, lesserChildIndex);
                downheap(pos);
            }
        }
    }

    /**
     * Swaps the entries at the specified locations.
     *
     * @param pos1 the location of the first entry
     * @param pos2 the location of the second entry
     */
    private void swap(int pos1, int pos2) {
        //implement this method
        E temp = this.apq.get(pos1);

        this.apq.set(pos1, this.apq.get(pos2));
        this.apq.set(pos2, temp);

        locator.set(apq.get(pos1), pos1);
        locator.set(apq.get(pos2), pos2);
    }

    private int getParentIndex(int currNode) {
        return (int) ((currNode - 1) / 2);
    }

    private int getLeftChildIndex(int currNode) {
        return (int) (2 * currNode + 1);
    }

    private int getRightChildIndex(int currNode) {
        return (int) (2 * currNode + 2);
    }
}