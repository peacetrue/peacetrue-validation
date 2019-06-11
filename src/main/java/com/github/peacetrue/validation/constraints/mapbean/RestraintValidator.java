package com.github.peacetrue.validation.constraints.mapbean;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * validator for the properties in {@link MapBean} like {@link ConstraintValidator}
 *
 * @author xiayx
 */
public interface RestraintValidator<T> {

    boolean isValid(Object value, T parameter);

    default void buildConstraintViolation(ConstraintValidatorContext context, String propertyName, Restraint<T> restraint) {}
}