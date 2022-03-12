package com.github.peacetrue.validation.constraints.totallength;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记在字符串类型的集合属性上，
 * 可以是 {@code String[]} 或者 {@code Collection<String>}。
 * 以指定分隔符合并字符串集合，
 * 验证其总长度不得大于指定值。
 * <p>
 * 使用场景：当数据库使用一个字段存储多个值时，比如：标签。
 *
 * @author peace
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TotalLengthValidator.class)
public @interface TotalLength {

    /**
     * 字符串集合合并后，总长度下限值。
     *
     * @return 总长度下限值
     */
    int min() default 1;

    /**
     * 字符串集合合并后，总长度上限值。
     *
     * @return 总长度上限值
     */
    int max() default 255;

    /**
     * 合并字符串集合使用的分隔符。
     *
     * @return 分隔符
     */
    String separator() default ",";

    String message() default "{com.github.peacetrue.validation.constraints.TotalLength.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** 同一个元素上可重复声明 {@link TotalLength} */
    @Target({METHOD, FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        TotalLength[] value();
    }

}
