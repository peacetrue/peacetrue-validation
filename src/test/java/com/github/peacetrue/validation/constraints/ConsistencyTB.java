package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.consistency.Consistency;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author xiayx
 */
//tag::class[]
@Data
@Consistency.List({
        @Consistency,
        @Consistency(propertyNames = {"id", "code", "expressCode"})
})
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
