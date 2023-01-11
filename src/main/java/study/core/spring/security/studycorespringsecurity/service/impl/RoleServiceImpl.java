package study.core.spring.security.studycorespringsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.core.spring.security.studycorespringsecurity.domain.entity.Role;
import study.core.spring.security.studycorespringsecurity.repository.RoleRepository;
import study.core.spring.security.studycorespringsecurity.service.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new IllegalStateException("Role이 없습니다."));
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
