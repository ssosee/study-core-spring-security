package study.core.spring.security.studycorespringsecurity.service;

import study.core.spring.security.studycorespringsecurity.domain.dto.AccountDto;
import study.core.spring.security.studycorespringsecurity.domain.entity.Account;

import java.util.List;

public interface UserService {
    void createUser(AccountDto dto);
    void modifyUser(AccountDto dto);
    List<Account> getUsers();
    AccountDto getUser(Long id);
    void deleteUser(Long id);
    void order();
}
