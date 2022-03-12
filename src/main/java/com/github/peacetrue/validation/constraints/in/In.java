package com.github.peacetrue.validation.constraints.in;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在字符串或字符串集合属性上，验证属性值必须在指定的集合内。
 *
 * @author peace
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InValidator.class)
public @interface In {

    /**
     * 属性值必须在指定集合范围内。
     *
     * @return 指定值数组
     */
    String[] values();

    String message() default "{com.github.peacetrue.validation.constraints.In.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 是否取反。
     *
     * @return true 表示属性值必须不在指定集合范围内
     */
    boolean negative() default false;

}
