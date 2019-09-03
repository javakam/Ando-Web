package com.ando.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title:HelloController
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2019/9/2 16:44
 */
@RestController
public class HelloController {

    @GetMapping(path = "hello")
    public String helloWeb() {
        return "Ando Web";
    }

}
