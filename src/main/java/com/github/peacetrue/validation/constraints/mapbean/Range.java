package com.github.peacetrue.validation.constraints.mapbean;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

/**
 * number range validator for {@link RestraintValidator}
 *
 * @author xiayx
 */
public class Range implements RestraintValidator<Integer[]> {

    @Override
    public boolean isValid(Object value, Integer[] parameter) {
        if (StringUtils.isEmpty(value)) return true;
        try {
            int intValue = Integer.parseInt(value.toString());
            return parameter[0] <= intValue && intValue <= parameter[1];
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void buildConstraintViolation(ConstraintValidatorContext context, String propertyName, Restraint<Integer[]> restraint) {
        String template = MessageFormat.format("数值必须在{0}和{1}之间", (Object[]) restraint.getValue());
        context.buildConstraintViolationWithTemplate(template).addNode(propertyName).addConstraintViolation();
    }

}
