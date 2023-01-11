package study.core.spring.security.studycorespringsecurity.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.core.spring.security.studycorespringsecurity.domain.dto.AccountDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Integer age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "account_roles", joinColumns = {@JoinColumn(name = "account_id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id")})
    private Set<Role> userRoles = new HashSet<>();

    public static Account createAccount(AccountDto dto) {
        Account account = new Account();

        account.username = dto.getUsername();
        account.password = dto.getPassword();
        account.email = dto.getEmail();
        account.age = dto.getAge();

        return account;
    }

    @Builder
    public Account(String username, String password, String email, int age, Set<Role> userRoles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.userRoles = userRoles;
    }

    public void changeRole(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public void changeUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }
}
