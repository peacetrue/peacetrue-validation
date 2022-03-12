package com.github.peacetrue.validation.constraints;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author peace
 */
@WebMvcTest({TestController.class, UniqueCheckerImpl.class})
class SpringValidatorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void multiNotNull() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "");
        this.mockMvc.perform(post("/multi/not/null").params(params))
                .andExpect(status().is4xxClientError());

        params.add("id", "12");
        this.mockMvc.perform(post("/multi/not/null").params(params))
                .andExpect(status().isOk());
    }

    @Test
    void delegate() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("code", "false");
        params.set("json", "false");
        this.mockMvc.perform(post("/delegate").params(params))
                .andExpect(status().is4xxClientError());

        params.set("code", "true");
        params.set("json", "{}");
        this.mockMvc.perform(post("/delegate").params(params))
                .andExpect(status().isOk());
    }

    @Test
    void unique() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("id", "1");
        params.set("name", "2");
        this.mockMvc.perform(post("/unique").params(params))
                .andExpect(status().is4xxClientError());

        params.set("id", null);
        params.set("name", "2");
        this.mockMvc.perform(post("/unique").params(params))
                .andExpect(status().isOk());
    }
}
