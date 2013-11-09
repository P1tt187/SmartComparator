package smartcomparator.dataclasses;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 25.10.13
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class TestDataObject {
    private int val1;
    private Integer val2 = 0;
    private String val3;

    /**
     * @return the val1
     */
    public int getVal1() {
        return val1;
    }

    /**
     * @param val1 the val1 to set
     */
    public void setVal1(int val1) {
        this.val1 = val1;
    }

    /**
     * @return the val2
     */
    public Integer getVal2() {
        return val2;
    }

    /**
     * @param val2 the val2 to set
     */
    public void setVal2(Integer val2) {
        this.val2 = val2;
    }

    /**
     * @return the val3
     */
    public String getVal3() {
        return val3;
    }

    /**
     * @param val3 the val3 to set
     */
    public void setVal3(String val3) {
        this.val3 = val3;
    }


    @Override
    public String toString() {
        return "[" + Integer.toString(val1) + " , " + val2.toString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestDataObject that = (TestDataObject) o;

        if (val1 != that.val1) return false;
        if (val2 != null ? !val2.equals(that.val2) : that.val2 != null) return false;
        if (val3 != null ? !val3.equals(that.val3) : that.val3 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = val1;
        result = 31 * result + (val2 != null ? val2.hashCode() : 0);
        result = 31 * result + (val3 != null ? val3.hashCode() : 0);
        return result;
    }
}
