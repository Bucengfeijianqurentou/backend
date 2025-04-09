package com.gb.backend.controller;

import com.gb.backend.annotation.PassToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 测试控制器，请不要往此控制器写入
 */
@RequiredArgsConstructor
@RequestMapping("/api/test")
@RestController
public class TestController {

    @GetMapping("h1")
    public String h1(){
        return "Hello World!";
    }


    @PassToken
    @GetMapping("h2")
    public String h2(){
        return "Hello World2!";
    }


}
