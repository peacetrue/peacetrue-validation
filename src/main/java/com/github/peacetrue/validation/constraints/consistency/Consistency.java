package com.github.peacetrue.validation.constraints.consistency;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标注在类上，声明多个属性值具有一致性表现。
 * 必须同时为空或者同时为非空。
 * <p>
 * 例如：地址为选填，但如果选了省份，必须选择市区，不选省份也无法选择市区。
 *
 * @author peace
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ConsistencyValidator.class)
@Repeatable(Consistency.List.class)
public @interface Consistency {

    /**
     * 属性名数组
     *
     * @return 属性名数组
     */
    String[] properties();

    String message() default "{com.github.peacetrue.validation.constraints.Consistency.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** 同一个元素上可重复声明 {@link Consistency}。 */
    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Consistency[] value();
    }
}
