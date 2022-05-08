package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.test.SourcePathUtils;
import com.github.peacetrue.util.FileUtils;
import com.github.peacetrue.validation.constraints.file.AbsolutePath;
import com.github.peacetrue.validation.constraints.file.FileExistence;
import com.github.peacetrue.validation.constraints.file.RelativePath;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author peace
 **/
@Slf4j
class FileValidatorTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Data
    @AllArgsConstructor
    static class PathTypeTestBean<T> {
        @RelativePath
        private T relativePath;
        @AbsolutePath
        private T absolutePath;
        @RelativePath(child = true)
        private T subRelativePath;

        public static <T> PathTypeTestBean<List<T>> list(T relativePath, T absolutePath, T subRelativePath) {
            return new PathTypeTestBean<>(
                    Collections.singletonList(relativePath),
                    Collections.singletonList(absolutePath),
                    Collections.singletonList(subRelativePath)
            );
        }

        public static <T> PathTypeTestBean<T> raw(T relativePath, T absolutePath, T subRelativePath) {
            return new PathTypeTestBean<>(relativePath, absolutePath, subRelativePath);
        }
    }

    @Test
    void validatePathType() {
        PathTypeTestBean<?> testBean = new PathTypeTestBean<>(null, null, null);
        Set<ConstraintViolation<PathTypeTestBean<?>>> violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(0, violations.size());

        testBean = new PathTypeTestBean<>(Paths.get("../../relativePath"), Paths.get("/absolutePath"), Paths.get("relativePath"));
        violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(0, violations.size());

        testBean = new PathTypeTestBean<>(Paths.get("/absolutePath"), Paths.get("relativePath"), Paths.get("../aa/.."));
        violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(3, violations.size());

        testBean = PathTypeTestBean.list("../../relativePath", "/absolutePath", "relativePath");
        violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(0, violations.size());

        testBean = PathTypeTestBean.list("/absolutePath", "relativePath", "../aa/..");
        violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(3, violations.size());

        testBean = PathTypeTestBean.raw("../../relativePath", "/absolutePath", "relativePath");
        violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(0, violations.size());

        testBean = PathTypeTestBean.raw("/absolutePath", "relativePath", "../aa/..");
        violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(3, violations.size());
    }

    @Data
    @AllArgsConstructor
    static class FileExistenceTestBean<T> {
        @FileExistence(exists = false)
        private T absentPath;
        @FileExistence
        private T existPath;
    }

    @Test
    void validateFileExistence() throws IOException {
        FileExistenceTestBean<?> testBean = new FileExistenceTestBean<>(null, null);
        Set<ConstraintViolation<FileExistenceTestBean<?>>> violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(0, violations.size());

        String existPathString = SourcePathUtils.getTestResourceAbsolutePath("/existPath");
        Path existPath = Paths.get(existPathString);
        FileUtils.createFileIfAbsent(existPath);

        testBean = new FileExistenceTestBean<>(existPath.resolveSibling("absentPath"), existPath);
        violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(0, violations.size());

        testBean = new FileExistenceTestBean<>(existPath, existPath.resolveSibling("absentPath"));
        violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(2, violations.size());

        Files.deleteIfExists(existPath);
    }
}
