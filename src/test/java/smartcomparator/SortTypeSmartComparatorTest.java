package smartcomparator;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import smartcomparator.dataclasses.TestSortTypeObject;
import smartcomparator.helperclasses.SortType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 26.10.13
 * Time: 02:04
 * To change this template use File | Settings | File Templates.
 */
public class SortTypeSmartComparatorTest {

    private List<TestSortTypeObject> list;

    @BeforeMethod
    public void setUp() throws Exception {
        list = new ArrayList<TestSortTypeObject>() {{
            add(new TestSortTypeObject("bb", 3));
            add(new TestSortTypeObject("aa", 1));
            add(new TestSortTypeObject("aa", 2));
            add(new TestSortTypeObject("c", 2));
            add(new TestSortTypeObject("a", 9));
            add(new TestSortTypeObject("f", 5));
        }};

    }

    @AfterMethod
    public void tearDown() throws Exception {
        list = null;
    }

    @Test
    public void testCompare() throws Exception {
        SmartComparator<TestSortTypeObject> sc = new SmartComparator<>(TestSortTypeObject.class);
        list.sort(sc);
        TestSortTypeObject[] expectedResult = new TestSortTypeObject[]{
                new TestSortTypeObject("a", 9),
                new TestSortTypeObject("f", 5),
                new TestSortTypeObject("bb", 3),
                new TestSortTypeObject("aa", 2),
                new TestSortTypeObject("c", 2),
                new TestSortTypeObject("aa", 1)
        };
        assertThat(list, contains(expectedResult));
    }

    @Test
    public void testCompare2() throws Exception {
        SmartComparator<TestSortTypeObject> sc = new SmartComparator<>(TestSortTypeObject.class,true, new String[]{"number", "val"}, new SortType[]{SortType.DESC, SortType.ASC});
        list.sort(sc);
        TestSortTypeObject[] expectedResult = new TestSortTypeObject[]{
                new TestSortTypeObject("a", 9),
                new TestSortTypeObject("f", 5),
                new TestSortTypeObject("bb", 3),
                new TestSortTypeObject("aa", 2),
                new TestSortTypeObject("c", 2),
                new TestSortTypeObject("aa", 1)
        };

        assertThat(list, contains(expectedResult));
    }

    @Test
    public void testCompare3() throws Exception {
        SmartComparator<TestSortTypeObject> sc = new SmartComparator<>(TestSortTypeObject.class,true, new String[]{"val", "number"}, new SortType[]{SortType.DESC, SortType.ASC});
        list.sort(sc);
        TestSortTypeObject[] expectedResult = new TestSortTypeObject[]{
                new TestSortTypeObject("f", 5),
                new TestSortTypeObject("c", 2),
                new TestSortTypeObject("bb", 3),
                new TestSortTypeObject("aa", 1),
                new TestSortTypeObject("aa", 2),
                new TestSortTypeObject("a", 9)
        };

        assertThat(list, contains(expectedResult));
    }
}
