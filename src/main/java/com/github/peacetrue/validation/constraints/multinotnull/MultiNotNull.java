package com.github.peacetrue.validation.constraints.multinotnull;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 申明一个bean中，多个属性值不能同时为null
 *
 * @author xiayx
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MultiNotNullValidator.class})
public @interface MultiNotNull {

    String message() default "{com.github.peacetrue.validation.constraints.MultiNotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** 属性名 */
    String[] propertyNames();

    /** 至少有几个，默认为1 */
    int count() default 1;

    /**
     * Defines several <code>@MultiNotNull</code> annotations on the same element
     *
     * @author xiayx
     * @see MultiNotNull
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        MultiNotNull[] value();
    }
}