package smartcomparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import smartcomparator.dataclasses.TestDataObject;
import smartcomparator.dataclasses.TestNamedSortsObject;
import smartcomparator.performance.PerformanceExecutor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.testng.Assert.fail;

/**
 * @author Fabian Markert
 * Date: 28.10.13
 * Time: 00:09
 */
public class DeclarativeSortingTests {

    private static Logger logger = LoggerFactory.getLogger(DeclarativeSortingTests.class);

    private List<TestNamedSortsObject> list;

    @BeforeMethod
    public void setUp() throws Exception {
        list = new ArrayList<TestNamedSortsObject>() {{
            add(new TestNamedSortsObject("bb", 3));
            add(new TestNamedSortsObject("aa", 1));
            add(new TestNamedSortsObject("aa", 2));
            add(new TestNamedSortsObject("c", 2));
            add(new TestNamedSortsObject("a", 9));
            add(new TestNamedSortsObject("f", 5));
        }};
    }


    public void displayTime(final long time) throws Exception {
        logger.info("time: " + time);
    }

    @Test
    public void testCompare() throws Exception {
        final SmartComparator<TestNamedSortsObject> sc = new SmartComparator<>(TestNamedSortsObject.class, "allASC");

        TestNamedSortsObject[] expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("a", 9)
        };

        displayTime(new PerformanceExecutor(()-> {
            list.sort(sc);
        }).calculateDuration());
        assertThat(list, contains(expectedResult));
    }

    @Test
    public void testCompare2() throws Exception {
        final SmartComparator<TestNamedSortsObject> sc = new SmartComparator<>(TestNamedSortsObject.class, "mixed");

        TestNamedSortsObject[] expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        };

        displayTime(new PerformanceExecutor(()-> {
            list.sort(sc);
        }).calculateDuration());
        assertThat(list, contains(expectedResult));
    }

    @Test
    public void testChangeSorting() throws Exception {
        SmartComparator<TestNamedSortsObject> sc = new SmartComparator<>(TestNamedSortsObject.class, "allASC");
        list.sort(sc);
        TestNamedSortsObject[] expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("a", 9)
        };

        assertThat(list, contains(expectedResult));

        sc.changeSorting("mixed");

        displayTime(new PerformanceExecutor(()-> {
            list.sort(sc);
        }).calculateDuration());
        expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        };

        assertThat(list, contains(expectedResult));
    }

    @Test
    public void testChangeSorting2() throws Exception {
        SmartComparator<TestNamedSortsObject> sc = new SmartComparator<>(TestNamedSortsObject.class, "allASC");
        list.sort(sc);
        TestNamedSortsObject[] expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("a", 9)
        };

        assertThat(list, contains(expectedResult));

        sc.changeSorting("mixed");
        expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        };
        displayTime(new PerformanceExecutor(()-> {
            list.sort(sc);
        }).calculateDuration());
        assertThat(list, contains(expectedResult));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCompareNegative() {
        new SmartComparator<>(TestDataObject.class, (String) null);
        fail("no exception was thrown");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testCompareNegative2() {
        new SmartComparator<>(TestNamedSortsObject.class, (String) null);
        fail("no exception was thrown");
    }
}
