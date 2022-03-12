package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.consistency.Consistency;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author peace
 */
//tag::class[]
@Data
@Consistency(properties = {"id", "code", "expressCode"})
public class ConsistencyTB {

    private String id;
    private String code;
    private String expressCode;

    @Data
    public static class Wrapper {
        @Valid
        private ConsistencyTB consistency;
    }
}
//end::class[]
