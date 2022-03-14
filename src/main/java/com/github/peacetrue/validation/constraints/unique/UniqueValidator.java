package com.github.peacetrue.validation.constraints.unique;

import com.github.peacetrue.spring.beans.BeanUtils;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.springframework.beans.BeanUtils.instantiateClass;

/**
 * 唯一性验证器。
 *
 * @author peace
 */
@Slf4j
@ToString
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private String idProperty;
    private String uniqueProperty;
    private UniqueChecker uniqueChecker;

    @Override
    public void initialize(Unique annotation) {
        this.idProperty = annotation.id().trim();
        this.uniqueProperty = annotation.unique().trim();
        this.uniqueChecker = instantiateClass(annotation.check());
        log.trace("initialized {}", this);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.trace("validate value '{}'", value);

        if (ObjectUtils.isEmpty(value)) return true;

        if (!StringUtils.hasLength(this.uniqueProperty)) {
            return this.uniqueChecker.check(null, value);
        }

        Object uniqueValue = BeanUtils.getPropertyValue(value, uniqueProperty);
        log.trace("got unique value '{}' by property '{}'", uniqueValue, uniqueProperty);
        if (ObjectUtils.isEmpty(uniqueValue)) return true;

        Object idValue = StringUtils.hasLength(idProperty) ? BeanUtils.getPropertyValue(value, idProperty) : null;
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
