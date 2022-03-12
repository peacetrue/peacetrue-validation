package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.validation.constraints.unique.UniqueChecker;

import javax.annotation.Nullable;

/**
 * @author peace
 **/
public class UniqueCheckerImpl implements UniqueChecker {

    @Override
    public boolean check(@Nullable Object id, Object unique) {
        return id == null;
    }

}
