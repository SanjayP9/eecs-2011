package A4Q2;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoursesTest {

    public static Courses EECSCourses = new Courses();
    public static CourseRequisite req = new CourseRequisite(CourseRequisiteType.PREREQUISITE);
    public static CourseNumber[] courses = new CourseNumber[8];

    @Before
    public void setup() throws InvalidCourseNumberException {
        courses[0] = new CourseNumber(4491);
        courses[1] = new CourseNumber(4481);
        courses[2] = new CourseNumber(2011);
        courses[3] = new CourseNumber(3431);
        courses[4] = new CourseNumber(3221);
        courses[5] = new CourseNumber(3214);
        courses[6] = new CourseNumber(2031);
        courses[7] = new CourseNumber(2021);

        EECSCourses.putCourse(courses[0], "Fundamentals of Data Structures ");
        EECSCourses.putCourse(courses[1], "Fundamentals of Data Structures ");
        EECSCourses.putCourse(courses[2], "Fundamentals of Data Structures ");
        EECSCourses.putCourse(courses[3], "Fundamentals of Data Structures ");
        EECSCourses.putCourse(courses[4], "Fundamentals of Data Structures ");
        EECSCourses.putCourse(courses[5], "Fundamentals of Data Structures ");
        EECSCourses.putCourse(courses[6], "Fundamentals of Data Structures ");
        EECSCourses.putCourse(courses[7], "Fundamentals of Data Structures ");
    }

    public void buildReqs() throws InvalidCourseNumberException, CircularPreRequisiteException {
        EECSCourses.putRequisite(courses[0], courses[1], req);
        EECSCourses.putRequisite(courses[1], courses[2], req);
        EECSCourses.putRequisite(courses[2], courses[3], req);
        EECSCourses.putRequisite(courses[3], courses[4], req);
        EECSCourses.putRequisite(courses[4], courses[5], req);
        EECSCourses.putRequisite(courses[5], courses[6], req);
        EECSCourses.putRequisite(courses[6], courses[7], req);
    }

    @Test
    public void nullTest1() {
        assertThrows(InvalidCourseNumberException.class,
                () -> EECSCourses.putRequisite(new CourseNumber(999), new CourseNumber(2011), req));
    }

    @Test
    public void nullTest2() {
        assertThrows(InvalidCourseNumberException.class,
                () -> EECSCourses.putRequisite(new CourseNumber(2011), new CourseNumber(959595), req));
    }

    @Test
    public void baseTest1() {
        try {
            EECSCourses.putRequisite(courses[0], courses[1], req);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void baseTest2() {
        try {
            buildReqs();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void cyclicTest1() {
        assertThrows(CircularPreRequisiteException.class, () -> {
            buildReqs();
            EECSCourses.putRequisite(courses[7], courses[0], req);
        });
    }

    @Test
    public void cyclicTest2() {
        for (int i = 0; i < courses.length / 2; i++) {
            int finalI = i;
            assertThrows(CircularPreRequisiteException.class, () -> {
                buildReqs();
                EECSCourses.putRequisite(courses[courses.length - finalI - 1], courses[finalI], req);
            });
        }
    }

    @Test
    public void cyclicTest3() {
        try {
            buildReqs();
            EECSCourses.putRequisite(courses[0], courses[7], req);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void cyclicTest4() {
        assertThrows(CircularPreRequisiteException.class, () -> {
            buildReqs();
            EECSCourses.putCourse(new CourseNumber(1337), "Fundamentals of Data Structures");
            EECSCourses.putRequisite(courses[2], new CourseNumber(1337), req);
            EECSCourses.putRequisite(new CourseNumber(1337), courses[0], req);
        });
    }

    @Test
    public void cyclicTest5() {
        assertThrows(CircularPreRequisiteException.class, () -> {
            buildReqs();
            EECSCourses.putRequisite(courses[0], courses[0], req);
        });
    }

    @Test
    public void cyclicTest6() {
        assertThrows(CircularPreRequisiteException.class, () -> {
            EECSCourses.putRequisite(courses[0], courses[1], req);
            EECSCourses.putRequisite(courses[1], courses[0], req);
        });
    }
}