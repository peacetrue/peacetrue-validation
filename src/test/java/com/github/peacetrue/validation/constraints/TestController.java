package com.github.peacetrue.validation.constraints;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author peace
 **/
@RestController
public class TestController {

    @RequestMapping("/multi/not/null")
    public void test(@Valid MultiNotNullTB tb) {
        System.out.print("come");
    }

    @RequestMapping("/delegate")
    public void delegate(@Valid TestBean tb) {
        System.out.print("come");
    }

    @RequestMapping("/unique")
    public void unique(@Valid TestBean tb) {
        System.out.print("come");
    }

}
