package com.github.peacetrue.validation.constraints.in;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标注在'字符串/数组/集合'属性上，验证属性值必须'在/不在'指定的集合内。
 * 如果属性类型不是字符串，会使用 {@link Object#toString()} 转换为字符串。
 *
 * @author peace
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InValidator.class)
@Repeatable(In.List.class)
public @interface In {

    /**
     * 属性值必须在指定集合范围内。
     *
     * @return 指定值数组
     */
    String[] values();

    /**
     * 是否取反。
     *
     * @return true 表示属性值必须不在指定集合范围内
     */
    boolean negative() default false;

    String message() default "{com.github.peacetrue.validation.constraints.In.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** 同一个元素上可重复声明 {@link In}。 */
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        In[] value();
    }

}
