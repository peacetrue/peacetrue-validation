package com.github.peacetrue.validation.constraints.json;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * JSON 验证规则，验证属性值必须是一个有效的 JSON 字符串。
 * 标注在字符串属性上。
 *
 * @author peace
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JSONConstraintValidator.class)
@Repeatable(JSON.List.class)
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

    /** 同一个元素上可重复声明 {@link JSON}。 */
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        JSON[] value();
    }

}
