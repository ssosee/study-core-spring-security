package study.core.spring.security.studycorespringsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MessageController {
    @GetMapping("/messages")
    public String mypage() {
        return "user/messages";
    }
}
