package com.github.peacetrue.validation.constraints.mapbean;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

/**
 * precision validator for {@link RestraintValidator}
 *
 * @author xiayx
 */
public class Precision implements RestraintValidator<Integer> {

    @Override
    public boolean isValid(Object value, Integer parameter) {
        if (StringUtils.isEmpty(value)) return true;
        try {
            String stringValue = String.valueOf(Double.parseDouble(value.toString()));
            return stringValue.substring(stringValue.indexOf("\\.") + 1).length() <= parameter;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void buildConstraintViolation(ConstraintValidatorContext context, String propertyName, Restraint<Integer> restraint) {
        String template = MessageFormat.format("精度必须小于等于{0}", restraint.getValue());
        context.buildConstraintViolationWithTemplate(template).addNode(propertyName).addConstraintViolation();
    }

}