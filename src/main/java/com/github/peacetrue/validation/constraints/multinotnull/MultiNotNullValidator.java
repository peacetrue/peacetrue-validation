package com.github.peacetrue.validation.constraints.multinotnull;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * {@link MultiNotNull} 验证器。
 *
 * @author peace
 */
@Slf4j
@ToString
public class MultiNotNullValidator implements ConstraintValidator<MultiNotNull, Object> {

    private String[] properties;
    private int count;
    private boolean negative;

    @Override
    public void initialize(MultiNotNull annotation) {
        this.properties = annotation.properties();
        this.count = annotation.count();
        this.negative = annotation.negative();
        log.trace("initialized {}", this);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.trace("validate value '{}'", value);

        if (value == null) return true;
        long notEmptyCount = getPropertyDescriptors(value)
                .map(property -> getPropertyValue(value, property))
                .filter(item -> !ObjectUtils.isEmpty(item)).count();
        log.trace("got '{}' nonempty properties for {}", notEmptyCount, toString(value));
        return notEmptyCount >= this.count;
    }

    private Object getPropertyValue(Object bean, PropertyDescriptor property) {
        Object value = ReflectionUtils.invokeMethod(property.getReadMethod(), bean);
        log.trace("{}.{} = {}", toString(bean), property.getName(), value);
        return value;
    }

    private String toString(Object bean) {
        return String.format("%s@%s", bean.getClass().getSimpleName(), bean.hashCode());
    }

    private Stream<PropertyDescriptor> getPropertyDescriptors(Object bean) {
        return negative
                ? Arrays.stream(BeanUtils.getPropertyDescriptors(bean.getClass())).filter(this::filter)
                : Arrays.stream(properties).map(property -> BeanUtils.getPropertyDescriptor(bean.getClass(), property));
    }

    private boolean filter(PropertyDescriptor property) {
        if ("class".equals(property.getName()) || property.getReadMethod() == null) return false;
        return properties.length == 0
                || Stream.of(properties).noneMatch(item -> property.getName().equals(item));
    }

}
