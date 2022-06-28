package com.github.peacetrue.validation.constraints.file;

import com.github.peacetrue.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.file.Path;

/**
 * {@link RelativePath} 验证器。
 *
 * @author peace
 */
@Slf4j
public class RelativePathValidator implements ConstraintValidator<RelativePath, Object> {

    private boolean child;

    @Override
    public void initialize(RelativePath annotation) {
        this.child = annotation.child();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.trace("validate value '{}'", value);
        return value == null || this.isRelative(value);
    }

    private boolean isRelative(Object value) {
        Path path = FileUtils.toPath(value);
        return !path.isAbsolute() && (!child || !FileUtils.isParentRelativePath(path));
    }


}
