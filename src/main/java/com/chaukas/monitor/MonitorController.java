package com.chaukas.monitor;

import com.chaukas.auth.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor")
@RequiredArgsConstructor
public class MonitorController {
    private final CurrentUserProvider currentUserProvider;

    @GetMapping("/hello")
    public String monitor() {
        return String.valueOf(currentUserProvider.getCurrentUserId());
    }
}
