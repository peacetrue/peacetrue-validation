package com.github.peacetrue.validation.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author peace
 **/
class ResourceBundleTest {

    /** 如果指定的语言不存在，不会去找默认方言，而是找了本地语言 */
    @Test
    void getBundle() {
        String baseName = "ValidationMessages";
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, Locale.JAPANESE);
        Assertions.assertEquals(
                Locale.getDefault().getLanguage(),
                bundle.getLocale().getLanguage(),
                "获取一个不存在的方言，默认会使用本地方言");

        bundle = ResourceBundle.getBundle(baseName, Locale.JAPANESE, new ResourceBundle.Control() {
            @Override
            public Locale getFallbackLocale(String baseName, Locale locale) {
                return Locale.ROOT;
            }
        });
        Assertions.assertEquals(Locale.ROOT, bundle.getLocale(),
                "如果想要使用默认方言，必须自定义 Control");
        //理想情况下，期望能找默认方言
        //如果将默认方言和本地方言保持一致，则找本地方言等同于找默认方言
    }
}
