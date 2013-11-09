package smartcomparator.dataclasses;


import smartcomparator.annotations.CompareCriteria;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 24.10.13
 * Time: 02:49
 * To change this template use File | Settings | File Templates.
 */
public class TestStringObject {

    @CompareCriteria(priority = 2)
    private String val;

    private int val2;

    public TestStringObject(String val) {
        this.val = val;
    }

    @CompareCriteria(priority = 1)
    public int getVal2() {

        return val2;
    }

    public TestStringObject(String val, int val2) {
        this.val = val;
        this.val2 = val2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestStringObject that = (TestStringObject) o;

        if (val2 != that.val2) return false;
        if (val != null ? !val.equals(that.val) : that.val != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = val != null ? val.hashCode() : 0;
        result = 31 * result + val2;
        return result;
    }


    public String getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "TestStringObject{" +
                "val='" + val + '\'' +
                ", val2=" + val2 +
                '}';
    }
}
