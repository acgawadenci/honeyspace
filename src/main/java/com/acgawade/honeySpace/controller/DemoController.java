package com.acgawade.honeySpace.controller;

import com.acgawade.honeySpace.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    ImageService imageService;

    @GetMapping("/")
    public String demoResponse() {
        return "This is my demo Controller, Hello User !!!";
    }

    @GetMapping("/{userName}")
    public String demoResp(@PathVariable String userName) {
        return "This is also my demo Controller, Hello User "+userName+" !!!";
    }

    @GetMapping("/test")
    public String demoTestResponse() {
        imageService.imageTest();
        return "This is my demo Controller, Hello User !!!";
    }
}
