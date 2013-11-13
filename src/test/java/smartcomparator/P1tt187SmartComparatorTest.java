package smartcomparator;


import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import smartcomparator.dataclasses.TestStringObject;
import smartcomparator.helperclasses.MethodNameGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author fabian
 *         Date: 25.10.13
 *         Time: 05:35
 */
public class P1tt187SmartComparatorTest {

    private List<TestStringObject> list;

    @BeforeMethod
    public void setUp() throws Exception {
        list = new ArrayList<TestStringObject>() {{
            add(new TestStringObject("bb", 3));
            add(new TestStringObject("aa", 1));
            add(new TestStringObject("aa", 2));
            add(new TestStringObject("c", 2));
            add(new TestStringObject("a", 9));
            add(new TestStringObject("f", 5));
        }};
    }

    @AfterMethod
    public void tearDown() throws Exception {
        list = null;
    }

    @Test
    public void testCompare() throws Exception {
        SmartComparator<TestStringObject> sc = new SmartComparator<>(TestStringObject.class);
        Collections.sort(list, sc);
        TestStringObject[] expected = new TestStringObject[]{new TestStringObject("a", 9),
                new TestStringObject("aa", 1), new TestStringObject("aa", 2), new TestStringObject("bb", 3),
                new TestStringObject("c", 2), new TestStringObject("f", 5)};

        Assert.assertArrayEquals(expected, list.toArray(new TestStringObject[list.size()]));

    }

    @Test
    public void testCompare2() throws Exception {
        SmartComparator<TestStringObject> sc = new SmartComparator<>(TestStringObject.class, new MethodNameGenerator("getVal").getList());
        Collections.sort(list, sc);
        TestStringObject[] expected = new TestStringObject[]{
                new TestStringObject("a", 9),
                new TestStringObject("aa", 1),
                new TestStringObject("aa", 2),
                new TestStringObject("bb", 3),
                new TestStringObject("c", 2),
                new TestStringObject("f", 5)};
        Assert.assertArrayEquals(expected, list.toArray(new TestStringObject[list.size()]));
    }

    @Test
    public void testCompareNegative() throws Exception {
        SmartComparator<TestStringObject> sc = new SmartComparator<>(TestStringObject.class, new MethodNameGenerator("getVal").getArray());
        Collections.sort(list, sc);
        TestStringObject[] expected = new TestStringObject[]{new TestStringObject("a", 9),
                new TestStringObject("aa", 2), new TestStringObject("bb", 3), new TestStringObject("aa", 1),
                new TestStringObject("c", 2), new TestStringObject("f", 5)};
        Assert.assertFalse(Arrays.equals(expected, list.toArray(new TestStringObject[list.size()])));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest() {
        new SmartComparator<>(int.class);
        Assert.fail("no exception was thrown");
    }


}
