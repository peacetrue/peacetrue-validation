package com.github.peacetrue.validation.constraints.multinotnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
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
        if (value == null) return true;

        Class<?> valueClass = value.getClass();
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(valueClass);
        int notNullCount = 0;
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            //空属性默认使用所有属性
            if (!propertyNames.isEmpty() && !propertyNames.contains(propertyDescriptor.getName())) continue;
            //排除Class.getClass及其他非bean的属性
            if (propertyDescriptor.getReadMethod() == null || propertyDescriptor.getWriteMethod() == null) continue;
            Object propertyValue = ReflectionUtils.invokeMethod(propertyDescriptor.getReadMethod(), value);
            logger.debug("取得属性[{}]的值为: {}", propertyDescriptor.getName(), propertyValue);
            if (propertyValue != null) notNullCount++;
        }
        logger.debug("取得 not null 值的数目[{}]", notNullCount);

        return notNullCount >= count;
    }

}