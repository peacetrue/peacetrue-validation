package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.in.In;
import lombok.Getter;

import java.util.List;

/**
 * @author peace
 **/
@Getter
public class InTestBean {

    @In(values = {"1", "2"})
    private String in;
    @In(values = {"1", "2"}, negative = true)
    private String notIn;
    @In(values = {"1", "2"})
    private List<String> ins;
    @In(values = {"1", "2"}, negative = true)
    private List<String> notIns;

    public InTestBean setIn(String in) {
        this.in = in;
        return this;
    }

    public InTestBean setNotIn(String notIn) {
        this.notIn = notIn;
        return this;
    }

    public InTestBean setIns(List<String> ins) {
        this.ins = ins;
        return this;
    }

    public InTestBean setNotIns(List<String> notIns) {
        this.notIns = notIns;
        return this;
    }
}
