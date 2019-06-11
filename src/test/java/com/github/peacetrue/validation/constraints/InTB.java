package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.in.In;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * user for test
 *
 * @author xiayx
 */
@Data
public class InTB {

    @NotNull
    @In({"name"})
    private String name;

}
