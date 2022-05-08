package com.github.peacetrue.validation.constraints.file;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文件存在性验证规则，验证属性值必须存在或不存在。
 * 标注在可路径化属性上（或者对应的可流化属性上）。
 *
 * @author peace
 * @see com.github.peacetrue.util.FileUtils#toPath(Object) 可路径化对象
 * @see com.github.peacetrue.util.stream.StreamUtils#toStreamSafely(Object) 可流化对象
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileExistenceValidator.class)
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

}
