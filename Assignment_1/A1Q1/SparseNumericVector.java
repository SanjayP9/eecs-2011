package A1Q1;

import java.util.*;

/**
 * Represents a sparse numeric vector. Elements are comprised of a (long)
 * location index an a (double) value.  The vector is maintained in increasing
 * order of location index, which facilitates numeric operations like
 * inner products (projections).  Note that location indices can be any integer
 * from 1 to Long.MAX_VALUE.  The representation is based upon a
 * singly-linked list.
 * The following methods are supported:  iterator, getSize, getFirst,
 * add, remove, and dot, which takes the dot product of the with a second vector
 * passed as a parameter.
 *
 * @author jameselder
 */
public class SparseNumericVector implements Iterable {

    protected SparseNumericNode head = null;
    protected SparseNumericNode tail = null;
    protected long size;

    /**
     * Iterator
     */
    @Override
    public Iterator<SparseNumericElement> iterator() { //iterator
        return new SparseNumericIterator(this);
    }

    /**
     * @return number of non-zero elements in vector
     */
    public long getSize() {
        return size;
    }

    /**
     * @return the first node in the list.
     */
    public SparseNumericNode getFirst() {
        return head;
    }

    /**
     * Add the element to the vector.  It is inserted to maintain the
     * vector in increasing order of index.  If the element has zero value, or if
     * an element with the same index already exists, an UnsupportedOperationException is thrown.
     *
     * @param e element to add
     */
    public void add(SparseNumericElement e) throws UnsupportedOperationException {
        //implement this method
        if (e.getValue() == 0) {
            throw new UnsupportedOperationException();
        } else if (this.size == 0) {
            this.head = new SparseNumericNode(e, tail);
            this.tail = this.head;
            this.size++;
        } else { // size is more than 0
            if ((e.getIndex() == this.head.getElement().getIndex()) || (e.getIndex() == this.tail.getElement().getIndex())) {
                throw new UnsupportedOperationException();
            } else if (e.getIndex() < this.head.getElement().getIndex()) {
                this.head = new SparseNumericNode(e, this.head);
                this.size++;
            } else if (e.getIndex() > this.tail.getElement().getIndex()) {
                this.tail.setNext(new SparseNumericNode(e, null));
                this.tail = this.tail.getNext();
                this.size++;

            } else {

                SparseNumericNode temp = this.head;

                while (temp.getNext() != null) {
                    if (temp.getElement().getIndex() == e.getIndex()) {
                        throw new UnsupportedOperationException();
                    } else if ((temp.getNext().getElement().getIndex() > e.getIndex()) && (temp.getElement().getIndex() < e.getIndex())) {
                        temp.setNext(new SparseNumericNode(e, temp.getNext()));
                        this.size++;
                        return;
                    }
                    temp = temp.getNext();
                }
            }
        }
    }

    /**
     * If an element with the specified index exists, it is removed and the
     * method returns true.  If not, it returns false.
     *
     * @param index of element to remove
     * @return true if removed, false if does not exist
     */
    public boolean remove(Long index) {
        //implement this method
        //this return statement is here to satisfy the compiler - replace it with your code.

        if (this.size == 0) {
            return false;
        } else if (this.size == 1) {
            if (index == this.head.getElement().getIndex()) {
                this.head = null;
                this.tail = null;
                this.size--;
                return true;
            }
            return false;
        } else if (this.head.getElement().getIndex() > index || index > this.tail.getElement().getIndex()) {
            return false;
        } else if (index == this.tail.getElement().getIndex()) {
            SparseNumericNode temp = this.head;

            while (temp.getNext().getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(null);
            this.size--;
            this.tail = temp;
            return true;
        } else if (this.head.getElement().getIndex() == index) {
            this.head = this.head.getNext();
            this.size--;
            return true;
        } else {

            SparseNumericNode temp = this.head;
            SparseNumericNode previous = temp;

            while (temp.getNext() != null) {
                if (temp.getElement().getIndex() == index) {
                    previous.setNext(temp.getNext());
                    this.size--;
                    return true;
                }
                previous = temp;
                temp = temp.getNext();
            }
        }
        return false;
    }

    /**
     * Returns the inner product of the vector with a second vector passed as a
     * parameter.  The vectors are assumed to reside in the same space.
     * Runs in O(m+n) time, where m and n are the number of non-zero elements in
     * each vector.
     *
     * @param Y Second vector with which to take inner product
     * @return result of inner product
     */

    public double dot(SparseNumericVector Y) {
        //implement this method
        //this return statement is here to satisfy the compiler - replace it with your code.
        double result = 0;

        if (this.size == 0) {
            return 0.0d;
        }

        SparseNumericIterator xIterate = new SparseNumericIterator(this);
        SparseNumericIterator yIterate = new SparseNumericIterator(Y);

        while (xIterate.hasNext() && yIterate.hasNext()) {
            if (xIterate.position.getElement().getIndex() == yIterate.position.getElement().getIndex()) {
                result += xIterate.position.getElement().getValue() * yIterate.position.getElement().getValue();
                xIterate.next();
                yIterate.next();
            } else if (xIterate.position.getElement().getIndex() > yIterate.position.getElement().getIndex()) {
                yIterate.next();
            } else {
                xIterate.next();
            }
        }

        return result;
    }

    /**
     * returns string representation of sparse vector
     */

    @Override
    public String toString() {
        String sparseVectorString = "";
        Iterator<SparseNumericElement> it = iterator();
        SparseNumericElement x;
        while (it.hasNext()) {
            x = it.next();
            sparseVectorString += "(index " + x.getIndex() + ", value " + x.getValue() + ")\n";
        }
        return sparseVectorString;
    }
}
