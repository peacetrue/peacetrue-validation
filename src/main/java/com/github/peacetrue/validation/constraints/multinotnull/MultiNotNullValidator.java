package com.github.peacetrue.validation.constraints.multinotnull;

import com.github.peacetrue.beans.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

/**
 * 验证一个对象的多个属性值至少有n个不能为null
 *
 * @author xiayx
 */
public class MultiNotNullValidator implements ConstraintValidator<MultiNotNull, Object> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private String[] propertyNames;
    private int count;

    @Override
    public void initialize(MultiNotNull constraintAnnotation) {
        logger.info("初始化注解[{}]", constraintAnnotation);
        this.propertyNames = constraintAnnotation.propertyNames();
        this.count = constraintAnnotation.count();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        logger.info("执行 MultiNotNull 验证 For [{}]", value);
        if (value == null) return true;

        Class<?> valueClass = value.getClass();
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(valueClass);
        int notNullCount = 0;
        for (PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getReadMethod() == null || descriptor.getWriteMethod() == null) continue;
            if (propertyNames.length != 0 && Arrays.stream(propertyNames).noneMatch(propertyName -> propertyName.equals(descriptor.getName()))) continue;
            Object propertyValue = ReflectionUtils.invokeMethod(descriptor.getReadMethod(), value);
            logger.debug("取得属性[{}]的值为: {}", descriptor.getName(), propertyValue);
            if (propertyValue != null) notNullCount++;
        }
        logger.debug("取得 not null 值的数目[{}]", notNullCount);

        return notNullCount >= count;
    }

}