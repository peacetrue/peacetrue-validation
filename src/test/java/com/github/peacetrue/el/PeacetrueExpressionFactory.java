//package com.github.peacetrue.el;
//
//import javax.el.ELContext;
//import javax.el.FunctionMapper;
//import javax.el.MethodExpression;
//import javax.el.ValueExpression;
//import java.lang.reflect.Method;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * @author xiayx
// */
//public class PeacetrueExpressionFactory extends com.sun.el.ExpressionFactoryImpl {
//
//    public PeacetrueExpressionFactory() {
//    }
//
//    public PeacetrueExpressionFactory(Properties properties) {
//        super(properties);
//    }
//
//    public static int length(String str) {
//        return str.length();
//    }
//
//    @Override
//    public MethodExpression createMethodExpression(ELContext context, String expression, Class expectedReturnType, Class[] expectedParamTypes) {
//        return super.createMethodExpression(context, expression, expectedReturnType, expectedParamTypes);
//    }
//
//    @Override
//    public ValueExpression createValueExpression(ELContext context, String expression, Class expectedType) {
//        getInitFunctionMap().forEach((key, value) -> {
//            String[] parts = key.split(":");
//            FunctionMapper functionMapper = context.getFunctionMapper();
//            functionMapper.mapFunction(parts[0], parts[1], value);
//        });
//        return super.createValueExpression(context, expression, expectedType);
//    }
//
//    @Override
//    public ValueExpression createValueExpression(Object instance, Class expectedType) {
//        return super.createValueExpression(instance, expectedType);
//    }
//
//    @Override
//    public Map<String, Method> getInitFunctionMap() {
//        Map<String, Method> initFunctionMap = super.getInitFunctionMap();
//        try {
//            initFunctionMap.put("fn:length", PeacetrueExpressionFactory.class.getDeclaredMethod("length", String.class));
//        } catch (NoSuchMethodException ignored) {
//
//        }
//        return initFunctionMap;
//    }
//}
