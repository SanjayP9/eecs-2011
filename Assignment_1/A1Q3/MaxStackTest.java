package A1Q3;

import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.fail;

public class MaxStackTest {

    @Test
    public void maxTest() {

        LinkedList<Integer> numbers = new LinkedList<>();
        MaxStack<Integer> maxStack = new MaxStack<>();

        for (int i = 0; i < 100; i ++) {
            numbers.add(ThreadLocalRandom.current().nextInt(1000));
        }

        LinkedList<Integer> maxNumbers = new LinkedList<>();
        for (int i = 0; i < numbers.size(); i++) {
            maxStack.push(numbers.get(i));
            maxNumbers.add(numbers.get(i));
            if (i % 10 == 0) {
                if (!maxStack.max().equals(Collections.max(maxNumbers))) {
                    fail("Expected: " + Collections.max(maxNumbers) + " | Actual: " + maxStack.max());
                }
            }
        }

        for (int i = 1; i < 100; i++) {
            numbers.remove(maxStack.pop());

            if (!maxStack.max().equals(Collections.max(numbers))) {
                fail();
            }
        }
    }
}