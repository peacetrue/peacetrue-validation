package com.github.peacetrue.validation.constraints.unique;

import com.github.peacetrue.spring.beans.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import static org.springframework.beans.BeanUtils.instantiateClass;


/**
 * 唯一性验证器。
 *
 * @author peace
 */
@Slf4j
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private String idProperty;
    private String uniqueProperty;
    private UniqueChecker uniqueChecker;

    @Override
    public void initialize(Unique annotation) {
        this.idProperty = annotation.id();
        this.uniqueProperty = annotation.unique();
        this.uniqueChecker = instantiateClass(Objects.requireNonNull(annotation.check()));
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;
        if (!StringUtils.hasLength(uniqueProperty)) return this.uniqueChecker.check(null, value);

        Object uniqueValue = BeanUtils.getPropertyValue(value, uniqueProperty);
        log.trace("got unique value '{}' by property '{}'", uniqueValue, uniqueProperty);
        if (uniqueValue == null) return true;

        Object idValue = StringUtils.hasLength(uniqueProperty) ? BeanUtils.getPropertyValue(value, idProperty) : null;
        log.trace("got id value '{}' by property '{}'", idValue, idProperty);
        boolean valid = this.uniqueChecker.check(idValue, uniqueValue);
        if (valid) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(uniqueProperty)
                .addConstraintViolation();
        return false;
    }

}
