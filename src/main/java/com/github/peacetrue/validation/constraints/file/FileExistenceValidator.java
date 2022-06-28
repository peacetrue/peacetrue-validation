package com.github.peacetrue.validation.constraints.file;

import com.github.peacetrue.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * {@link FileExistence} 验证器。
 *
 * @author peace
 */
@Slf4j
public class FileExistenceValidator implements ConstraintValidator<FileExistence, Object> {

    private boolean exists;

    @Override
    public void initialize(FileExistence annotation) {
        this.exists = annotation.exists();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.trace("validate value '{}'", value);
        return value == null || this.exists(value);
    }

    private boolean exists(Object value) {
        Path path = FileUtils.toPath(value);
        return exists ? Files.exists(path) : Files.notExists(path);
    }

}
