package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.requiredwhen.RequiredWhen;
import lombok.Getter;

/**
 * @author peace
 **/
@Getter
@RequiredWhen(required = "required", condition = "requiredCondition=='true'")
public class RequiredWhenTestBean {

    private String required;
    private String requiredCondition;

    public RequiredWhenTestBean setRequired(String required) {
        this.required = required;
        return this;
    }

    public RequiredWhenTestBean setRequiredCondition(String requiredCondition) {
        this.requiredCondition = requiredCondition;
        return this;
    }
}
