package A2Q2;

import java.util.*;

/**
 * Triages patients in Emergency Ward according to medical priority and wait time.
 * Priorities are positive integers;  the highest priority is 1.
 * Normally patients are seen in priority order, however, if there are patients
 * who have waited longer than a specified time (maxWait), they are seen first,
 * in order of their arrival.
 *
 * @author elder
 */
public class PatientTriage {

    private APQ<Patient> priorityHeap; //maintain patients in priority order
    private APQ<Patient> timeHeap;  //maintain patients in order of arrival
    private Time maxWait; //maximum waiting time

    /**
     * Constructor
     *
     * @param time Maximum wait time.  Patients waiting longer than this are seen first.
     */
    public PatientTriage(Time time) {
        Comparator<Patient> priorityComparator = new PatientPriorityComparator();
        Comparator<Patient> timeComparator = new PatientTimeComparator();
        Locator<Patient> priorityLocator = new PatientPriorityLocator();
        Locator<Patient> timeLocator = new PatientTimeLocator();
        priorityHeap = new APQ<>(priorityComparator, priorityLocator);
        timeHeap = new APQ<>(timeComparator, timeLocator);
        setMaxWait(time);
    }

    /**
     * Adds patient to queues.
     *
     * @param patient to add.
     * @throws NullPointerException if given null patient
     */
    public void add(Patient patient) throws NullPointerException {
        if (patient == null) {
            throw new NullPointerException();
        }
        priorityHeap.offer(patient); //add to priority queue
        timeHeap.offer(patient); //add to arrival time queue
    }

    // Method used to remove a root from the time heap and return the Patient
    private Patient removeFromTimeHeap() throws BoundaryViolationException {
        Patient result = timeHeap.poll();
        priorityHeap.remove(result.getPriorityPos());
        return result;
    }

    // Method used to remove a root from the priority heap and return the Patient
    private Patient removeFromPriorityHeap() throws BoundaryViolationException {
        Patient result = priorityHeap.poll();
        timeHeap.remove(result.getTimePos());
        return result;
    }

    /**
     * Removes next patient in queue.
     *
     * @param currentTime used to determine whether to use priority or arrival time
     * @return Next patient to attend to
     * @throws NullPointerException       if given null time
     * @throws EmptyQueueException        if queue is empty
     * @throws BoundaryViolationException under some internal error conditions
     */
    public Patient remove(Time currentTime) throws NullPointerException, EmptyQueueException, BoundaryViolationException {
        //implement this method
        if (currentTime == null) {
            throw new NullPointerException();
        }
        if (priorityHeap.isEmpty() || timeHeap.isEmpty()) {
            throw new EmptyQueueException();
        }

        TimeComparator timeCompare = new TimeComparator();
        Time timePassed;

        // Checks if current time is more than the arrival time to avoid getting a negative elapsed time
        // Getting a negative elapsed time would throw an exception
        if (timeCompare.compare(timeHeap.peek().getArrivalTime(), currentTime) <= 0) {
            timePassed = timeHeap.peek().getArrivalTime().elapsed(currentTime);

            // Checks if Patient is over wait time
            if (timeCompare.compare(timePassed, maxWait) >= 0) {
                return removeFromTimeHeap();
            }
        }
        // If time hasn't exceeded wait time just go by the priority queue
        return removeFromPriorityHeap();
    }




    /**
     * @return maximum wait time
     */
    public Time getMaxWait() {
        return maxWait;
    }

    /**
     * Set the maximum wait time
     *
     * @param time - the maximum wait time
     * @throws NullPointerException if given null time
     */
    public void setMaxWait(Time time) throws NullPointerException {
        if (time == null) {
            throw new NullPointerException();
        }
        maxWait = time;
    }

}
