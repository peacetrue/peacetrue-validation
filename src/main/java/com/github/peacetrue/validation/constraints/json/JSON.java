package com.github.peacetrue.validation.constraints.json;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注在字符串属性上，验证属性值必须是一个有效的 JSON 字符串。
 *
 * @author peace
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JSONConstraintValidator.class)
public @interface JSON {

    /**
     * 指定 JSON 的类型，不指定默认为所有类型。
     *
     * @return JSON 的类型
     */
    JSONType[] types() default {};

    String message() default "{com.github.peacetrue.validation.constraints.JSON.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
