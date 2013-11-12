package smartcomparator;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import smartcomparator.dataclasses.TestStringObject;
import smartcomparator.dataclasses.TestWrapper;

import java.util.*;

/**
 * @author fabian
 *         Date: 31.10.13
 *         Time: 22:13
 */
public class PerformanceTest {

    private List<TestWrapper> scList;
    private List<TestWrapper> nativeList;
    private Random generator = new Random();


    public void setUp() throws Exception {

        nativeList = new LinkedList<>();

        for (int i = 0; i < 10000; i++) {
            nativeList.add(new TestWrapper(generator.nextBoolean(), generator.nextBoolean(),
                    (char) generator.nextInt(Character.MAX_VALUE), (char) generator.nextInt(Character.MAX_VALUE), (
                    short) generator.nextInt(Short.MAX_VALUE), (short) generator.nextInt(Short.MAX_VALUE),
                    generator.nextInt(), generator.nextInt(),
                    generator.nextLong(), generator.nextLong(),
                    generator.nextFloat(), generator.nextFloat(),
                    generator.nextDouble(), generator.nextDouble()));
        }
        Collections.shuffle(nativeList);


        scList = new LinkedList<>(nativeList);

    }

    @AfterMethod
    public void tearDown() throws Exception {
        scList = null;
        nativeList = null;
    }

    @Test
    public void testNativeBool() throws Exception {
        System.out.println("native bool");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "nativeBoolean");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return Boolean.valueOf(o1.isNativeBoolean()).compareTo(o2.isNativeBoolean());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeBool")
    public void testWrapperBool() throws Exception {
        System.out.println("wrapped bool");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "wrapperBoolean");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return o1.getWrapperBoolean().compareTo(o2.getWrapperBoolean());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperBool")
    public void testNativeCharacter() throws Exception {
        System.out.println("native char");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "nativeChar");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return new Character(o1.getNativeChar()).compareTo(o2.getNativeChar());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeCharacter")
    public void testWrapperCharacter() throws Exception {
        System.out.println("wrapped char");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "wrapperChar");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return o1.getWrapperChar().compareTo(o2.getWrapperChar());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperCharacter")
    public void testNativeShort() throws Exception {
        System.out.println("native short");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "nativeShort");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return new Short(o1.getNativeShort()).compareTo(o2.getNativeShort());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeShort")
    public void testWrapperShort() throws Exception {
        System.out.println("wrapped short");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "wrapperShort");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return o1.getWrapperShort().compareTo(o2.getWrapperShort());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperShort")
    public void testNativeInt() throws Exception {
        System.out.println("native int");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "nativeInt");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return new Integer(o1.getNativeInt()).compareTo(o2.getNativeInt());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeInt")
    public void testWrapperInt() throws Exception {
        System.out.println("wrapped int");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "wrapperInt");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return o1.getWrapperInt().compareTo(o2.getWrapperInt());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperInt")
    public void testNativeLong() throws Exception {
        System.out.println("native Long");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "nativeLong");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return new Long(o1.getNativeLong()).compareTo(o2.getNativeLong());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeLong")
    public void testWrapperLong() throws Exception {
        System.out.println("wrapped Long");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "wrapperLong");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return o1.getWrapperLong().compareTo(o2.getWrapperLong());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperLong")
    public void testNativeFloat() throws Exception {
        System.out.println("native Float");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "nativeFloat");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return new Float(o1.getNativeFloat()).compareTo(o2.getNativeFloat());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeFloat")
    public void testWrapperFloat() throws Exception {
        System.out.println("wrapped Float");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "wrapperFloat");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return o1.getWrapperFloat().compareTo(o2.getWrapperFloat());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperFloat")
    public void testNativeDouble() throws Exception {
        System.out.println("native Double");


        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator<>(TestWrapper.class, "nativeDouble");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return new Double(o1.getNativeDouble()).compareTo(o2.getNativeDouble());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeDouble")
    public void testWrapperDouble() throws Exception {
        System.out.println("wrapped Double");


        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator sc = new SmartComparator(TestWrapper.class, "wrapperDouble");

        Comparator<TestWrapper> nativeComparator = new Comparator<TestWrapper>() {
            @Override
            public int compare(TestWrapper o1, TestWrapper o2) {
                return o1.getWrapperDouble().compareTo(o2.getWrapperDouble());
            }
        };

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            Date before = new Date();
            Collections.sort(scList, sc);
            meanSc += new Date().getTime() - before.getTime();

            before = new Date();
            Collections.sort(nativeList, nativeComparator);
            meanStandard += new Date().getTime() - before.getTime();

            Assert.assertArrayEquals(scList.toArray(new TestWrapper[scList.size()]), nativeList.toArray(new TestWrapper[nativeList.size()]));
        }

        printResults(meanSc, meanStandard, numberOfRuns);
    }

    private void printResults(double meanSc, double meanStandard, double numberOfRuns) {
        System.out.println("SmartComparator: " + (meanSc / numberOfRuns) + " NativeComparator: " + (meanStandard / numberOfRuns) + " Ratio: " + (meanSc / meanStandard));
    }

    @Test(dependsOnMethods = "testWrapperDouble")
    public void testPerformance() throws Exception {

        System.out.println("testPerformance");
        double numberOfRuns = 1000;
        double meanSc = 0;
        double meanStandard = 0;

        List<TestStringObject> list;

        List<TestStringObject> list2 = null;
        for (int idx = 0; idx < numberOfRuns; idx++) {
            list = new ArrayList<>();

            for (int i = 0; i < 10000; i++) {
                list.add(new TestStringObject(UUID.randomUUID().toString(), generator.nextInt()));
            }
            list2 = new ArrayList<>(list);

            SmartComparator sc = new SmartComparator<>(TestStringObject.class);
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
                        return 0;
                    } else if (o1.getVal() != null) {
                        ret = o1.getVal().compareTo(o2.getVal());
                        if (ret != 0) {
                            return ret;
                        }
                    } else {
                        return -1;
                    }

                    ret = Integer.valueOf(o1.getVal2()).compareTo(o2.getVal2());

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

        printResults(meanSc, meanStandard, numberOfRuns);


    }
}
