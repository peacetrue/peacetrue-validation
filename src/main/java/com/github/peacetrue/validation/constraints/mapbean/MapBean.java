package com.github.peacetrue.validation.constraints.mapbean;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * a bean described by {@link java.util.Map}
 *
 * @author xiayx
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MapBeanValidator.class)
public @interface MapBean {

    String message() default "{com.github.peacetrue.validation.constraints.MapBean.message}";

    /** bean name */
    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}