package com.github.peacetrue.validation.constraints.requiredwhen;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标注在类型上，指定某个属性是必须的，当其他属性满足某个条件时。
 * 比如：保存身份信息时，如果证件类型为身份证，则身份证号是必须的；
 * <p>
 * 这里不检查当依赖属性不是指定值时，必须属性一定要为空。
 * 比如：保存身份信息时，如果证件类型不是身份证，则身份证号必须为空；
 *
 * @author peace
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequiredWhenValidator.class)
@Repeatable(RequiredWhen.List.class)
public @interface RequiredWhen {

    /**
     * 指定哪个属性是必须的。
     *
     * @return 必须的属性名
     */
    String required();

    /**
     * 指定依赖属性需要满足的条件，
     * 需要使用 SpeL 表达式，
     * Root 对象为当前注解所标注类的实例，可以使用其中的所有属性。
     * <p>
     * 例如：证件类型为身份证，type=='ID'。
     *
     * @return SPEL 表达式条件，必须返回布尔值
     */
    String condition();

    /**
     * 描述定为：是必须的，因为无法准确表达条件表达式的含义。
     * 理想情况下应该提示：
     * 证件类型为身份证时，身份证号是必须的，
     * 目前只能"身份证号是必须的"。
     * <p>
     * 如果想准确提示，需要在申明注解时，自定义描述。
     *
     * @return 违反约束时的提示描述信息。
     */
    String message() default "{com.github.peacetrue.validation.constraints.RequiredWhen.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** 同一个元素上可重复声明 {@link RequiredWhen}。 */
    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    @interface List {
        RequiredWhen[] value();
    }
}
