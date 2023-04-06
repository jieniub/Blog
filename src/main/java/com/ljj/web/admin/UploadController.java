package com.ljj.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UploadController {

    @RequestMapping("/upload")
    public String upload(){
        System.out.println("111111111");
        return "test";
    }
}
