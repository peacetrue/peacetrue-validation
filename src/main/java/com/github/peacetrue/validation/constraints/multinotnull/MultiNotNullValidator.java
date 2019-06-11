package com.github.peacetrue.validation.constraints.multinotnull;

import com.github.peacetrue.lang.reflect.NoSuchPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
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
        if (propertyNames.length == 0) {
            //设置所有属性，验证描述时需要使用
            propertyNames = Arrays.stream(descriptors)
                    //排除非bean的属性，例如：Object.getClass
                    .filter(propertyDescriptor -> propertyDescriptor.getReadMethod() != null && propertyDescriptor.getWriteMethod() != null)
                    .map(FeatureDescriptor::getName).toArray(String[]::new);
            logger.debug("取得类[{}]的所有属性: {}", valueClass.getName(), propertyNames);
        }

        int notNullCount = 0;
        for (String propertyName : propertyNames) {
            Method method = Arrays.stream(descriptors)
                    .filter(propertyDescriptor -> propertyDescriptor.getName().equals(propertyName))
                    .findAny().map(PropertyDescriptor::getReadMethod)
                    .orElseThrow(() -> new NoSuchPropertyException(valueClass, propertyName));
            Object propertyValue = ReflectionUtils.invokeMethod(method, value);
            if (propertyValue != null) notNullCount++;
        }
        logger.debug("取得 not null 值的数目[{}]", notNullCount);

        return notNullCount >= count;
    }

}