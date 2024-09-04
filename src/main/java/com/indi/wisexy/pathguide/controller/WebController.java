package com.indi.wisexy.pathguide.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @RequestMapping("/full")
    public String fullF() {
        return "ff";
    }

}
