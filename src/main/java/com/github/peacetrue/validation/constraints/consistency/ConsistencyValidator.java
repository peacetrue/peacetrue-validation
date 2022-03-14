package com.github.peacetrue.validation.constraints.consistency;

import com.github.peacetrue.spring.beans.BeanUtils;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * {@link Consistency} 验证器。
 *
 * @author peace
 */
@Slf4j
@ToString
public class ConsistencyValidator implements ConstraintValidator<Consistency, Object> {

    private String[] properties;

    @Override
    public void initialize(Consistency annotation) {
        this.properties = annotation.properties();
        log.trace("initialized {}", this);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.trace("validate value '{}'", value);

        if (value == null) return true;

        long emptyCount = Arrays.stream(properties)
                .map(propertyName -> BeanUtils.getPropertyValue(value, propertyName))
                .filter(ObjectUtils::isEmpty).count();
        return emptyCount == 0 || emptyCount == properties.length;
    }

}
