package study.core.spring.security.studycorespringsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.core.spring.security.studycorespringsecurity.domain.Account;
import study.core.spring.security.studycorespringsecurity.domain.AccountDto;
import study.core.spring.security.studycorespringsecurity.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void createUser(AccountDto dto) {
        // 암호화
        String encodePassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodePassword);

        userRepository.save(Account.createAccount(dto));
    }
}
