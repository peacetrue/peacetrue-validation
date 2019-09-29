package com.github.peacetrue.validation.constraints.consistency;

import com.github.peacetrue.beans.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

/**
 * 一个bean中，多个属性值具有一致性表现，
 * 必须同时为null或者同时为非null
 *
 * @author xiayx
 */
public class ConsistencyValidator implements ConstraintValidator<Consistency, Object> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String[] propertyNames;

    @Override
    public void initialize(Consistency constraintAnnotation) {
        logger.info("初始化注解[{}]", constraintAnnotation);
        this.propertyNames = constraintAnnotation.propertyNames();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        logger.info("执行 Consistency 验证 For [{}]", value);
        if (value == null) return true;

        Class<?> valueClass = value.getClass();
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(valueClass);
        int nullCount = 0, propertyCount = 0;
        for (PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getReadMethod() == null || descriptor.getWriteMethod() == null) continue;
            if (propertyNames.length != 0 && Arrays.stream(propertyNames).noneMatch(propertyName -> propertyName.equals(descriptor.getName()))) continue;
            propertyCount++;
            Object propertyValue = ReflectionUtils.invokeMethod(descriptor.getReadMethod(), value);
            logger.debug("取得属性[{}]的值为: {}", descriptor.getName(), propertyValue);
            if (propertyValue == null) nullCount++;
        }

        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addBeanNode().inIterable().atIndex(1).addConstraintViolation();
//                .addPropertyNode("[1,2,3]").addPropertyNode("mobile").addConstraintViolation();

        logger.debug("取得 null 值的数目[{}]", nullCount);

        return nullCount == 0 || nullCount == propertyCount;
    }

}