package smartcomparator;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import smartcomparator.dataclasses.TestDataObject;
import smartcomparator.dataclasses.TestNamedSortsObject;

import java.util.*;

/**
 * @author Fabian Markert
 * Date: 28.10.13
 * Time: 00:09
 */
public class DeclarativeSortingTests {
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


    public void tearDown(final Date before, final Date after) throws Exception {
        System.err.println("time: " + (after.getTime() - before.getTime()));

        list = null;
    }

    @Test
    public void testCompare() throws Exception {
        SmartComparator<TestNamedSortsObject> sc = new SmartComparator<>(TestNamedSortsObject.class, "allASC");

        TestNamedSortsObject[] expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("a", 9)
        };
        Date before = new Date();
        list.sort(sc);
        Date after = new Date();
        assertThat(list, contains(expectedResult));
        tearDown(before, after);
    }

    @Test
    public void testCompare2() throws Exception {
        SmartComparator<TestNamedSortsObject> sc = new SmartComparator<>(TestNamedSortsObject.class, "mixed");
        Date before = new Date();
        list.sort(sc);
        Date after = new Date();
        TestNamedSortsObject[] expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        };
        assertThat(list, contains(expectedResult));
        tearDown(before, after);
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

        Date before = new Date();
        list.sort(sc);
        Date after = new Date();
        expectedResult = expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        };

        assertThat(list, contains(expectedResult));
        tearDown(before, after);
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

        Date before = new Date();
        list.sort(sc);
        Date after = new Date();
        expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        };

        assertThat(list, contains(expectedResult));
        tearDown(before, after);
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
