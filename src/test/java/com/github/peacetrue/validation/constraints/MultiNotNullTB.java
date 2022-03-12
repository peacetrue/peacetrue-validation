package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.multinotnull.MultiNotNull;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author peace
 */
//tag::class[]
@Data
@MultiNotNull.List({
        @MultiNotNull(properties = {"id", "code", "name"}),
        @MultiNotNull(properties = {"id"}, negative = true)
})
public class MultiNotNullTB {

    private String id;
    private String code;
    private String name;

    @Data
    public static class Wrapper {
        @Valid
        private MultiNotNullTB multiNotNullTB;
    }
}
//end::class[]
