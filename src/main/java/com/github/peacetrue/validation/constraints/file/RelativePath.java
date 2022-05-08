package com.github.peacetrue.validation.constraints.file;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 相对路径验证规则，验证属性值必须是一个相对路径。
 * 标注在可路径化属性上（或者对应的可流化属性上）。
 *
 * @author peace
 * @see com.github.peacetrue.util.FileUtils#toPath(Object) 可路径化对象
 * @see com.github.peacetrue.util.stream.StreamUtils#toStreamSafely(Object) 可流化对象
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RelativePathValidator.class)
public @interface RelativePath {

    /**
     * 必须是子级相对路径，子级相对路径不允许超出父级路径的范围，即不能以 .. 起始。
     *
     * @return true 如果必须是子级相对路径；false 不限制，子级和非子级都可以
     */
    boolean child() default false;

    String message() default "{com.github.peacetrue.validation.constraints.RelativePath.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
