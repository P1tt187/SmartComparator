package smartcomparator.annotations;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 27.10.13
 * Time: 21:53
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedSorts {
    NamedSort[] value();
}
