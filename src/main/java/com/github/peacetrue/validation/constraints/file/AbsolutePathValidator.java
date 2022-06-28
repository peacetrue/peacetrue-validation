package com.github.peacetrue.validation.constraints.file;

import com.github.peacetrue.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * {@link AbsolutePath} 验证器。
 *
 * @author peace
 */
@Slf4j
public class AbsolutePathValidator implements ConstraintValidator<AbsolutePath, Object> {

    @Override
    public void initialize(AbsolutePath annotation) {
        // nothing need to do
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.trace("validate value '{}'", value);
        return value == null || this.isAbsolute(value);
    }

    private boolean isAbsolute(Object value) {
        return FileUtils.toPath(value).isAbsolute();
    }

}
