package com.github.peacetrue.validation.constraints.multinotnull;

import com.github.peacetrue.lang.reflect.NoSuchPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 验证一个对象的多个属性值至少有n个不能为null
 *
 * @author xiayx
 */
public class MultiNotNullValidator implements ConstraintValidator<MultiNotNull, Object> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private Set<String> propertyNames;
    private int count;

    @Override
    public void initialize(MultiNotNull constraintAnnotation) {
        logger.info("初始化注解[{}]", constraintAnnotation);
        this.propertyNames = new HashSet<>(Arrays.asList(constraintAnnotation.propertyNames()));
        this.count = constraintAnnotation.count();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        logger.info("执行 MultiNotNull 验证 For [{}]", value);
        if (value == null) return false;

        Class<?> valueClass = value.getClass();
        List<PropertyDescriptor> propertyDescriptors = Arrays.asList(BeanUtils.getPropertyDescriptors(valueClass));
        int nullCount = 0;
        for (String propertyName : propertyNames) {
            Method method = propertyDescriptors.stream()
                    .filter(propertyDescriptor -> propertyDescriptor.getName().equals(propertyName))
                    .findAny().map(PropertyDescriptor::getReadMethod)
                    .orElseThrow(() -> new NoSuchPropertyException(valueClass, propertyName));
            Object propertyValue = ReflectionUtils.invokeMethod(method, value);
            if (propertyValue == null) nullCount++;
        }
        logger.debug("取得 not null 值的数目[{}]", propertyNames.size() - nullCount);

        return propertyNames.size() - nullCount >= count;
    }

}