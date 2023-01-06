package study.core.spring.security.studycorespringsecurity.service;

import study.core.spring.security.studycorespringsecurity.domain.Account;
import study.core.spring.security.studycorespringsecurity.domain.AccountDto;

public interface UserService {
    void createUser(AccountDto dto);

}
