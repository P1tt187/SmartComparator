package smartcomparator;

import smartcomparator.helperclasses.SortType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 26.10.13
 * Time: 01:50
 * To change this template use File | Settings | File Templates.
 */
public class MethodNameRecord {

    private String methodName;

    private SortType sortType;
    Method method;
    Class retType;
    boolean primitive;
    Constructor retClassConstructor;

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public MethodNameRecord(String methodName) {
        this(methodName, SortType.ASC);
    }

    public MethodNameRecord(String methodName, SortType sortType) {
        this.methodName = methodName;
        this.sortType = sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public SortType getSortType() {
        return this.sortType;
    }

    public void setMethod(Method method) {
        this.method = method;
    }


}
