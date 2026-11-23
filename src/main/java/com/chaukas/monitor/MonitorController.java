package com.chaukas.monitor;

import com.chaukas.auth.dto.LoginResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor")
public class MonitorController {
    @GetMapping("/hello")
    public String monitor(){
        return "Hello World";
    }
}
