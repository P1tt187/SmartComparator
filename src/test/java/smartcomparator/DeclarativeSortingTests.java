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
    private Date before;
    private Date after;

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

    @AfterMethod
    public void tearDown() throws Exception {
        System.err.println("time: " + (after.getTime() - before.getTime()));

        list = null;
    }

    @Test
    public void testCompare() throws Exception {
        SmartComparator<TestNamedSortsObject> sc = new SmartComparator<>(TestNamedSortsObject.class, "allASC");

        List<TestNamedSortsObject> expectedResult = Arrays.asList(
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("a", 9));
        before = new Date();
        list.sort(sc);
        after = new Date();
        assertThat(list, contains(expectedResult));

    }

    @Test
    public void testCompare2() throws Exception {
        SmartComparator<TestNamedSortsObject> sc = new SmartComparator<>(TestNamedSortsObject.class, "mixed");
        before = new Date();
        list.sort(sc);
        after = new Date();
        List<TestNamedSortsObject> expectedResult = Arrays.asList(
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        );
        assertThat(list, contains(expectedResult));
    }

    @Test
    public void testChangeSorting() throws Exception {
        SmartComparator<TestNamedSortsObject> sc = new SmartComparator<>(TestNamedSortsObject.class, "allASC");
        list.sort(sc);
        List<TestNamedSortsObject> expectedResult = Arrays.asList(
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("a", 9)
        );

        assertThat(list, contains(expectedResult));

        sc.changeSorting("mixed");

        before = new Date();
        list.sort(sc);
        after = new Date();
        expectedResult = Arrays.asList(
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        );

        assertThat(list, contains(expectedResult));
    }

    @Test
    public void testChangeSorting2() throws Exception {
        SmartComparator<TestNamedSortsObject> sc = new SmartComparator<>(TestNamedSortsObject.class, "allASC");
        list.sort(sc);
        List<TestNamedSortsObject> expectedResult = Arrays.asList(
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("a", 9)
        );

        assertThat(list, contains(expectedResult));

        sc.changeSorting("mixed");

        before = new Date();
        Collections.sort(list, sc);
        after = new Date();
        expectedResult = Arrays.asList(
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        );

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
