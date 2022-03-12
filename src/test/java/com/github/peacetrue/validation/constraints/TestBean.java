package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.json.JSON;
import com.github.peacetrue.validation.constraints.totallength.TotalLength;
import com.github.peacetrue.validation.constraints.unique.Unique;
import lombok.Data;

/**
 * @author peace
 */
//tag::class[]
@Data
@Unique(id = "id", unique = "name", check = UniqueCheckerImpl.class)
public class TestBean {

    private String id;
    private String code;
    private String name;
    @JSON
    private String json;
    @TotalLength(min = 3, max = 10)
    private String[] scopes;

}
//end::class[]
