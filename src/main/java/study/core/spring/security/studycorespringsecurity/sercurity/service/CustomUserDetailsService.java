package study.core.spring.security.studycorespringsecurity.sercurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.core.spring.security.studycorespringsecurity.domain.entity.Account;
import study.core.spring.security.studycorespringsecurity.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Account> optionalAccount = userRepository.findByUsername(username);
        Account account = optionalAccount.orElseThrow(() -> new UsernameNotFoundException("존재하는 회원이 없습니다."));

        Set<String> userRoles = account.getUserRoles().stream()
                .map(userRole -> userRole.getRoleName())
                .collect(Collectors.toSet());

        List<GrantedAuthority> roles = userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        AccountContext accountContext = new AccountContext(account, roles);

        return accountContext;
    }
}
