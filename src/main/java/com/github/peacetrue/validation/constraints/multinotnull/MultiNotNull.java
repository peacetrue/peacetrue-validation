package com.github.peacetrue.validation.constraints.multinotnull;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
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

    /** 属性名，不传默认该bean下的所有属性，无效的属性会被忽略 */
    String[] propertyNames() default {};

    /** 至少有几个非null的属性，默认为1 */
    int count() default 1;

    /**
     * Defines several <code>@MultiNotNull</code> annotations on the same element
     *
     * @author xiayx
     * @see MultiNotNull
     */
    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    @interface List {
        MultiNotNull[] value();
    }
}