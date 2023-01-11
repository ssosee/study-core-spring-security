package study.core.spring.security.studycorespringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.core.spring.security.studycorespringsecurity.domain.entity.RoleHierarchy;

import java.util.Optional;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {
    Optional<RoleHierarchy> findByChildName(String roleName);
}
