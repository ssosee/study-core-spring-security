package study.core.spring.security.studycorespringsecurity.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.core.spring.security.studycorespringsecurity.domain.AccountDto;
import study.core.spring.security.studycorespringsecurity.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/mypage")
    public String myPage() {
        return "user/mypage";
    }

    @GetMapping("/users")
    public String createUser() {
        return "user/login/register";
    }

    @PostMapping("/users")
    public String createUser(@Valid @ModelAttribute AccountDto dto) {

        userService.createUser(dto);

        return "redirect:/";
    }
}
