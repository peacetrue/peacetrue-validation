package com.github.peacetrue.validation.constraints.mapbean;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

/**
 * precision validator for {@link RestraintValidator}
 *
 * @author xiayx
 */
public class Length implements RestraintValidator<Integer[]> {

    @Override
    public boolean isValid(Object value, Integer[] parameter) {
        if (StringUtils.isEmpty(value)) return true;
        int length = value.toString().length();
        return parameter[0] <= length && length <= parameter[1];
    }

    @Override
    public void buildConstraintViolation(ConstraintValidatorContext context, String propertyName, Restraint<Integer[]> restraint) {
        String template = MessageFormat.format("字符长度必须在{0}和{1}之间", (Object[]) restraint.getValue());
        context.buildConstraintViolationWithTemplate(template).addNode(propertyName).addConstraintViolation();
    }
}
