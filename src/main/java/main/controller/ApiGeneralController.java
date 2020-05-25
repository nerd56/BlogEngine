package main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ApiGeneralController {
    @GetMapping("/api/init/")
    public HashMap<String, String> getApiInit() {
        HashMap<String, String> map = new HashMap<String, String>(){
            {
                put("title", "DevPub");
                put("subtitle", "Рассказы разработчиков");
                put("phone", "+7 777-77-77");
                put("email", "mail@fmail.com");
                put("copyright", "Dude");
                put("copyrightFrom", "2020");
            }
        };

        return map;
    }
}
