package study.core.spring.security.studycorespringsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MessageController {
    @GetMapping({"/messages", "/api/message"})
    public String message() {
        return "user/messages";
    }

    @ResponseBody
    @GetMapping("/api/message")
    public String apiMessage() {
        return "messages ok";
    }
}
