package smartcomparator;


import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import smartcomparator.dataclasses.TestStringObject;
import smartcomparator.helperclasses.MethodNameGenerator;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 25.10.13
 * Time: 05:35
 * To change this template use File | Settings | File Templates.
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
        SmartComparator<TestStringObject> sc = new SmartComparator(TestStringObject.class);
        Collections.sort(list, sc);
        TestStringObject[] expected = new TestStringObject[]{new TestStringObject("a", 9),
                new TestStringObject("aa", 1), new TestStringObject("aa", 2), new TestStringObject("bb", 3),
                new TestStringObject("c", 2), new TestStringObject("f", 5)};

        System.out.println(list);
        Assert.assertArrayEquals(expected, list.toArray(new TestStringObject[list.size()]));

    }

    @Test
    public void testCompare2() throws Exception {
        SmartComparator<TestStringObject> sc = new SmartComparator<>(TestStringObject.class,new MethodNameGenerator("getVal").getList());
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
        SmartComparator<TestStringObject> sc = new SmartComparator<>(TestStringObject.class,new MethodNameGenerator("getVal").getArray());
        Collections.sort(list, sc);
        TestStringObject[] expected = new TestStringObject[]{new TestStringObject("a", 9),
                new TestStringObject("aa", 2), new TestStringObject("bb", 3), new TestStringObject("aa", 1),
                new TestStringObject("c", 2), new TestStringObject("f", 5)};
        Assert.assertFalse(Arrays.equals(expected, list.toArray(new TestStringObject[list.size()])));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void exeptionTest() {
        new SmartComparator(int.class);
        Assert.fail("no exception was thrown");
    }

    /*
    @Test
    public void testPerformance() throws Exception {

        double numberOfRuns = 1000;
        double meanSc = 0;
        double meanStandard = 0;
        List<TestStringObject> list2 = null;
        for (int idx = 0; idx < numberOfRuns; idx++) {
            list = new ArrayList<>();

            for (int i = 0; i < 10000; i++) {
                list.add(new TestStringObject(UUID.randomUUID().toString(), (int) ((Math.random() * 1000) % 1000)));
            }
            list2 = new ArrayList<>(list);

            SmartComparator sc = new SmartComparator(TestStringObject.class);
            Date beforeSc = new Date();
            Collections.sort(list, sc);
            Date afterSc = new Date();

            Comparator<TestStringObject> standardComparator = new Comparator<TestStringObject>() {
                @Override
                public int compare(TestStringObject o1, TestStringObject o2) {

                    int ret = -0;
                    if (o1 == null && o2 == null) {
                        return ret;
                    }

                    if (o1.getVal() == null && o1.getVal() == o2.getVal()) {
                        ret = 0;
                    } else if (o1.getVal() != null) {
                        ret = o1.getVal().compareTo(o2.getVal());
                        if (ret != 0) {
                            return ret;
                        }
                    } else {
                        return -1;
                    }

                    ret = new Integer(o1.getVal2()).compareTo(o2.getVal2());

                    return ret;  //To change body of implemented methods use File | Settings | File Templates.
                }
            };

            Date beforeStandard = new Date();
            Collections.sort(list2, standardComparator);
            Date afterStandard = new Date();

            meanSc += afterSc.getTime() - beforeSc.getTime();
            meanStandard += afterStandard.getTime() - beforeStandard.getTime();
            Assert.assertArrayEquals(list.toArray(new TestStringObject[list.size()]), list2.toArray(new TestStringObject[list2.size()]));
        }

        meanSc = meanSc / numberOfRuns;
        meanStandard = meanStandard / numberOfRuns;

        System.out.println("meanSc: " + meanSc + " meanStandard: " + meanStandard);


    }   */
}
