package com.github.peacetrue.validation.constraints.in;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link In} 验证器。支持验证字符串和集合字符串。
 *
 * @author peace
 */
@Slf4j
public class InValidator implements ConstraintValidator<In, Object> {

    private Set<String> values = Collections.emptySet();
    private boolean negative = false;

    @Override
    public void initialize(In annotation) {
        log.trace("initialize In: '{}'", annotation);
        this.values = Stream.of(annotation.values()).collect(Collectors.toSet());
        this.negative = annotation.negative();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.trace("validate value '{}' by In", value);
        if (value == null) return true;

        if (value instanceof Collection) {
            return negative(this.values.stream().anyMatch(((Collection<?>) value)::contains));
        }

        if (value instanceof String) {
            return negative(this.values.contains(value));
        }

        log.warn("unsupported '{}' type, must be String or Collection<String>", value.getClass().getName());
        return false;
    }

    private boolean negative(boolean contains) {
        return (this.negative && !contains) || (!this.negative && contains);
    }

}
