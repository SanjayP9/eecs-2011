package A2Q2;

import com.sun.java.swing.plaf.windows.TMSchema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.PriorityQueue;
import java.util.Random;

public class PatientTriageTest {

    PatientTriage patientTriage;
    PriorityQueue<Patient> testTimeQueue;
    PriorityQueue<Patient> testPriorityQueue;
    Random rand;

    @BeforeAll
    public void startup() {
        rand = new Random();
        this.testTimeQueue = new PriorityQueue<>();
        this.testPriorityQueue = new PriorityQueue<>();
    }

    private Patient testRemove(PriorityQueue<Patient> testQueue, Time currentTime, Time maxWait)
    {
        int patientTime = timeHeap.peek().getArrivalTime().getHour() * 60 + timeHeap.peek().getArrivalTime().getMinute();
        int currTime = currentTime.getHour() * 60 + currentTime.getMinute();
        Patient removed;

        if ((patientTime - currTime) > (maxWait.getHour() * 60 + maxWait.getMinute())) {
            removed = timeHeap.poll();
            priorityHeap.remove(removed.getPriorityPos());
        } else {
            removed = priorityHeap.poll();
            timeHeap.remove(removed.getPriorityPos());
        }
        return removed;
    }

    @Test
    public void randomRemoveTest() {
        try {
            this.patientTriage = new PatientTriage(new Time(rand.nextInt(5), rand.nextInt(60)));
            Patient tempPatient;

            for (int i = 1; i <= 100; i++) {
                tempPatient = new Patient(i, rand.nextInt(100) + 1, new Time(rand.nextInt(5), rand.nextInt(60)));
                this.patientTriage.add(tempPatient);
                this.testQueue.add(tempPatient);
            }

            int patientTime =0, currTime = 0;

            for (int i = 0; i < rand.nextInt(); i++)
            {

            }


        } catch (Exception e) {
            fail();
        }
    }

}
