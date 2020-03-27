package smartcomparator;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import smartcomparator.dataclasses.TestStringObject;
import smartcomparator.helperclasses.MethodNameGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.testng.Assert.fail;


/**
 * @author fabian
 * Date: 25.10.13
 * Time: 05:35
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
        list.sort(sc);
        TestStringObject[] expected = new TestStringObject[]{
                new TestStringObject("a", 9),
                new TestStringObject("aa", 1), new TestStringObject("aa", 2), new TestStringObject("bb", 3),
                new TestStringObject("c", 2), new TestStringObject("f", 5)};
        assertThat(list, contains(expected));
    }

    @Test
    public void testCompare2() throws Exception {
        SmartComparator<TestStringObject> sc = new SmartComparator<>(TestStringObject.class, new MethodNameGenerator("getVal").getList());
        list.sort(sc);
        TestStringObject[] expected = new TestStringObject[]{
                new TestStringObject("a", 9),
                new TestStringObject("aa", 1),
                new TestStringObject("aa", 2),
                new TestStringObject("bb", 3),
                new TestStringObject("c", 2),
                new TestStringObject("f", 5)};
        assertThat(list, contains(expected));
    }

    @Test
    public void testCompareNegative() throws Exception {
        SmartComparator<TestStringObject> sc = new SmartComparator<>(TestStringObject.class, new MethodNameGenerator("getVal").getArray());
        list.sort(sc);
        TestStringObject[] expected = new TestStringObject[]{new TestStringObject("a", 9),
                new TestStringObject("aa", 2), new TestStringObject("bb", 3), new TestStringObject("aa", 1),
                new TestStringObject("c", 2), new TestStringObject("f", 5)};
        assertThat(list, not(contains(expected)));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest() {
        new SmartComparator<>(int.class);
        fail("no exception was thrown");
    }
}
