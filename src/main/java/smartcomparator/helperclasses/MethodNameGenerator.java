package smartcomparator.helperclasses;

import smartcomparator.MethodNameRecord;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Fabian
 *         Date: 29.10.13
 *         Time: 00:48
 *         To change this template use File | Settings | File Templates.
 */
public class MethodNameGenerator {

    private List<MethodNameRecord> list = new LinkedList<>();

    public MethodNameGenerator(String name) {
        this(name, SortType.ASC);
    }

    public MethodNameGenerator(String... names) {
        for (String name : names) {
            add(name);
        }
    }

    public MethodNameGenerator(String name, SortType sortType) {
        list.add(new MethodNameRecord(name, sortType));
    }

    public MethodNameGenerator add(String name) {
        return add(name, SortType.ASC);
    }

    public MethodNameGenerator add(String name, SortType sortType) {
        list.add(new MethodNameRecord(name, sortType));
        return this;
    }

    public MethodNameRecord[] getArray() {
        return list.toArray(new MethodNameRecord[list.size()]);
    }

    public List<MethodNameRecord> getList() {
        return list;
    }
}
