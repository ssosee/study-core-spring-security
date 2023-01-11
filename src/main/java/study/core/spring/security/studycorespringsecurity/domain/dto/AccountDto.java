package study.core.spring.security.studycorespringsecurity.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.core.spring.security.studycorespringsecurity.constant.validation.AccountDtoValidation;
import study.core.spring.security.studycorespringsecurity.domain.entity.Role;

import javax.validation.constraints.NotBlank;

import java.util.List;

import static study.core.spring.security.studycorespringsecurity.constant.validation.AccountDtoValidation.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String id;
    @NotBlank(message = validUsername)
    private String username;
    @NotBlank(message = validPassword)
    private String password;
    @NotBlank(message = validEmail)
    private String email;
    //@NotBlank(message = validAge)
    private Integer age;
    private List<String> roles;
}
