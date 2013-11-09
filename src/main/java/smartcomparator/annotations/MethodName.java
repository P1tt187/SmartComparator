package smartcomparator.annotations;

import smartcomparator.helperclasses.SortType;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 27.10.13
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface MethodName {
    /**
     * name of the sort Method
     */
    String name();

    /**
     * optional sortType, ascended or descended <br/>
     * default ascended
     */
    SortType sortType() default SortType.ASC;
}
