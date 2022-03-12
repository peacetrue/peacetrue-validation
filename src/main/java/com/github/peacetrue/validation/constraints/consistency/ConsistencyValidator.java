package com.github.peacetrue.validation.constraints.consistency;

import com.github.peacetrue.spring.beans.BeanUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

/**
 * 一个 bean 中，多个属性值具有一致性表现，
 * 必须同时为 null 或者同时为非 null.
 *
 * @author peace
 */
@Slf4j
public class ConsistencyValidator implements ConstraintValidator<Consistency, Object> {

    private String[] propertyNames;

    @Override
    public void initialize(Consistency annotation) {
        this.propertyNames = Objects.requireNonNull(annotation.properties());
    }

    @Override
    public boolean isValid(Object bean, ConstraintValidatorContext context) {
        if (bean == null) return true;

        long nullCount = Arrays.stream(propertyNames)
                .map(propertyName -> BeanUtils.getPropertyValue(bean, propertyName))
                .filter(Objects::isNull).count();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addBeanNode().inIterable().atIndex(1).addConstraintViolation();
//                .addPropertyNode("[1,2,3]").addPropertyNode("mobile").addConstraintViolation();
        return nullCount == 0 || nullCount == propertyNames.length;
    }

}
