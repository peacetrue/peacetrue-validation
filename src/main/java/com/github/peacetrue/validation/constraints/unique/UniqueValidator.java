package com.github.peacetrue.validation.constraints.unique;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


/**
 * validator for {@link Unique} and {@link UniqueRemote}
 *
 * @author xiayx
 */
public abstract class UniqueValidator<T extends Annotation> implements ConstraintValidator<T, List<?>> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<?> beanClass;
    protected String[] properties;
    protected Method[] readMethods;
    protected Object[] propertyValues;
    protected String delimiter;
    @Resource
    private ConversionService conversionService;

    public void initialize(T annotation) {
        logger.debug("initialize validator");
        initializeAnnotation(annotation);
        setReadMethods();
        setDefaultValues();
    }

    /** set annotation properties into this properties */
    protected abstract void initializeAnnotation(T annotation);

    protected void setReadMethods() {
        //get read methods prepare for validate
        this.readMethods = new Method[properties.length];
        for (int i = 0; i < properties.length; i++) {
            readMethods[i] = BeanUtils.getPropertyDescriptor(beanClass, properties[i]).getReadMethod();
        }
    }

    protected void setDefaultValues() {
        for (int i = 0; i < this.propertyValues.length; i++) {
            if (StringUtils.isEmpty(this.propertyValues[i])) continue;
            this.propertyValues[i] = conversionService.convert(this.propertyValues[i], this.readMethods[i].getReturnType());
        }
    }

    protected void buildConstraintViolation(ConstraintValidatorContext context, List<?> duplication) {
        context.disableDefaultConstraintViolation();
        String template = String.format(context.getDefaultConstraintMessageTemplate(), duplication);
        context.buildConstraintViolationWithTemplate(template).addConstraintViolation();
    }

    protected Object[] getValues(Object object) {
        Object[] values = new Object[readMethods.length];
        for (int i = 0; i < readMethods.length; i++) {
            values[i] = ReflectionUtils.invokeMethod(readMethods[i], object);
        }
        //all empty return null
        if (Arrays.stream(values).allMatch(StringUtils::isEmpty)) return null;

        //multiple value support default value
        for (int i = 0; i < values.length; i++) {
            values[i] = processDefaultValue(i, values[i]);
        }
        return values;
    }

    protected Object processDefaultValue(int i, Object value) {
        if (!StringUtils.isEmpty(value)) return value;
        if (propertyValues == null || propertyValues.length <= i || StringUtils.isEmpty(propertyValues[i]))
            return value;
        return propertyValues[i];
    }

    protected Object concatValues(Object[] values) {
        return StringUtils.arrayToDelimitedString(values, delimiter);
    }

}