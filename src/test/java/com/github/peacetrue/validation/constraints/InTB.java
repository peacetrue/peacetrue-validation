package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.in.In;
import com.github.peacetrue.validation.constraints.json.JSON;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author peace
 */
@Data
public class InTB {

    @JSON
    private String json;
    @NotNull
    @In(values = {"x1", "x2"})
    private String name;

}
