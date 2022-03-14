package com.github.peacetrue.validation.constraints;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

/**
 * @author peace
 */
@Slf4j
class ValidatorTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private static Validator getValidator(boolean failFast, Locale locale) {
        return Validation
                .byProvider(HibernateValidator.class)
                .configure()
                //how to config failFast
                .failFast(failFast)
                //how to config lang
                .messageInterpolator(new ResourceBundleMessageInterpolator() {
                    @Override
                    public String interpolate(String message, Context context) {
                        return super.interpolate(message, context, locale);
                    }
                })
                .buildValidatorFactory().getValidator();
    }

    @Test
    void multiNotNull() {
        MultiNotNullTestBean testBean = new MultiNotNullTestBean();
        Set<ConstraintViolation<MultiNotNullTestBean>> violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(2, violations.size());

        testBean = new MultiNotNullTestBean().setId("22");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());

        testBean = new MultiNotNullTestBean().setCode("22");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void consistency() {
        ConsistencyTestBean testBean = new ConsistencyTestBean();
        Set<ConstraintViolation<ConsistencyTestBean>> violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(0, violations.size());

        testBean = new ConsistencyTestBean().setCode("22");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void in() {
        InTestBean bean = new InTestBean().setIn("0");
        Set<ConstraintViolation<InTestBean>> violations = VALIDATOR.validate(bean);
        Assertions.assertEquals(1, violations.size());

        bean = new InTestBean().setIn("1");
        violations = VALIDATOR.validate(bean);
        Assertions.assertEquals(0, violations.size());

        bean = new InTestBean().setNotIn("1");
        violations = VALIDATOR.validate(bean);
        Assertions.assertEquals(1, violations.size());

        bean = new InTestBean().setNotIn("0");
        violations = VALIDATOR.validate(bean);
        Assertions.assertEquals(0, violations.size());

        bean = new InTestBean().setIns(Arrays.asList("0", "2"));
        violations = VALIDATOR.validate(bean);
        Assertions.assertEquals(1, violations.size());

        bean = new InTestBean().setIns(Arrays.asList("1", "2"));
        violations = VALIDATOR.validate(bean);
        Assertions.assertEquals(0, violations.size());

        bean = new InTestBean().setNotIns(Arrays.asList("0", "1"));
        violations = VALIDATOR.validate(bean);
        Assertions.assertEquals(1, violations.size());

        bean = new InTestBean().setNotIns(Arrays.asList("0", "3"));
        violations = VALIDATOR.validate(bean);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void unique() {
        UniqueTestBean testBean = new UniqueTestBean().setId("1").setName("name");
        Set<ConstraintViolation<UniqueTestBean>> violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());

        testBean = new UniqueTestBean().setName("name");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void totalLength() {
        Set<ConstraintViolation<TotalLengthTestBean>> violations;
        TotalLengthTestBean testBean = new TotalLengthTestBean().setTotalLength(new String[]{"1"});
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());

        testBean = new TotalLengthTestBean().setTotalLength(new String[]{"11", "222"});
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(0, violations.size());

        testBean = new TotalLengthTestBean().setTotalLength(new String[]{"1111111", "221112"});
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void requiredWhen() {
        Set<ConstraintViolation<RequiredWhenTestBean>> violations;
        RequiredWhenTestBean testBean
                = new RequiredWhenTestBean().setRequired(null).setRequiredCondition("true");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());

        testBean
                = new RequiredWhenTestBean();
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void json() {
        Set<ConstraintViolation<JsonTestBean>> violations;
        JsonTestBean testBean;

        testBean = new JsonTestBean().setJson("a");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());

        testBean = new JsonTestBean().setJsonValue("a");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());

        testBean = new JsonTestBean().setJsonValue("1");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(0, violations.size());

        testBean = new JsonTestBean().setJsonArray("a");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());

        testBean = new JsonTestBean().setJsonArray("[]");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(0, violations.size());

        testBean = new JsonTestBean().setJsonObject("a");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());

        testBean = new JsonTestBean().setJsonObject("{}");
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void locale() {
        Set<ConstraintViolation<JsonTestBean>> violations;
        JsonTestBean testBean = new JsonTestBean().setJson("a");

        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(
                "必须是一个有效的 JSON 字符串",
                violations.iterator().next().getMessage()
        );

        Validator VALIDATOR = getValidator(true, Locale.ENGLISH);
        violations = VALIDATOR.validate(testBean);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(
                "must be JSON string",
                violations.iterator().next().getMessage()
        );
    }
}
