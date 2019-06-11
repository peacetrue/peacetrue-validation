package com.github.peacetrue.validation.constraints.mapbean;

import javax.validation.Constraint;
import java.util.Arrays;

/**
 * a restraint same as {@link Constraint}
 *
 * @author xiayx
 */
public class Restraint<T> {

    public final static Restraint<Object> REQUIRED = new Restraint<>("required", null);

    private String name;
    private T value;

    public Restraint(String name, T value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("{name:%s,value:%s}", name, value instanceof Object[] ? Arrays.toString((Object[]) value) : value);
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

}
