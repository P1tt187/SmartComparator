package smartcomparator.annotations;


import smartcomparator.helperclasses.SortType;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 24.10.13
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompareCriteria {
    /**
     * @author fabian
     * prirority of this attribute
     * a higher value suggest the SmartComperator a hither priority
     */
    int priority() default 1;

    /**
     * SortType ASC or DESC
     */
    SortType sortType() default SortType.ASC;
}
