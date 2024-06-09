package fr.utc.sr03.chat_admin.controller_web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Test {
    @GetMapping("/truc")
    public String test(){
        System.out.println("---> test");
        return "ok";
    }
}
