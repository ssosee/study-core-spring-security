package study.core.spring.security.studycorespringsecurity.service;

import study.core.spring.security.studycorespringsecurity.domain.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRole(Long id);
    List<Role> getRoles();
    void createRole(Role role);
    void deleteRole(Long id);
}
