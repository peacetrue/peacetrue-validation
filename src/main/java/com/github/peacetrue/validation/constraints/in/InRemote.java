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
@Constraint(validatedBy = RemoteInValidator.class)
public @interface InRemote {

    /** jpql to select collection value, must return a collection */
    String value() default "";

    /** @see In#delimiter() */
    String delimiter() default "";

    String message() default "{com.github.peacetrue.validation.constraints.InRemote.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}