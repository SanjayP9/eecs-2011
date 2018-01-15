package A1Q3;

import java.util.*;

/**
 * Specializes the stack data structure for comparable elements, and provides
 * a method for determining the maximum element on the stack in O(1) time.
 *
 * @author jameselder
 */
public class MaxStack<E extends Comparable> extends Stack<E> {

    private Stack<E> mainStack;
    private Stack<E> maxStack;

    public MaxStack() {
        mainStack = new Stack<>();
        maxStack = new Stack<>();
    }

    /* must run in O(1) time */
    public E push(E element) {
        if (mainStack.empty()) {
            maxStack.push(element);
        } else {
            if (maxStack.peek().compareTo(element) > 0) {
                maxStack.push(maxStack.peek());
            } else {
                maxStack.push(element);
            }
        }
        return mainStack.push(element);//Dummy return to satisfy compiler.  Remove once coded.
    }

    /* @exception  EmptyStackException  if this stack is empty. */
    /* must run in O(1) time */
    public synchronized E pop() {
        maxStack.pop();
        return mainStack.pop(); //Dummy return to satisfy compiler.  Remove once coded.
    }

    /* Returns the maximum value currenctly on the stack. */
    /* @exception  EmptyStackException  if this stack is empty. */
    /* must run in O(1) time */
    public synchronized E max() {
        if (maxStack.empty()) {
            throw new EmptyStackException();
        }
        return maxStack.peek(); //Dummy return to satisfy compiler.  Remove once coded.
    }
}