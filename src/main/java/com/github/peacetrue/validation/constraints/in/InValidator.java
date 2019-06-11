package com.github.peacetrue.validation.constraints.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * validator for {@link In}
 *
 * @author xiayx
 */
public abstract class InValidator<T extends Annotation> implements ConstraintValidator<T, Object> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected List<?> values = Collections.emptyList();
    protected String delimiter;

    public void initialize(T in) {
        logger.debug("initialize InValidator");
        initializeAnnotation(in);
    }

    protected abstract void initializeAnnotation(T annotation);

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        logger.info("validate value [{}]", value);

        if (StringUtils.isEmpty(value)) {
            logger.trace("empty value means valid");
            return true;
        }

        if (values == null || values.isEmpty()) {
            logger.trace("empty collection values means valid");
            return true;
        }

        boolean valid = valid(value, context);
        logger.trace("validate is {} in {} return {}", value, this.values.size() >= 10 ? ("size " + this.values.size()) : this.values, valid);
        return valid;
    }

    protected boolean valid(Object value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(delimiter)) return this.values.contains(value);
        String stringValue = value.toString();
        Set<String> values = Arrays.stream(stringValue.split(delimiter))
                .map(StringUtils::trimWhitespace)
                .filter(s -> !StringUtils.isEmpty(s))
                .collect(Collectors.toSet());
        logger.trace("split {} with delimiter {} to {}", stringValue, delimiter, values);
        return this.values.containsAll(values);
    }

}