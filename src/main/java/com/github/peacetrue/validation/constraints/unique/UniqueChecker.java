package com.github.peacetrue.validation.constraints.unique;

import javax.annotation.Nullable;

/**
 * 唯一值检查器。
 *
 * @author peace
 **/
public interface UniqueChecker {

    /**
     * 检查属性值是否唯一
     *
     * @param id     主键值
     * @param unique 唯一属性值
     * @return true 如果是唯一的
     */
    boolean check(@Nullable Object id, Object unique);
}
