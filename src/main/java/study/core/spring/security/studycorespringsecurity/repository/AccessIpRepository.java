package study.core.spring.security.studycorespringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.core.spring.security.studycorespringsecurity.domain.entity.AccessIp;

import java.util.Optional;

public interface AccessIpRepository extends JpaRepository<AccessIp, Long> {
    Optional<AccessIp> findByIpAddress(String ipAddress);
}
