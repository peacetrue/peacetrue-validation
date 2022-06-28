package com.github.peacetrue.validation.constraints.file;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
 * {@link FileName} 验证器。
 *
 * @author peace
 */
@Slf4j
public class FileNameValidator implements ConstraintValidator<FileName, String> {

    @Override
    public void initialize(FileName annotation) {
        // nothing need to do
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.trace("validate value '{}'", value);
        return value == null || !isInvalidFileName(value);
    }

    private static boolean isInvalidFileName(String value) {
        return value.contains("/") || value.contains("\\") || isInvalidPath(value);
    }

    private static boolean isInvalidPath(String value) {
        try {
            Paths.get(value);
            return false;
        } catch (InvalidPathException e) {
            log.trace("the value '{}' is invalid path", value, e);
            return true;
        }
    }

}
