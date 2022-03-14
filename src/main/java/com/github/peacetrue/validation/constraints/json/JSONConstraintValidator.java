package com.github.peacetrue.validation.constraints.json;

import com.alibaba.fastjson.JSONValidator;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * {@link JSON} 验证器。避免和 {@link JSONValidator} 重名。
 *
 * @author peace
 */
@Slf4j
@ToString
public class JSONConstraintValidator implements ConstraintValidator<JSON, String> {

    private JSONType[] types;

    @Override
    public void initialize(JSON annotation) {
        this.types = annotation.types();
        if (this.types.length == 0) this.types = JSONType.values();
        log.trace("initialized {}", this);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.trace("validate value '{}'", value);

        if (!StringUtils.hasText(value)) return true;

        JSONValidator validator = JSONValidator.from(value);
        if (!validator.validate()) return false;

        JSONValidator.Type type = validator.getType();
        if (type == null) return false;

        JSONType jsonType = JSONType.valueOf(type.name().toUpperCase());
        return Arrays.stream(types).anyMatch(item -> item == jsonType);
    }

}
