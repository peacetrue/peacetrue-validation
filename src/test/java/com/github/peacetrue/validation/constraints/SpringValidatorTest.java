package com.github.peacetrue.validation.constraints;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.beans.PropertyEditor;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SpringValidatorTest.class,
        properties = "logging.level.com.github.peacetrue=debug")
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class SpringValidatorTest {

    @ControllerAdvice
    public static class StringTrimmerControllerAdvice {
        @InitBinder
        public void registerCustomEditors(WebDataBinder binder) {
            // configure for empty string change to null
            binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        }
    }

    @Controller
    public static class Controller1 {
        @RequestMapping("/multi/not/null")
        public void test(@Valid MultiNotNullTB tb) {
            System.out.print("22");
        }

//        @Bean
//        public CustomEditorConfigurer CustomEditorConfigurer() {
//            CustomEditorConfigurer configurer = new CustomEditorConfigurer();
//            configurer.setPropertyEditorRegistrars(new PropertyEditorRegistrar[]{new CustomPropertyEditorRegistrar()});
//            return configurer;
//        }

    }

//    public static final class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {
//
//        public void registerCustomEditors(PropertyEditorRegistry registry) {
//
//            // it is expected that new PropertyEditor instances are created
//            registry.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//
//            // you could register as many custom property editors as are required here...
//        }
//    }


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void multiNotNull() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "");
        this.mockMvc.perform(post("/multi/not/null").params(params))
                .andExpect(status().is4xxClientError());

        params.add("id", "12");
        this.mockMvc.perform(post("/multi/not/null").params(params))
                .andExpect(status().isOk());

    }
}
