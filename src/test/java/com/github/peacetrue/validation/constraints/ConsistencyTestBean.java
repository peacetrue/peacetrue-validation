package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.consistency.Consistency;
import lombok.Getter;

/**
 * @author peace
 **/
@Getter
@Consistency(properties = {"code", "name"})
public class ConsistencyTestBean {

    private String id;
    private String code;
    private String name;

    public ConsistencyTestBean setId(String id) {
        this.id = id;
        return this;
    }

    public ConsistencyTestBean setCode(String code) {
        this.code = code;
        return this;
    }

    public ConsistencyTestBean setName(String name) {
        this.name = name;
        return this;
    }
}
