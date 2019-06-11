package com.github.peacetrue.lang.reflect;

import lombok.Getter;

/**
 * @author xiayx
 * @see NoSuchFieldException
 */
@Getter
public class NoSuchPropertyException extends RuntimeException {

    private Class<?> clazz;
    private String propertyName;

    public NoSuchPropertyException(Class<?> clazz, String propertyName) {
        super(String.format("the property [%s.%s] not found", clazz.getName(), propertyName));
        this.clazz = clazz;
        this.propertyName = propertyName;
    }
}
