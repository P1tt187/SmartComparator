package smartcomparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import smartcomparator.dataclasses.TestStringObject;
import smartcomparator.dataclasses.TestWrapper;
import smartcomparator.performance.PerformanceExecutor;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author fabian
 * Date: 31.10.13
 * Time: 22:13
 */
public class PerformanceTest {

    private static final double numberOfMethods;

    static {

        int count = 0;
        for (Method method : PerformanceTest.class.getMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                count++;
            }
        }

        numberOfMethods = count;
    }

    private static Logger logger = LoggerFactory.getLogger(PerformanceTest.class);

    private List<TestWrapper> scList;
    private List<TestWrapper> nativeList;
    private Random generator = new Random();
    private DecimalFormat formatter;
    private StringBuffer bigTableBuffer = new StringBuffer();
    private StringBuffer smallTableBuffer = new StringBuffer();
    private double totalNative = 0;
    private double totalSC = 0;
    private double totalCounter = 0;
    private double smallCounter = 0;
    private double totalPrimitive = 0;
    private double totalWrapped = 0;
    /**
     * used for small table creation, ratio cell
     */

    private double progress = 0;
    private double ratioBuffer = 0;

    @BeforeClass
    public void tableHeader() {

        formatter = new DecimalFormat("#00.00");
        String line = "| **Typ**            | **Nativ [ms]** | **SmartComparator [ms]** | **Ratio [SC/Nativ]** |\n" +
                "|:-------------------|:--------------:|:------------------------:|:--------------------:|\n";
        bigTableBuffer.append(line);

        line = "| **Typ**    | **Nativ [ms]** | **Wrapped [ms]** | **Ratio [Wrapped/Nativ]** |\n" +
                "|:-----------|:--------------:|:----------------:|:-------------------------:|\n";
        smallTableBuffer.append(line);
    }

    private void appendSmallTable(String line) {
        appendSmallTable(false, line);
    }

    private void appendSmallTable(boolean newLine, String line) {
        smallTableBuffer.append(line);
        smallTableBuffer.append("   \t| ");
        if (newLine)
            smallTableBuffer.append("\n");
    }

    private void addPrimitive(double value) {
        totalPrimitive += value;
        ratioBuffer = value;
        smallCounter++;
        appendSmallTable(reformatResult(value));
    }

    private void addWrapped(double value) {
        totalWrapped += value;
        appendSmallTable(reformatResult(value));
        appendSmallTable(true, reformatResult(value / ratioBuffer));
    }

    private void setUp() throws Exception {

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

    private void printFirstTableCell(String entry) {
        StringBuilder line = new StringBuilder("| " + entry);
        for (int i = line.length(); i < 21; i++) {
            line.append(" ");
        }
        line.append("| ");
        logger.info(line.toString());
        bigTableBuffer.append(line);

    }

    @AfterMethod
    public void tearDown() throws Exception {
        scList = null;
        nativeList = null;
        logger.info(String.format("%2.0f", ++progress * 100 / numberOfMethods) + "% |");
    }

    @Test
    public void testNativeBool() throws Exception {
        printFirstTableCell(" native bool ");
        appendSmallTable("| bool");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "nativeBoolean");

        Comparator<TestWrapper> nativeComparator = (o1, o2) -> Boolean.compare(o1.isNativeBoolean(), o2.isNativeBoolean());

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();
            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }
        addPrimitive(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeBool")
    public void testWrapperBool() throws Exception {
        printFirstTableCell("wrapped bool");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "wrapperBoolean");

        Comparator<TestWrapper> nativeComparator = Comparator.comparing(TestWrapper::getWrapperBoolean);

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();
            assertThat(scList, equalTo(nativeList));
        }

        addWrapped(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperBool")
    public void testNativeCharacter() throws Exception {
        printFirstTableCell("native char");
        appendSmallTable("| char");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "nativeChar");

        Comparator<TestWrapper> nativeComparator = Comparator.comparingInt(TestWrapper::getNativeChar);

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();
            assertThat(scList, equalTo(nativeList));
        }

        addPrimitive(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeCharacter")
    public void testWrapperCharacter() throws Exception {
        printFirstTableCell("wrapped char");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "wrapperChar");

        Comparator<TestWrapper> nativeComparator = Comparator.comparing(TestWrapper::getWrapperChar);

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }

        addWrapped(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperCharacter")
    public void testNativeShort() throws Exception {
        printFirstTableCell("native short");
        appendSmallTable("| short");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "nativeShort");

        Comparator<TestWrapper> nativeComparator = (o1, o2) -> Short.compare(o1.getNativeShort(), o2.getNativeShort());

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }
        addPrimitive(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeShort")
    public void testWrapperShort() throws Exception {
        printFirstTableCell("wrapped short");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "wrapperShort");

        Comparator<TestWrapper> nativeComparator = Comparator.comparing(TestWrapper::getWrapperShort);

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }
        addWrapped(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperShort")
    public void testNativeInt() throws Exception {
        printFirstTableCell("native int");
        appendSmallTable("| int");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "nativeInt");

        Comparator<TestWrapper> nativeComparator = Comparator.comparingInt(TestWrapper::getNativeInt);

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }
        addPrimitive(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeInt")
    public void testWrapperInt() throws Exception {
        printFirstTableCell("wrapped int");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "wrapperInt");

        Comparator<TestWrapper> nativeComparator = Comparator.comparing(TestWrapper::getWrapperInt);

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }
        addWrapped(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperInt")
    public void testNativeLong() throws Exception {
        printFirstTableCell("native Long");
        appendSmallTable("| long");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "nativeLong");

        Comparator<TestWrapper> nativeComparator = Comparator.comparingLong(TestWrapper::getNativeLong);

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }
        addPrimitive(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeLong")
    public void testWrapperLong() throws Exception {
        printFirstTableCell("wrapped Long");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "wrapperLong");

        Comparator<TestWrapper> nativeComparator = Comparator.comparing(TestWrapper::getWrapperLong);

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }
        addWrapped(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperLong")
    public void testNativeFloat() throws Exception {
        printFirstTableCell("native Float");
        appendSmallTable("| float");
        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "nativeFloat");

        Comparator<TestWrapper> nativeComparator = (o1, o2) -> Float.compare(o1.getNativeFloat(), o2.getNativeFloat());

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }
        addPrimitive(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeFloat")
    public void testWrapperFloat() throws Exception {
        printFirstTableCell("wrapped Float");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "wrapperFloat");

        Comparator<TestWrapper> nativeComparator = Comparator.comparing(TestWrapper::getWrapperFloat);

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }
        addWrapped(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testWrapperFloat")
    public void testNativeDouble() throws Exception {
        printFirstTableCell("native Double");
        appendSmallTable("| double");

        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "nativeDouble");

        Comparator<TestWrapper> nativeComparator = Comparator.comparingDouble(TestWrapper::getNativeDouble);

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }

        addPrimitive(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    @Test(dependsOnMethods = "testNativeDouble")
    public void testWrapperDouble() throws Exception {
        printFirstTableCell("wrapped Double");


        double meanSc = 0;
        double meanStandard = 0;

        double numberOfRuns = 100;

        SmartComparator<TestWrapper> sc = new SmartComparator<>(TestWrapper.class, "wrapperDouble");

        Comparator<TestWrapper> nativeComparator = Comparator.comparing(TestWrapper::getWrapperDouble);

        for (int i = 0; i < numberOfRuns; i++) {

            setUp();

            meanSc += new PerformanceExecutor(() -> {
                scList.sort(sc);
            }).calculateDuration();
            meanStandard += new PerformanceExecutor(() -> {
                nativeList.sort(nativeComparator);
            }).calculateDuration();

            assertThat(scList, equalTo(nativeList));
        }
        addWrapped(meanSc / numberOfRuns);
        printResults(meanSc, meanStandard, numberOfRuns);
    }

    private String reformatResult(double value) {
        return formatter.format(value);
    }

    private void printResults(double meanSc, double meanStandard, double numberOfRuns) {
        totalCounter++;
        totalSC += meanSc / numberOfRuns;
        totalNative += meanStandard / numberOfRuns;
        String line = " " + reformatResult(meanStandard / numberOfRuns) + " \t| " + reformatResult(meanSc / numberOfRuns) + " \t| " + reformatResult(meanSc / meanStandard) + " \t|\n";
        bigTableBuffer.append(line);

    }

    @Test(dependsOnMethods = "testWrapperDouble")
    public void testPerformance() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("| testPerformance |");
        double numberOfRuns = 100;
        double meanSc = 0;
        double meanStandard = 0;


        for (int idx = 0; idx < numberOfRuns; idx++) {
            final List<TestStringObject> list = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                list.add(new TestStringObject(UUID.randomUUID().toString(), generator.nextInt()));
            }
            final List<TestStringObject> list2 = new ArrayList<>(list);
            final SmartComparator<TestStringObject> sc = new SmartComparator<>(TestStringObject.class);
            meanSc += new PerformanceExecutor(() -> list.sort(sc)).calculateDuration();

            Comparator<TestStringObject> standardComparator = (o1, o2) -> {

                int ret = -0;
                if (o1 == null && o2 == null) {
                    return ret;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }

                if (Objects.equals(o1.getVal(), o2.getVal())) {
                    return 0;
                } else if (o1.getVal() != null) {
                    ret = o1.getVal().compareTo(o2.getVal());
                    if (ret != 0) {
                        return ret;
                    }
                } else {
                    return -1;
                }

                ret = Integer.compare(o1.getVal2(), o2.getVal2());

                return ret;
            };


            meanStandard += new PerformanceExecutor(() -> {
                list2.sort(standardComparator);
            }).calculateDuration();

            assertThat(list, equalTo(list2));
        }

        sb.append(" ").append(reformatResult(meanStandard / numberOfRuns)).append(" | ").append(reformatResult(meanSc / numberOfRuns)).append(" | ").append(reformatResult(meanSc / meanStandard)).append(" | ");
        logger.info(sb.toString());

    }

    @AfterClass
    public void printResults() {

        bigTableBuffer.append("| total              |  ").append(reformatResult(totalNative / totalCounter)).append(" \t| ").append(reformatResult(totalSC / totalCounter)).append(" \t| ").append(reformatResult(totalSC / totalNative)).append(" \t| ");
        smallTableBuffer.append("| total    \t| ").append(reformatResult(totalPrimitive / smallCounter)).append(" \t| ").append(reformatResult(totalWrapped / smallCounter)).append(" \t| ").append(reformatResult(totalWrapped / totalNative )).append("   \t|");
        logger.info("");
        logger.info("\n" + bigTableBuffer.toString());
        logger.info("");
        logger.info("\n" + smallTableBuffer.toString());
        logger.info("");
        logger.info("\n" + bigTableBuffer.toString().replaceAll(",", "."));
        logger.info("");
        logger.info("\n" + smallTableBuffer.toString().replaceAll(",", "."));
    }
}
