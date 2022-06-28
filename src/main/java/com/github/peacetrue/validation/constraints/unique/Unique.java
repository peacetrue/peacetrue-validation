package com.github.peacetrue.validation.constraints.unique;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证值是唯一的。
 * <p>
 * 存在以下常见场景：
 * <ul>
 *     <li>验证值在数据库中是唯一的
 *     <ul>
 *         <li>单个属性值即可完成校验。新增时，标注在属性上，从数据库查询该属性值是否存在</li>
 *         <li>多个属性值才能完成校验。修改时，标注在对象上，指定对象主键和唯一属性，如果属性值被修改，从数据库查询该属性值是否存在</li>
 *     </ul>
 *     </li>
 * </ul>
 *
 * @author peace
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Repeatable(Unique.List.class)
public @interface Unique {

    /**
     * 主键属性名
     *
     * @return 主键属性名
     */
    String id() default "";

    /**
     * 唯一属性名
     *
     * @return 唯一属性名
     */
    String unique() default "";

    /**
     * 唯一值检查器，实际执行检查逻辑的类。
     *
     * @return 唯一值检查器类
     */
    Class<? extends UniqueChecker> check();

    String message() default "{com.github.peacetrue.validation.constraints.Unique.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** 同一个元素上可重复声明 {@link Unique} */
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Unique[] value();
    }

}
