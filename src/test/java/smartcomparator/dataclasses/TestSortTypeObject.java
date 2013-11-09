package smartcomparator.dataclasses;


import smartcomparator.helperclasses.SortType;
import smartcomparator.annotations.CompareCriteria;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 25.10.13
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class TestSortTypeObject {

    @CompareCriteria(priority = 1, sortType = SortType.ASC)
    private String val;

    @CompareCriteria(priority = 2, sortType = SortType.DESC)
    private Integer number;

    public TestSortTypeObject(String val, Integer number) {
        this.val = val;
        this.number = number;
    }

    public String getVal() {

        return val;
    }

    @Override
    public String toString() {
        return "TestSortTypeObject{" +
                "val='" + val + '\'' +
                ", number=" + number +
                '}';
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestSortTypeObject that = (TestSortTypeObject) o;

        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (val != null ? !val.equals(that.val) : that.val != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = val != null ? val.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}
