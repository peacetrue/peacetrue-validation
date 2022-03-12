package com.github.peacetrue.validation.constraints.multinotnull;

import com.github.peacetrue.spring.beans.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.springframework.beans.BeanUtils.getPropertyDescriptors;

/**
 * {@link MultiNotNull} 验证器。
 *
 * @author peace
 */
@Slf4j
public class MultiNotNullValidator implements ConstraintValidator<MultiNotNull, Object> {

    private String[] properties;
    private int count;
    private boolean negative;

    @Override
    public void initialize(MultiNotNull annotation) {
        log.trace("initialize MultiNotNull: '{}'", annotation);
        this.properties = annotation.properties();
        this.count = annotation.count();
        this.negative = annotation.negative();
    }

    @Override
    public boolean isValid(Object bean, ConstraintValidatorContext context) {
        if (bean == null) return true;
        if (negative) {
            long notEmptyCount = Arrays.stream(getPropertyDescriptors(bean.getClass()))
                    .filter(property -> !"class".equals(property.getName()) && property.getReadMethod() != null)
                    .filter(property -> Stream.of(properties).noneMatch(item -> property.getName().equals(item)))
                    .map(property -> ReflectionUtils.invokeMethod(property.getReadMethod(), bean))
                    .filter(value -> !ObjectUtils.isEmpty(value)).count();
            return notEmptyCount >= this.count;
        }

        long notEmptyCount = Arrays.stream(properties)
                .map(propertyName -> BeanUtils.getPropertyValue(bean, propertyName))
                .filter(item -> !ObjectUtils.isEmpty(item)).count();
        return notEmptyCount >= this.count;
    }

}
