package study.core.spring.security.studycorespringsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ConfigController {
    @GetMapping("/config")
    public String config() {
        return "admin/config";
    }
}
