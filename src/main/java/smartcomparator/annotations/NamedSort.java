package smartcomparator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 27.10.13
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedSort {
    String name();

    MethodName[] methodNames();
}
