package study.core.spring.security.studycorespringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.core.spring.security.studycorespringsecurity.domain.entity.Account;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    int countByUsername(String username);
}
