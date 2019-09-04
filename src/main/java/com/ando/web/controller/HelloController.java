package com.ando.web.controller;

import com.ando.web.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

@Controller
public class HelloController {

//    @RequestMapping({"/","/index.html"})
//    public String index(){
//        return "index";
//    }

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(@RequestParam("user") String user) {
        if (user.equals("aaa")) {
            throw new UserNotExistException();
        }
        return "Hello World";
    }

    //查出用户数据，在页面展示
    /* Thymeleaf 五种表达式:
        ${...} : 变量表达式。
        *{...} : 选择表达式。
        #{...} : 消息 (i18n) 表达式。
        @{...} : 链接 (URL) 表达式。
        ~{...} : 片段表达式。*/
    @RequestMapping("/success")
    public String thymeleaf(Map<String, Object> map) {
        map.put("hello", "<h1>你好世界</h1>");
        map.put("users", Arrays.asList("zhangsan", "lisi", "wangwu"));
        //*{...} : 选择表达式。
        map.put("realuser", new User("小明", 6, "admin"));
        map.put("daming", new User("大明", 28, "manager"));
        return "success";
    }

    private class User {
        public String name;
        public int age;
        public String type;

        public User(String name, int age, String type) {
            this.name = name;
            this.age = age;
            this.type = type;
        }

        public String run() {
            return "<b>" + name + " 爱跑步</b>";
        }

        public String whoSpeak(String name) {
            return name + "正再讲话";
        }
    }

}
