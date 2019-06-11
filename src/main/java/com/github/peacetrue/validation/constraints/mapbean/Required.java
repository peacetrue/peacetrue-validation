package com.github.peacetrue.validation.constraints.mapbean;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

/**
 * required validator for {@link RestraintValidator}
 *
 * @author xiayx
 */
public class Required implements RestraintValidator<Object> {

    @Override
    public boolean isValid(Object value, Object parameter) {
        return !StringUtils.isEmpty(value);
    }

    @Override
    public void buildConstraintViolation(ConstraintValidatorContext context, String propertyName, Restraint<Object> restraint) {
        String template = MessageFormat.format("必须不为空", restraint);
        context.buildConstraintViolationWithTemplate(template).addNode(propertyName).addConstraintViolation();
    }

}