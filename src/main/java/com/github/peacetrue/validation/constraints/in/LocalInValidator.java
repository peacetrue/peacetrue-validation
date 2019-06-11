package com.github.peacetrue.validation.constraints.in;

import java.util.Arrays;

/**
 * local validator for {@link In}, only string type supported
 *
 * @author xiayx
 */
public class LocalInValidator extends InValidator<In> {

    protected void initializeAnnotation(In in) {
        delimiter = in.delimiter();
        if (in.value().length == 0) {
            logger.trace("no local value found, skip local validate");
        } else {
            values = Arrays.asList(in.value());
            logger.debug("set local collection value {} to this", values);
        }
    }
}
