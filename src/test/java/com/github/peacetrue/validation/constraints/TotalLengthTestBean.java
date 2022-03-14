package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.totallength.TotalLength;
import lombok.Getter;

/**
 * @author peace
 **/
@Getter
public class TotalLengthTestBean {

    @TotalLength(min = 3, max = 10)
    private String[] totalLength;

    public TotalLengthTestBean setTotalLength(String[] totalLength) {
        this.totalLength = totalLength;
        return this;
    }
}
