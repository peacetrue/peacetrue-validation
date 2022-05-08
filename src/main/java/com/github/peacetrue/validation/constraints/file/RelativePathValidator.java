package com.github.peacetrue.validation.constraints.file;

import com.github.peacetrue.util.FileUtils;
import com.github.peacetrue.util.stream.StreamUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

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
        return value == null || test(value, this::isRelative);
    }

    static Boolean test(Object value, Predicate<Object> predicate) {
        if (value instanceof Path) return predicate.test(value);
        return Optional.ofNullable(StreamUtils.toStreamSafely(value))
                .map(stream -> stream.allMatch(predicate))
                .orElseGet(() -> predicate.test(value));
    }

    private boolean isRelative(Object value) {
        Path path = FileUtils.toPath(value);
        return !path.isAbsolute() && (!child || !FileUtils.isParentRelativePath(path));
    }


}
