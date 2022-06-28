package com.github.peacetrue.validation.constraints.file;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 相对路径验证规则，验证属性值必须是一个相对路径。
 * 标注在可路径化属性上。
 *
 * @author peace
 * @see com.github.peacetrue.util.FileUtils#toPath(Object) 可路径化对象
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RelativePathValidator.class)
@Repeatable(RelativePath.List.class)
public @interface RelativePath {

    /**
     * 必须是子级相对路径，子级相对路径不允许超出父级路径的范围，即不能以 .. 起始。
     * 程序设计的意图是在指定目录下工作，超出指定目录的范围，会引发意想不到的问题，比如在删除文件时。
     *
     * @return true 如果必须是子级相对路径；false 不限制，子级和非子级都可以
     */
    boolean child() default false;

    String message() default "{com.github.peacetrue.validation.constraints.RelativePath.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** 同一个元素上可重复声明 {@link RelativePath}。 */
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        RelativePath[] value();
    }

}
