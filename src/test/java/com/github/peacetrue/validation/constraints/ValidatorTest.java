package com.github.peacetrue.validation.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author peace
 */
class ValidatorTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void multiNotNull() {
        MultiNotNullTB multiNotNullTB = new MultiNotNullTB();
        Set<ConstraintViolation<MultiNotNullTB>> violations = validator.validate(multiNotNullTB);
        Assertions.assertEquals(2, violations.size());
        System.out.println(violations);

        multiNotNullTB.setCode("22");
        violations = validator.validate(multiNotNullTB);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void consistency() {
        ConsistencyTB consistencyTB = new ConsistencyTB();
        Set<ConstraintViolation<ConsistencyTB>> violations = validator.validate(consistencyTB);
        Assertions.assertEquals(0, violations.size());

        consistencyTB.setCode("22");
        violations = validator.validate(consistencyTB);
        Assertions.assertEquals(2, violations.size());
    }

    @Test
    void in() {
        InTB inTB = new InTB();
        Set<ConstraintViolation<InTB>> violations = validator.validate(inTB);
        Assertions.assertEquals(1, violations.size());

        inTB.setName("x0");
        violations = validator.validate(inTB);
        Assertions.assertEquals(1, violations.size());

        inTB.setName("x1");
        violations = validator.validate(inTB);
        Assertions.assertEquals(0, violations.size());

        inTB.setName("x2");
        violations = validator.validate(inTB);
        Assertions.assertEquals(0, violations.size());

        inTB.setName("x3");
        violations = validator.validate(inTB);
        Assertions.assertEquals(1, violations.size());

        inTB.setName("x1");
        inTB.setJson("x3");
        violations = validator.validate(inTB);
        Assertions.assertEquals(1, violations.size());

        inTB.setJson("{\"a\":\"a\"}");
        violations = validator.validate(inTB);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void unique() {
        Set<ConstraintViolation<TestBean>> violations;
        TestBean testBean = new TestBean();
        testBean.setId("1");
        testBean.setName("name");
        violations = validator.validate(testBean);
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void totalLength() {
        Set<ConstraintViolation<TestBean>> violations;
        TestBean testBean = new TestBean();
        testBean.setScopes(new String[]{"1"});
        violations = validator.validate(testBean);
        Assertions.assertEquals(1, violations.size());

        testBean.setScopes(new String[]{"11", "222"});
        violations = validator.validate(testBean);
        Assertions.assertEquals(0, violations.size());

        testBean.setScopes(new String[]{"1111111", "221112"});
        violations = validator.validate(testBean);
        System.out.println(violations);
        Assertions.assertEquals(1, violations.size());
    }
}
