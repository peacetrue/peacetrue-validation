package com.github.peacetrue.validation.constraints;

import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * the test for {@link Validator}
 *
 * @author xiayx
 */
public class ValidatorTest {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void in() throws Exception {
        InTB user = new InTB();
        Set<ConstraintViolation<InTB>> violations = validator.validate(user);
        Assert.assertEquals(1, violations.size());

//        user.setName("name2");
//        violations = validator.validate(user);
//        Assert.assertEquals(0, violations.size());

        user.setName("name");
        violations = validator.validate(user);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void multiNotNull() {
        MultiNotNullTB multiNotNullTB = new MultiNotNullTB();
        Set<ConstraintViolation<MultiNotNullTB>> violations = validator.validate(multiNotNullTB);
        Assert.assertEquals(violations.size(), 1);
        System.out.println(violations);

        multiNotNullTB.setCode("22");
        violations = validator.validate(multiNotNullTB);
        Assert.assertEquals(violations.size(), 0);
    }

    @Test
    public void consistency() {
        ConsistencyTB consistencyTB = new ConsistencyTB();
        Set<ConstraintViolation<ConsistencyTB>> violations = validator.validate(consistencyTB);
        Assert.assertEquals(violations.size(), 0);

        consistencyTB.setCode("22");
        violations = validator.validate(consistencyTB);
        Assert.assertEquals(violations.size(), 2);
        System.out.println(violations);
    }
}