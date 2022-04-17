package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.multinotnull.MultiNotNull;
import lombok.Getter;

/**
 * @author peace
 **/
@MultiNotNull(properties = {"id", "code", "name"})
@MultiNotNull(properties = {"id"}, negative = true)
@Getter
public class MultiNotNullTestBean {

    private String id;
    private String code;
    private String name;

    public MultiNotNullTestBean setId(String id) {
        this.id = id;
        return this;
    }

    public MultiNotNullTestBean setCode(String code) {
        this.code = code;
        return this;
    }

    public MultiNotNullTestBean setName(String name) {
        this.name = name;
        return this;
    }
}
