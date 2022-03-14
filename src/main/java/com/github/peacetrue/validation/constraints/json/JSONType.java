package com.github.peacetrue.validation.constraints.json;

/**
 * JSON 类型
 *
 * @author peace
 */
public enum JSONType {
    /** 对象，格式为：{"xx":"xx"} */
    OBJECT,
    /** 数组，格式为：[1, {"xx":"xx"}] */
    ARRAY,
    /** 普通值，例如：1, "1", "a" */
    VALUE
}
