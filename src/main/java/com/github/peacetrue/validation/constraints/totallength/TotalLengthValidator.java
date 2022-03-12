package com.github.peacetrue.validation.constraints.totallength;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

/**
 * {@link TotalLength} 验证器。
 *
 * @author peace
 */
@Slf4j
public class TotalLengthValidator implements ConstraintValidator<TotalLength, Object> {

    private int min;
    private int max;
    private String separator;

    @Override
    public void initialize(TotalLength annotation) {
        this.min = annotation.min();
        this.max = annotation.max();
        this.separator = annotation.separator();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.trace("validate value '{}' by TotalLength", value);
        if (value == null) return true;

        if (value instanceof String[]) {
            int length = String.join(separator, (String[]) value).length();
            return min <= length && length <= max;
        }

        if (value instanceof Collection) {
            @SuppressWarnings("unchecked")
            int length = String.join(separator, (Collection<? extends CharSequence>) value).length();
            return min <= length && length <= max;
        }

        log.trace("unsupported type '{}', must be String[] or Collection<String>", value.getClass().getName());
        return false;
    }

}
