package smartcomparator;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import smartcomparator.dataclasses.TestDataObject;
import smartcomparator.helperclasses.MethodNameGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 25.10.13
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class NathenielsFreakSmartComparatorTest {

    private List<TestDataObject> list;

    @BeforeMethod
    public void setUp() throws Exception {
        list = new ArrayList<TestDataObject>() {{
            add(new TestDataObject() {{
                setVal1(3);
                setVal2(4);
            }});
            add(new TestDataObject() {{
                setVal1(5);
                setVal2(5);
            }});
            add(new TestDataObject() {{
                setVal1(3);
                setVal2(3);
            }});
            add(new TestDataObject() {{
                setVal1(15);
            }});
            add(new TestDataObject() {{
                setVal1(9);
            }});
        }};
    }

    @AfterMethod
    public void tearDown() throws Exception {
        list = null;
    }

    @Test
    public void testCreateStringArray() throws Exception {
        SmartComparator<TestDataObject> sc = new SmartComparator<>(TestDataObject.class, new String[]{"getVal1", "getVal2"});
        TestDataObject[] expectedResult = new TestDataObject[]{
                new TestDataObject() {{
                    setVal1(3);
                    setVal2(3);
                }},
                new TestDataObject() {{
                    setVal1(3);
                    setVal2(4);
                }},
                new TestDataObject() {{
                    setVal1(5);
                    setVal2(5);
                }},
                new TestDataObject() {{
                    setVal1(9);
                }},
                new TestDataObject() {{
                    setVal1(15);
                }}
        };
        list.sort(sc);
        boolean equal = true;
        for (int i = 0; i < list.size(); i++) {
            equal = list.get(i).hashCode() == expectedResult[i].hashCode();
            if (!equal) {
                break;
            }
        }
        assertThat(equal, is(true));
    }

    @Test
    public void testCreateMethodNameRecordArray() throws Exception {
        SmartComparator<TestDataObject> sc = new SmartComparator<>(TestDataObject.class,
                new MethodNameRecord[]{new MethodNameRecord("getVal1"), new MethodNameRecord("getVal2")});
        TestDataObject[] expectedResult = new TestDataObject[]{
                new TestDataObject() {{
                    setVal1(3);
                    setVal2(3);
                }},
                new TestDataObject() {{
                    setVal1(3);
                    setVal2(4);
                }},
                new TestDataObject() {{
                    setVal1(5);
                    setVal2(5);
                }},
                new TestDataObject() {{
                    setVal1(9);
                }},
                new TestDataObject() {{
                    setVal1(15);
                }}
        };
        list.sort(sc);
        boolean equal = true;
        for (int i = 0; i < list.size(); i++) {
            equal = list.get(i).hashCode() == expectedResult[i].hashCode();
            if (!equal) {
                break;
            }
        }
        assertThat(equal, is(true));
    }

    @Test
    public void testCompare() throws Exception {
        SmartComparator<TestDataObject> sc = new SmartComparator<>(TestDataObject.class, new MethodNameGenerator("getVal1").add("getVal2").getList());
        TestDataObject[] expectedResult = new TestDataObject[]{
                new TestDataObject() {{
                    setVal1(3);
                    setVal2(3);
                }},
                new TestDataObject() {{
                    setVal1(3);
                    setVal2(4);
                }},
                new TestDataObject() {{
                    setVal1(5);
                    setVal2(5);
                }},
                new TestDataObject() {{
                    setVal1(9);
                }},
                new TestDataObject() {{
                    setVal1(15);
                }}
        };
        list.sort(sc);
        boolean equal = true;
        for (int i = 0; i < list.size(); i++) {
            equal = list.get(i).hashCode() == expectedResult[i].hashCode();
            if (!equal) {
                break;
            }
        }
        assertThat(equal, is(true));
    }

    @Test
    public void testCompare2() throws Exception {
        SmartComparator<TestDataObject> sc = new SmartComparator<>(TestDataObject.class, true, new MethodNameGenerator("Val1").add("val2").getList());
        TestDataObject[] expectedResult = new TestDataObject[]{
                new TestDataObject() {{
                    setVal1(3);
                    setVal2(3);
                }},
                new TestDataObject() {{
                    setVal1(3);
                    setVal2(4);
                }},
                new TestDataObject() {{
                    setVal1(5);
                    setVal2(5);
                }},
                new TestDataObject() {{
                    setVal1(9);
                }},
                new TestDataObject() {{
                    setVal1(15);
                }}
        };
        list.sort(sc);
        boolean equal = true;
        for (int i = 0; i < list.size(); i++) {
            equal = list.get(i).hashCode() == expectedResult[i].hashCode();
            if (!equal) {
                break;
            }
        }
        assertThat(equal, is(true));
    }

    @Test
    public void testCompareNegative() throws Exception {
        SmartComparator<TestDataObject> sc = new SmartComparator<>(TestDataObject.class, true, new MethodNameGenerator("val1", "val2").getList());
        TestDataObject[] expectedResult = list.toArray(new TestDataObject[list.size()]);

        list.sort(sc);

        boolean equal = true;
        for (int i = 0; i < list.size(); i++) {
            equal = list.get(i).hashCode() == expectedResult[i].hashCode();
            if (!equal) {
                break;
            }
        }
        assertThat(equal, is(false));
    }
}
