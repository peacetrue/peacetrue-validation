package com.github.peacetrue.validation.constraints.json;

import com.alibaba.fastjson.JSONValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * {@link JSON} 验证器。避免和 {@link JSONValidator} 重名。
 *
 * @author peace
 */
@Slf4j
public class JSONConstraintValidator implements ConstraintValidator<JSON, String> {

    @Override
    @SuppressWarnings("all")
    public void initialize(JSON json) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.trace("validate value '{}' by JSON", value);
        if (!StringUtils.hasText(value)) return true;
        return JSONValidator.from(value).validate();
    }

}
