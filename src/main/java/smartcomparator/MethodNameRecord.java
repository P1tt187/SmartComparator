package smartcomparator;

import smartcomparator.helperclasses.SortType;

import java.lang.reflect.Method;


public class MethodNameRecord {

    private String methodName;

    private SortType sortType;
    Method method;
    Class<?> retType;

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
