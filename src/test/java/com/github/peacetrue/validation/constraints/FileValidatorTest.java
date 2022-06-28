package com.github.peacetrue.validation.constraints;

import com.github.peacetrue.test.SourcePathUtils;
import com.github.peacetrue.util.FileUtils;
import com.github.peacetrue.validation.constraints.file.AbsolutePath;
import com.github.peacetrue.validation.constraints.file.FileExistence;
import com.github.peacetrue.validation.constraints.file.FileName;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author peace
 **/
@Slf4j
class FileValidatorTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Data
    @AllArgsConstructor
    public static class PathTypeTestBean<T> {
        @RelativePath
        private T relativePath;
        @AbsolutePath
        private T absolutePath;
        @RelativePath(child = true)
        private T subRelativePath;

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

    @Data
    @AllArgsConstructor
    static class FileNameTestBean {
        @FileName
        private String fileName;
    }

    @Test
    void fileName() {
        FileNameTestBean testBean = new FileNameTestBean(null);
        Set<ConstraintViolation<FileNameTestBean>> violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(0, violations.size());

        String collect = IntStream.range(0, Byte.MAX_VALUE)
                .filter(i -> isPrintableChar((char) i) && i != '/' && i != '\\')
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining(""));
        testBean.setFileName(collect);
        violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(0, violations.size(), "所有可打印字符都能作为文件名");

        testBean.setFileName("11/1");
        violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(1, violations.size());

        testBean.setFileName("11\\1");
        violations = VALIDATOR.validate(testBean);
        log.info("violations: {}", violations);
        Assertions.assertEquals(1, violations.size());
    }

    public static boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return (!Character.isISOControl(c)) &&
                c != 0xFFFF &&
                block != null &&
                block != Character.UnicodeBlock.SPECIALS;
    }
}
