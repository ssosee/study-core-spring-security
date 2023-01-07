package study.core.spring.security.studycorespringsecurity.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import study.core.spring.security.studycorespringsecurity.sercurity.config.SecurityConfig;

import javax.persistence.*;

import static study.core.spring.security.studycorespringsecurity.sercurity.config.SecurityConfig.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String age;
    private String role;

    public static Account createAccount(AccountDto dto) {
        Account account = new Account();

        account.username = dto.getUsername();
        account.password = dto.getPassword();
        account.email = dto.getEmail();
        account.age = dto.getAge();
        account.role = dto.getRole();

        return account;
    }
}
