package com.github.peacetrue.validation.constraints.in;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link In} 验证器。
 *
 * @author peace
 */
@Slf4j
@ToString
public class InValidator implements ConstraintValidator<In, Object> {

    protected Set<String> values = Collections.emptySet();
    protected boolean negative = false;

    @Override
    public void initialize(In annotation) {
        this.values = Stream.of(annotation.values()).collect(Collectors.toSet());
        this.negative = annotation.negative();
        log.trace("initialized {}", this);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.trace("validate value '{}'", value);

        if (value == null) return true;

        if (value instanceof Object[] && !ObjectUtils.isEmpty((Object[]) value)) {
            return isValid(Arrays.asList((Object[]) value));
        }

        if (value instanceof Collection && !CollectionUtils.isEmpty((Collection<?>) value)) {
            return isValid((Collection<?>) value);
        }

        if (value instanceof String && StringUtils.hasText((String) value)) {
            return isValid(value);
        }

        return isValid(value.toString());
    }

    private boolean isValid(Collection<?> value) {
        Set<String> stringValues = value.stream()
                .filter(item -> !ObjectUtils.isEmpty(item))
                .map(Object::toString)
                .collect(Collectors.toSet());
        boolean matched = negative
                ? this.values.stream().noneMatch(stringValues::contains)
                : this.values.containsAll(stringValues);
        log.trace("target value {} {} in {}: {}", stringValues, negative ? "not" : "is ", values, matched);
        return matched;
    }

    private boolean isValid(Object value) {
        boolean contains = values.contains(value.toString());
        boolean matched = this.negative != contains; //this.negative ? !contains : contains;
        log.trace("target value {} {} in {}: {}", value, negative ? "not" : "is ", values, matched);
        return matched;
    }

}
