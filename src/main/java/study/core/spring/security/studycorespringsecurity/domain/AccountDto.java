package study.core.spring.security.studycorespringsecurity.domain;

import lombok.Data;
import study.core.spring.security.studycorespringsecurity.constant.validation.AccountDtoValidation;

import javax.validation.constraints.NotBlank;

import static study.core.spring.security.studycorespringsecurity.constant.validation.AccountDtoValidation.*;

@Data
public class AccountDto {
    @NotBlank(message = validUsername)
    private String username;
    @NotBlank(message = validPassword)
    private String password;
    @NotBlank(message = validEmail)
    private String email;
    @NotBlank(message = validAge)
    private String age;
    private String role;
}
