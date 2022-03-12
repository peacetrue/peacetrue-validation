package com.github.peacetrue.validation.constraints.json;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在字符串属性上，验证属性值必须是一个有效的 JSON 字符串。
 *
 * @author peace
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JSONConstraintValidator.class)
public @interface JSON {

    String message() default "{com.github.peacetrue.validation.constraints.JSON.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
