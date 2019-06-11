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
@Constraint(validatedBy = LocalUniqueValidator.class)
public @interface Unique {

    /** the type of element in {@link Collection} */
    Class<?> beanClass();

    /** property name may multiple */
    String[] properties() default {};

    /** the default property value */
    String[] propertyValues() default {};

    String delimiter() default "";

    String message() default "{com.github.peacetrue.validation.constraints.Unique.message}%s";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link Unique} annotations on the same element.
     *
     * @see Unique
     */
    @Target({METHOD, FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Unique[] value();
    }

}