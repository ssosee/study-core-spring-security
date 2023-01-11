package study.core.spring.security.studycorespringsecurity.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfigController {

    @GetMapping
    public String config() {
        return "admin/config";
    }
}
