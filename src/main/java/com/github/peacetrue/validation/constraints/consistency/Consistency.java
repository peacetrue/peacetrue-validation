package com.github.peacetrue.validation.constraints.consistency;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 申明一个bean中，多个属性值具有一致性表现。
 * 必须同时为null或者同时为非null
 *
 * @author xiayx
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ConsistencyValidator.class})
public @interface Consistency {

    String message() default "{com.github.peacetrue.validation.constraints.Consistency.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** 属性名，不传默认为该bean下的所有属性，无效的属性会被忽略 */
    String[] propertyNames() default {};

    /**
     * Defines several <code>@Consistency</code> annotations on the same element
     *
     * @author xiayx
     * @see Consistency
     */
    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Consistency[] value();
    }
}