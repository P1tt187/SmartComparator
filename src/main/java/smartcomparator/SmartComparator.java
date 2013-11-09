package smartcomparator;

import smartcomparator.helperclasses.SortType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author USER
 */
public class SmartComparator<T> implements Comparator<T> {

    // -----------------------------------------------------------------------------------------------------------------

    private Class<T> clazz = null;
    private List<MethodNameRecord> list = new ArrayList<MethodNameRecord>();

    // -----------------------------------------------------------------------------------------------------------------

    public SmartComparator(Class clazz, String namedQuery) {
        list = AnnotationParser.parseTypeAnnotations(clazz, namedQuery);
        this.clazz = clazz;
        this.analyzeClassReturnTypes();
    }

    public SmartComparator(Class<T> clazz) {
        list = AnnotationParser.parseElementAnnotations(clazz);
        this.clazz = clazz;
        this.analyzeClassReturnTypes();
    }

    public SmartComparator(Class<T> clazz, boolean fieldsUsed, List<MethodNameRecord> methodNames) {
        list = methodNames;
        if (fieldsUsed){
            for (MethodNameRecord methodItem : methodNames){
                methodItem.setMethodName(addGet(methodItem.getMethodName()));
            }
        }
        this.clazz = clazz;
        this.analyzeClassReturnTypes();

    }

    public SmartComparator(Class<T> clazz, List<MethodNameRecord> methodNames) {
         this(clazz,false,methodNames);
    }

    public SmartComparator(Class<T> clazz, MethodNameRecord[] methodNames) {
        Collections.addAll(list, methodNames);
        this.clazz = clazz;
        this.analyzeClassReturnTypes();
    }



    public SmartComparator(Class<T> clazz, boolean fieldsUsed, String[] methodNames, SortType[] sortTypes) {
        if (sortTypes == null) {
            sortTypes = new SortType[methodNames.length];
            for (int i = 0; i < methodNames.length; i++) {
                sortTypes[i] = SortType.ASC;
            }
        }

        if (methodNames.length != sortTypes.length) {
            throw new ArrayIndexOutOfBoundsException("length of methodNames and sortType must match");
        }
        this.clazz = clazz;
        changeSorting(fieldsUsed, methodNames, sortTypes);
    }



    // -----------------------------------------------------------------------------------------------------------------

    public void changeSorting(boolean fieldsUsed, String[] methodNames, SortType[] sortTypes) {
        //sortTypeMap = new HashMap<>();
        if (fieldsUsed) {
            /**if you don't have specified a get method the comparator generates the name*/
            for (int i = 0; i < methodNames.length; i++) {
                methodNames[i] = addGet(methodNames[i]);
            }
        }

        int i = 0;
        for (String t : methodNames)
            list.add(new MethodNameRecord(t, sortTypes[i++]));
        this.analyzeClassReturnTypes();
    }

    private String addGet(String methodName) {
        if (!methodName.startsWith("get")) {
            String newName = methodName;
            char firstChar = methodName.charAt(0);
            if (firstChar >= 'a' && firstChar <= 'z') {
                newName = newName.replaceFirst("" + firstChar, ("" + firstChar).toUpperCase());
            }
            newName = "get" + newName;
            methodName = newName;
        }
        return methodName;
    }


    public void changeSorting(String namedQuery) {
        if (namedQuery == null || namedQuery.isEmpty()) {
            throw new NullPointerException("namedQuery must not be null");
        }
        list = AnnotationParser.parseTypeAnnotations(this.clazz, namedQuery);
        this.analyzeClassReturnTypes();
    }

    public void setSortlist(Class<T> clazz, String... sortlist) {
        for (String t : sortlist)
            list.add(new MethodNameRecord(t, SortType.ASC));
        this.clazz = clazz;
        this.analyzeClassReturnTypes();
    }

    private void analyzeClassReturnTypes() {
        if (list != null) {
            for (MethodNameRecord val : list) {
                try {
                    Method meth = this.clazz.getMethod(val.getMethodName());
                    Class clazz = meth.getReturnType();
                    val.setMethod(meth);
                    if (Object.class.isAssignableFrom(clazz)) {
                        if (Comparable.class.isAssignableFrom(clazz)) {
                            val.retType=clazz;
                        } else {
                            // else it is not assignable to Comparable  ==> Element löschen
                            list.remove(val);
                        }
                    } else {
                        // val.setRetType(castObjects(clazz));
                        val.retClassConstructor=castObjects(clazz).getConstructor(clazz);
                        val.retType=castObjects(clazz);
                        val.primitive= true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public int compare(T t, T t1) {
        int ret = 0;
        for (MethodNameRecord val : list) {
            ret = compare(t, t1, val);
            if (ret != 0) return (val.getSortType() == SortType.DESC ? -1 : 1) * ret;
        }
        return ret;
    }

    private int compare(T t, T t1, MethodNameRecord VAL) {
        int ret = 0;
        if (t == null && t1 == null) return 0;
        try {
            Method meth = VAL.method;
            Object obj1 = null;
            Object obj2 = null;
            Comparable cmp = null;

            // is it an Object?
            if (VAL.primitive == false) {
                // it's an object
                // it implements comparable
                obj1 = meth.invoke(t);
                obj2 = meth.invoke(t1);
                cmp = (Comparable) obj1;
            } else {
                // it's one of the standard data types like int, float, double, long

                obj1 = VAL.retClassConstructor.newInstance(meth.invoke(t));
                obj2 = VAL.retClassConstructor.newInstance(meth.invoke(t1));
                cmp = (Comparable) obj1;
            }
            ret = cmp.compareTo(obj2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    private Class castObjects(Class clazz) {
        if (clazz.isAssignableFrom(int.class)) {
            return Integer.class;
        } else if (clazz.isAssignableFrom(float.class)) {
            return Float.class;
        } else if (clazz.isAssignableFrom(double.class)) {
            return Double.class;
        } else if (clazz.isAssignableFrom(char.class)) {
            return Character.class;
        } else if (clazz.isAssignableFrom(byte.class)) {
            return Byte.class;
        } else if (clazz.isAssignableFrom(boolean.class)) {
            return Boolean.class;
        } else if (clazz.isAssignableFrom(short.class)) {
            return Short.class;
        } else if (clazz.isAssignableFrom(long.class)) {
            return Long.class;
        }
        return null;
    }

    // -----------------------------------------------------------------------------------------------------------------

}
