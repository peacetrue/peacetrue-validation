package com.github.peacetrue.validation.constraints.in;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

/**
 * the value must in a {@link Collection}
 *
 * @author xiayx
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalInValidator.class)
public @interface In {

    String[] value() default {};//the collection //TODO how to support other primitive

    String delimiter() default "";//the value is multiple split by delimiter

    String message() default "{com.github.peacetrue.validation.constraints.In.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}