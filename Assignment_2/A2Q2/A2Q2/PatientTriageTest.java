package A2Q2;

import org.junit.Test;

import java.util.PriorityQueue;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PatientTriageTest {

    @Test
    public void boundsTest1() {
        assertThrows(EmptyQueueException.class, () -> {
                    // Try removing an empty triage
                    PatientTriage triage = new PatientTriage(new Time(1, 0));
                    triage.remove(new Time(1, 0));
                }
        );
    }

    @Test
    public void boundsTest2() {
        assertThrows(NullPointerException.class, () -> {
                    // Try null time
                    PatientTriage triage = new PatientTriage(new Time(1, 0));
                    triage.remove(null);
                }
        );
    }

    @Test
    public void randomizedPatientTest() {
        int[] passes = new int[100];
        for (int repeat = 0; repeat < 100; repeat++) {
            try {
                Random rand = new Random(repeat);
                int numOfPatients = rand.nextInt(20) + 1;
                PriorityQueue<Patient> patientPriorityQ =
                        new PriorityQueue<>(numOfPatients, new PatientPriorityComparator());
                PriorityQueue<Patient> patientTimeQ =
                        new PriorityQueue<>(numOfPatients, new PatientTimeComparator());

                Time maxWait = new Time(
                        rand.nextInt(24),
                        rand.nextInt(60)
                );

                PatientTriage triage = new PatientTriage(maxWait);
                for (int i = 0; i < numOfPatients; i++) {
                    Patient patient = new Patient(
                            i + 1,
                            rand.nextInt(10) + 1,
                            new Time(rand.nextInt(24), rand.nextInt(60))
                    );

                    Patient expected = new Patient(
                            i + 1,
                            patient.getPriority(),
                            new Time(patient.getArrivalTime().hour,
                                    patient.getArrivalTime().minute)
                    );

                    triage.add(patient);
                    patientPriorityQ.add(expected);
                    patientTimeQ.add(expected);
                }

                System.out.println(numOfPatients + " Patients in queue | Seed: " + repeat);

                TimeComparator timeComparator = new TimeComparator();
                for (int i = 0; i < numOfPatients; i++) {
                    System.out.println("-------------------------------------------------------\n" +
                            "Processing " + (i + 1) + "/" + numOfPatients);
                    Time current = new Time(rand.nextInt(24), rand.nextInt(60));

                    Patient value = null;
                    try {
                        value = triage.remove(current);
                    } catch (BoundaryViolationException e) {
                        fail("PatientTriage tried to generate a time instance with negative numbers");
                    } catch (IndexOutOfBoundsException e) {
                        fail("APQ tried to access an index not inside the array");
                    } catch (NullPointerException e) {
                        fail("PatientTriage threw NPE when time is not null");
                    } catch (EmptyQueueException e) {
                        fail("PatientTriage shouldn't be empty but it threw anyway");
                    } catch (Exception e) {
                        fail("How did this even happen " + e);
                    }

                    System.out.println("\n==== EXPECTED ====");
                    System.out.println("Priority: " + patientPriorityQ.peek() + "\nTime: " + patientTimeQ.peek());
                    Patient expected;
                    if (timeComparator.compare(patientTimeQ.peek().getArrivalTime(), current) <= 0) {
                        System.out.println("Last Patient Arrived at: " + patientTimeQ.peek().getArrivalTime() + "\n" +
                                "Current Time: " + current);

                        Time expectedElapsed = patientTimeQ.peek().getArrivalTime().elapsed(current);

                        System.out.println("Elapsed Time: " + expectedElapsed + " | Max Wait Time: " + maxWait);
                        if (timeComparator.compare(expectedElapsed, maxWait) >= 0) {
                            System.out.println("Longest Waiter Priority\nPolling: " + patientTimeQ.peek());
                            expected = patientTimeQ.poll();
                            patientPriorityQ.remove(expected);
                        } else {
                            System.out.println("Condition Takes Priority\nPolling: " + patientPriorityQ.peek());
                            expected = patientPriorityQ.poll();
                            patientTimeQ.remove(expected);
                        }
                    } else {
                        System.out.println("Time: " + current + " | " + "Earliest Patient: " +
                                patientTimeQ.peek().getArrivalTime());
                        System.out.println("- Patient has not arrived yet -\nPolling: " + patientPriorityQ.peek());
                        expected = patientPriorityQ.poll();
                        patientTimeQ.remove(expected);
                    }

                    if (value.getID() != expected.getID()) {
                        if (value.getPriority() == expected.getPriority()) {
                            System.out.println("Patient ID Mismatch but priorities are equal");
                            passes[repeat] = 0;
                        } else {
                            System.out.println("\n--FAILED--\nExpecting: " + expected + "\n" + "Value: " + value + "\n--------------");
                            passes[repeat] = -1;
                            break;
                        }
                    } else {
                        passes[repeat] = 1;
                        System.out.println("-------------------------------------------------------\n");
                        System.out.print("\033[H\033[2J");
                    }

                    //assertTrue(value.equals(expected));
                }
                System.out.println("-------------------------------------------------------");

            } catch (Exception e) {
                fail("The test failed, this shouldn't happen");
            }
        }

        int fails = 0, partialFails = 0;
        System.out.print("Seed [i] = fail | (i) = ID mismatch: ");
        for (int i = 0; i < passes.length; i++) {
            if (i % 25 == 0) {
                System.out.println();
            }
            if (passes[i] == -1) {
                System.out.print("[" + i + "]" + ", ");
            } else if (passes[i] == 0) {
                System.out.print("(" + i + ")" + ", ");
            }
            fails += passes[i] == -1 ? 1 : 0;
            partialFails += passes[i] == 0 ? 1 : 0;
        }
        System.out.println();
        System.out.println(fails + " Seeds Failed, " + partialFails + " Seeds where priorities matched but IDs didn't");
        if (fails > 0) {
            fail("Unit Test failed more than once with " + fails + " failures");
        }
    }

    @Test
    public void hardTest() {
        try {
            // Priority: 5 4 6 8 3 2 7 1 9
            // Time:     1 2 3 4 5 6 7 8 9
            Patient patient;
            Patient[] patients = new Patient[]{
                    new Patient(1, 5, new Time(0, 30)),
                    new Patient(2, 4, new Time(1, 45)),
                    new Patient(3, 3, new Time(2, 0)),
                    new Patient(4, 2, new Time(3, 15)),
                    new Patient(5, 1, new Time(1, 45)),
                    new Patient(6, 2, new Time(4, 45)),
                    new Patient(7, 3, new Time(5, 30)),
                    new Patient(8, 4, new Time(6, 45)),
                    new Patient(9, 5, new Time(7, 45))
            };

            PatientTriage patientTriage = new PatientTriage(new Time(5, 0)); //Time limit of 3 hours

            for (int i = 0; i < patients.length; i++) {
                patientTriage.add(patients[i]);
            }

            patient = patientTriage.remove(new Time(3, 0));
            System.out.print("Now seeing: " + patient); //Should be patient 5
            assertEquals(patient, patients[4]);
            patient = patientTriage.remove(new Time(4, 15));
            System.out.print("Now seeing: " + patient); //Should be patient 4
            assertEquals(patient, patients[3]);
            patient = patientTriage.remove(new Time(5, 30));
            System.out.print("Now seeing: " + patient); //Should be patient 1
            assertEquals(patient, patients[0]);
            patient = patientTriage.remove(new Time(5, 35));
            System.out.print("Now seeing: " + patient); //Should be patient 6
            assertEquals(patient, patients[5]);
            patient = patientTriage.remove(new Time(6, 0));
            System.out.print("Now seeing: " + patient); //Should be patient 3
            assertEquals(patient, patients[2]);
            patient = patientTriage.remove(new Time(6, 30));
            System.out.print("Now seeing: " + patient); //Should be patient 7
            assertEquals(patient, patients[6]);
            patient = patientTriage.remove(new Time(10, 45));
            System.out.print("Now seeing: " + patient); //Should be patient 2
            assertEquals(patient, patients[1]);
            patient = patientTriage.remove(new Time(21, 30));
            System.out.print("Now seeing: " + patient); //Should be patient 8
            assertEquals(patient, patients[7]);
            patient = patientTriage.remove(new Time(23, 30));
            System.out.print("Now seeing: " + patient); //Should be patient 9
            assertEquals(patient, patients[8]);
        } catch (BoundaryViolationException e) {
            fail("PatientTriage tried to generate a time instance with negative numbers");
        } catch (IndexOutOfBoundsException e) {
            fail("APQ tried to access an index not inside the array");
        } catch (NullPointerException e) {
            fail("PatientTriage threw NPE when time is not null");
        } catch (EmptyQueueException e) {
            fail("PatientTriage shouldn't be empty but it threw anyway");
        } catch (Exception e) {
            fail("How did this even happen " + e);
        }
    }
}