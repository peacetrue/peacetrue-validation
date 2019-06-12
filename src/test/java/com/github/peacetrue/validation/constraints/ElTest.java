package com.github.peacetrue.validation.constraints;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.engine.messageinterpolation.el.SimpleELContext;
import org.junit.Test;

import javax.el.ELProcessor;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

/**
 * @author xiayx
 */
public class ElTest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ElTB {
        private String name;
        private String[] array;
    }

    public static int length(String str) {
        return str.length();
    }

    @Test
    public void name() throws Exception {
        ELProcessor elp = new ELProcessor();
        ElTB bean = new ElTB("Charlie Brown", new String[]{});
        elp.defineBean("employee", bean);
        Object eval = elp.eval("employee.name");
        System.out.println(eval);
        Object value = elp.getValue("employee.name", String.class);
        System.out.println(value);

        ExpressionFactory expressionFactory = ExpressionFactory.newInstance();
        SimpleELContext context = new SimpleELContext();
        context.setFunction("fn", "length", this.getClass().getDeclaredMethod("length", String.class));
        context.setVariable("employee", expressionFactory.createValueExpression(bean, ElTB.class));
        ValueExpression valueExpression = expressionFactory.createValueExpression(context, "${fn:length(employee.name)}${employee.array[0]==null}", String.class);
        Object value1 = valueExpression.getValue(context);
        System.out.println(value1);
    }
}
