package smartcomparator;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import smartcomparator.dataclasses.TestDataObject;
import smartcomparator.dataclasses.TestNamedSortsObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 28.10.13
 * Time: 00:09
 * To change this template use File | Settings | File Templates.
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
        SmartComparator sc = new SmartComparator(TestNamedSortsObject.class, "allASC");

        TestNamedSortsObject[] expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("a", 9)
        };
        before = new Date();
        Collections.sort(list, sc);
        after = new Date();
        Assert.assertArrayEquals(expectedResult, list.toArray(new TestNamedSortsObject[list.size()]));
    }

    @Test
    public void testCompare2() throws Exception {
        SmartComparator sc = new SmartComparator(TestNamedSortsObject.class, "mixed");
        before = new Date();
        Collections.sort(list, sc);
        after = new Date();
        TestNamedSortsObject[] expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        };
        Assert.assertArrayEquals(expectedResult, list.toArray(new TestNamedSortsObject[list.size()]));
    }

    @Test
    public void testChangeSorting() throws Exception {
        SmartComparator sc = new SmartComparator(TestNamedSortsObject.class, "allASC");
        Collections.sort(list, sc);
        TestNamedSortsObject[] expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("a", 9)
        };

        Assert.assertArrayEquals(expectedResult, list.toArray(new TestNamedSortsObject[list.size()]));

        sc.changeSorting("mixed");

        before = new Date();
        Collections.sort(list, sc);
        after = new Date();
        expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        };

        Assert.assertArrayEquals(expectedResult, list.toArray(new TestNamedSortsObject[list.size()]));
    }

    @Test
    public void testChangeSorting2() throws Exception {
        SmartComparator sc = new SmartComparator(TestNamedSortsObject.class,"allASC");
        Collections.sort(list, sc);
        TestNamedSortsObject[] expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("a", 9)
        };

        Assert.assertArrayEquals(expectedResult, list.toArray(new TestNamedSortsObject[list.size()]));

        sc.changeSorting("mixed");

        before = new Date();
        Collections.sort(list, sc);
        after = new Date();
        expectedResult = new TestNamedSortsObject[]{
                new TestNamedSortsObject("f", 5),
                new TestNamedSortsObject("c", 2),
                new TestNamedSortsObject("bb", 3),
                new TestNamedSortsObject("aa", 1),
                new TestNamedSortsObject("aa", 2),
                new TestNamedSortsObject("a", 9)
        };

        Assert.assertArrayEquals(expectedResult, list.toArray(new TestNamedSortsObject[list.size()]));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCompareNegative() {
        new SmartComparator(TestDataObject.class, (String)null);
        Assert.fail("no exception was thrown");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testCompareNegative2() {
        new SmartComparator(TestNamedSortsObject.class,(String) null);
        Assert.fail("no exception was thrown");
    }
}
