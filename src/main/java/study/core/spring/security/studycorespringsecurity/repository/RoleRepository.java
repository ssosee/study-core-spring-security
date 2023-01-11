package study.core.spring.security.studycorespringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.core.spring.security.studycorespringsecurity.domain.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}
