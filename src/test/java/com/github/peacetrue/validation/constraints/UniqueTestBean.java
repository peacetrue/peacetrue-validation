package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.unique.Unique;
import lombok.Getter;

/**
 * @author peace
 **/
@Getter
@Unique(id = "id", unique = "name", check = UniqueCheckerImpl.class)
public class UniqueTestBean {
    private String id;
    private String name;

    public UniqueTestBean setId(String id) {
        this.id = id;
        return this;
    }

    public UniqueTestBean setName(String name) {
        this.name = name;
        return this;
    }
}
