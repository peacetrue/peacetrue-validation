package com.github.peacetrue.validation.constraints.requiredwhen;

import com.alibaba.fastjson.JSONValidator;
import com.github.peacetrue.spring.beans.BeanUtils;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * {@link RequiredWhen} 验证器。避免和 {@link JSONValidator} 重名。
 *
 * @author peace
 */
@Slf4j
@ToString
public class RequiredWhenValidator implements ConstraintValidator<RequiredWhen, Object> {

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();

    private String required;
    private Expression condition;

    @Override
    public void initialize(RequiredWhen annotation) {
        this.required = annotation.required().trim();
        this.condition = PARSER.parseExpression(annotation.condition());
        log.trace("initialized {}", this);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.trace("validate value '{}'", value);

        if (value == null) return true;

        Object requiredValue = BeanUtils.getPropertyValue(value, required);
        log.trace("got required value '{}'", requiredValue);
        if (!ObjectUtils.isEmpty(requiredValue)) return true;

        Boolean matched = condition.getValue(value, Boolean.class);
        log.trace("evaluation condition expression '{}' -> '{}'", condition.getExpressionString(), matched);
        if (!Boolean.TRUE.equals(matched)) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(required)
                .addConstraintViolation();
        return false;
    }

}
