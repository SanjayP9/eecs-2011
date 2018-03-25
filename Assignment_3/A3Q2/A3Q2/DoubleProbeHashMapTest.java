package A3Q2;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DoubleProbeHashMapTest {

    HashMap<String, MarathonRunner> hashMap = new HashMap<String, MarathonRunner>();
    DoubleProbeHashMap<String, MarathonRunner> doubleMarathonMap;
    List<String> keyList = new ArrayList<>();
    Random rand;


    private void initialiseMaps() {
        AsciiReader asciiReader;
        Object[] Format = {Integer.class, String.class, String.class, String.class, String.class, String.class};
        Object[] result;

        try {
            doubleMarathonMap = new DoubleProbeHashMap<>(7500);
        } catch (Exception e) {
            fail("Error initialising HashMaps");
            return;
        }
        MarathonRunner marathonRunner1, marathonRunner2;

        try {
            asciiReader = new AsciiReader("marathon2017.csv");
            do {
                result = asciiReader.ReadLine(Format, ",");
                if (result != null) {
                    marathonRunner1 = new MarathonRunner((Integer) result[0],
                            (String) result[1], (String) result[2],
                            (String) result[3], (String) result[4],
                            (String) result[5]
                    );
                    marathonRunner2 = marathonRunner1;
                    try {
                        String entry = (String) result[3] + " " + (String) result[4] + " " + (String) result[5];

                        keyList.add(entry);
                        marathonRunner1 = doubleMarathonMap.put(entry, marathonRunner1);
                        marathonRunner2 = hashMap.put(entry, marathonRunner2);

                        if (marathonRunner1 != null) {
                            System.out.println(marathonRunner1.toString()); //print duplicates
                            fail("rip");
                        }
                        if (marathonRunner2 != null) {
                            System.out.println(marathonRunner2.toString()); //print duplicates
                            fail("rip");
                        }
                    } catch (Exception ex) {
                        fail("Something Ripped");
                    }
                }
            } while (result != null);
        } catch (Exception x) {
            fail("File not found");
        }
    }


    @Test
    public void testAllEntries() {
        rand = new Random();
        initialiseMaps();

        String key;
        MarathonRunner expected, actual;

        while (keyList.size() != 0)
        {
            key = keyList.get(rand.nextInt(keyList.size()));

            expected = hashMap.get(key);
            actual = doubleMarathonMap.get(key);

            System.out.println("\nExpected:" + expected.toString());
            System.out.println("Actual:\t " + actual.toString());
            assertEquals(expected, actual);

            keyList.remove(key);
        }
    }
}
