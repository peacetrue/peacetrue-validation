package com.github.peacetrue.beans;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

/**
 * @author xiayx
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static String[] toPropertyNames(PropertyDescriptor[] propertyDescriptors) {
        return Arrays.stream(propertyDescriptors)
                //排除非bean的属性，例如：Object.getClass
                .filter(propertyDescriptor -> propertyDescriptor.getReadMethod() != null && propertyDescriptor.getWriteMethod() != null)
                .map(FeatureDescriptor::getName).toArray(String[]::new);
    }

}
