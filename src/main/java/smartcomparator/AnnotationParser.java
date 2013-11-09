package smartcomparator;

import smartcomparator.annotations.CompareCriteria;
import smartcomparator.annotations.MethodName;
import smartcomparator.annotations.NamedSort;
import smartcomparator.annotations.NamedSorts;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author fabian
 *         Date: 24.10.13
 *         Time: 18:02
 */
final class AnnotationParser {
    /**
     * default constructor
     */
    private AnnotationParser() {

    }

    /**
     * parseTypeAnnotations annotations
     *
     * @param clazz      the class to parse named queries
     * @param namedQuery named query to use
     */
    public static List<MethodNameRecord> parseTypeAnnotations(final Class<?> clazz, String namedQuery) {
        if (!clazz.isAnnotationPresent(NamedSorts.class)) {
            throw new IllegalArgumentException("No NamedSorts annotation was found");
        }
        if (namedQuery == null) {
            throw new NullPointerException("namedQuery must not be null");
        }

        List<MethodNameRecord> result = new LinkedList<>();

        boolean foundQuery = false;
        NamedSorts namedSorts = clazz.getAnnotation(NamedSorts.class);
        for (NamedSort namedSort : namedSorts.value()) {
            if (namedSort.name().equals(namedQuery)) {
                foundQuery = true;

                for (MethodName methodName : namedSort.methodNames()) {
                    result.add(new MethodNameRecord(methodName.name(), methodName.sortType()));
                }
                break;
            }
        }

        if (!foundQuery) {
            throw new NoSuchElementException("The specified named query was not found");
        }

        return result;
    }

    /**
     * parseElementAnnotations annotations
     *
     * @param clazz the class to parseElementAnnotations
     */
    public static List<MethodNameRecord> parseElementAnnotations(final Class<?> clazz) {


        /**
         *  key - priority
         *  value - methodName and sortType
         *  */
        Map<Integer, MethodNameRecord> methods = new HashMap<>();

        if (clazz.isPrimitive()) {
            throw new IllegalArgumentException("you can only generate a comparator for own classes, not primitives or standard classes");
        }

        /**first, collect all annotations and values*/

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(CompareCriteria.class)) {
                String firstChar = field.getName().charAt(0) + "";
                String methodName = "get" + field.getName().replaceFirst(firstChar, firstChar.toUpperCase());
                CompareCriteria criteria = field.getAnnotation(CompareCriteria.class);
                methods.put(criteria.priority(), new MethodNameRecord(methodName, criteria.sortType()));
            }
        }


        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(CompareCriteria.class)) {
                CompareCriteria criteria = method.getAnnotation(CompareCriteria.class);
                methods.put(criteria.priority(), new MethodNameRecord(method.getName(), criteria.sortType()));
            }
        }

        /**Sort the keys*/
        List<Integer> sortedPriority = new LinkedList<>(methods.keySet());
        Collections.sort(sortedPriority, Collections.reverseOrder());

        /**create a new list of the methodNames based on the priority*/
        List<MethodNameRecord> sortedMethodNames = new LinkedList<>();
        for (Integer priority : sortedPriority) {
            sortedMethodNames.add(methods.get(priority));
        }


        return sortedMethodNames;
    }
}
