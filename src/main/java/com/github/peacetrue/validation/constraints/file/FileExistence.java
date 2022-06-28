package com.github.peacetrue.validation.constraints.file;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 文件存在性验证规则，验证属性值必须存在或不存在。
 * 标注在可路径化属性上。
 *
 * @author peace
 * @see com.github.peacetrue.util.FileUtils#toPath(Object) 可路径化对象
 * @see com.github.peacetrue.util.stream.StreamUtils#toStreamSafely(Object) 可流化对象
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileExistenceValidator.class)
@Repeatable(FileExistence.List.class)
public @interface FileExistence {

    /**
     * 文件是否存在。
     *
     * @return true 文件必须存在，false 文件必须不存在
     */
    boolean exists() default true;

    String message() default "{com.github.peacetrue.validation.constraints.FileExistence.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** 同一个元素上可重复声明 {@link FileExistence}。 */
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        FileExistence[] value();
    }

}
