package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.multinotnull.MultiNotNull;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author xiayx
 */
//tag::class[]
@Data
@MultiNotNull(propertyNames = {"id", "code", "expressCode"})
public class MultiNotNullTB {

    private String id;
    private String code;
    private String expressCode;

    @Data
    public static class Wrapper {
        @Valid
        private MultiNotNullTB multiNotNullTB;
    }
}
//end::class[]
