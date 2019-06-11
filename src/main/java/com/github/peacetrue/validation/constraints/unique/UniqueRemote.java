package com.github.peacetrue.validation.constraints.unique;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Collection;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * used on {@link Collection} to validate the elements ara unique
 *
 * @author xiayx
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RemoteUniqueValidator.class)
public @interface UniqueRemote {

    /** the type of element in {@link Collection} */
    Class<?> beanClass();

    String[] properties();

    String[] propertyValues() default {};

    String delimiter() default "";

    /** jpql to select collection value, must return a collection */
    String value();

    String message() default "{com.github.peacetrue.validation.constraints.UniqueRemote.message}%s";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link UniqueRemote} annotations on the same element.
     *
     * @see UniqueRemote
     */
    @Target({METHOD, FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        UniqueRemote[] value();
    }

}