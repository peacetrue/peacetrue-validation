package com.github.peacetrue.validation.constraints.multinotnull;

import com.github.peacetrue.validation.constraints.consistency.Consistency;
import org.springframework.util.ObjectUtils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 申明一个 Bean 中，多个属性值不能同时为空，
 * 为空判断逻辑使用 {@link ObjectUtils#isEmpty(Object)}。
 * <p>
 * 使用场景：
 * 新增时，手机号和邮箱不能同时为空；
 * 部分修改时，修改的属性不能都为空，至少要有一个非空属性。
 *
 * @author peace
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MultiNotNullValidator.class)
@Repeatable(MultiNotNull.List.class)
public @interface MultiNotNull {

    /**
     * 指定哪些属性不能同时为空。
     *
     * @return 属性名数组
     */
    String[] properties();

    /**
     * 至少有几个非 {@code null} 的属性，默认为 1。
     *
     * @return 非 {@code null} 属性个数
     */
    int count() default 1;

    /**
     * 是否取反，正向判断属性过多时，可使用反向判断。
     *
     * @return true 表示一个 Bean 中不在指定范围内的其他属性必须不能同时为空
     */
    boolean negative() default false;

    String message() default "{com.github.peacetrue.validation.constraints.MultiNotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** 同一个元素上可重复声明 {@link MultiNotNull}。 */
    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    @interface List {
        MultiNotNull[] value();
    }

}
