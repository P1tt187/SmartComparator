package smartcomparator.dataclasses;

import smartcomparator.helperclasses.SortType;
import smartcomparator.annotations.MethodName;
import smartcomparator.annotations.NamedSort;
import smartcomparator.annotations.NamedSorts;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 27.10.13
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
@NamedSorts({
        @NamedSort(
                name = "allASC",
                methodNames = {
                        @MethodName(name = "getVal"),
                        @MethodName(name = "getName")
                }),
        @NamedSort(
                name = "mixed",
                methodNames = {
                        @MethodName(name = "getName", sortType = SortType.DESC),
                        @MethodName(name = "getVal")
                }
        )
})
public class TestNamedSortsObject {

    Integer val;
    String name;

    public TestNamedSortsObject(String name, Integer val) {
        this.val = val;
        this.name = name;
    }

    public Integer getVal() {
        return val;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestNamedSortsObject that = (TestNamedSortsObject) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (val != null ? !val.equals(that.val) : that.val != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = val != null ? val.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TestNamedSortsObject{" +
                "val=" + val +
                ", name='" + name + '\'' +
                '}';
    }
}
