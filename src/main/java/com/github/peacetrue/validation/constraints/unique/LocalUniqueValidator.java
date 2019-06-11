package com.github.peacetrue.validation.constraints.unique;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidatorContext;
import java.util.*;
import java.util.stream.Collectors;


/**
 * validator for {@link Unique}
 *
 * @author xiayx
 */
public class LocalUniqueValidator extends UniqueValidator<Unique> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void initializeAnnotation(Unique unique) {
        beanClass = unique.beanClass();
        properties = unique.properties();
        propertyValues = unique.propertyValues();
        delimiter = unique.delimiter();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        if (value == null) return true;
        Map<Object[], List<?>> group = new LinkedHashMap<>(value.size());//group and keep the list order
        for (Object item : value) {
            if (item == null) continue;

            Object[] values = getValues(item);
            if (values == null) continue;

            Object[] sameValues = group.keySet().stream().filter(objects -> Arrays.deepEquals(objects, values)).findAny().orElse(values);//use value equals
            List objects = group.computeIfAbsent(sameValues, k -> new ArrayList<>());
            objects.add(item);
        }

        //TODO message template need to add more var
        List<Map.Entry<Object[], List<?>>> duplication = group.entrySet().stream().filter(o -> o.getValue().size() > 1).collect(Collectors.toList());
        if (duplication.isEmpty()) return true;

        buildConstraintViolation(context, duplication);
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void buildConstraintViolation(ConstraintValidatorContext context, List<?> duplication) {
        duplication = duplication.stream().map(o -> this.formatValues((Map.Entry<Object[], List<?>>) o)).collect(Collectors.toList());
        super.buildConstraintViolation(context, duplication);
    }

    protected Object formatValues(Map.Entry<Object[], List<?>> values) {
        return this.concatValues(values.getKey()) + String.format("(共%s条)", values.getValue().size());
    }

    /*
    protected String getIndexes(List<?> value, Map<String, ? extends List<?>> group) {
        List<? extends List<?>> du = group.values().stream().filter(o -> o.size() > 1).collect(Collectors.toList());
        return du.stream().map(o -> getIndexes(value, o)).collect(Collectors.joining(","));
    }

    protected String getIndexes(List<?> value, List<?> subValues) {
        return subValues.stream().map(o -> String.valueOf(value.indexOf(o))).collect(Collectors.joining(","));
    }
    */
}