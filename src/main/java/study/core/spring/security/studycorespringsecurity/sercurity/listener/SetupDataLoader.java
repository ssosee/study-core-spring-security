package study.core.spring.security.studycorespringsecurity.sercurity.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.core.spring.security.studycorespringsecurity.domain.dto.AccountDto;
import study.core.spring.security.studycorespringsecurity.domain.entity.*;
import study.core.spring.security.studycorespringsecurity.repository.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ResourcesRepository resourcesRepository;
    private final RoleHierarchyRepository roleHierarchyRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessIpRepository accessIpRepository;
    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        setupSecurityResources();
        setupAccessIpData();

        alreadySetup = true;
    }

    private void setupSecurityResources() {

//        Set<Role> roles = new HashSet<>();
//        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
//        roles.add(adminRole);
//        createResourceIfNotFound("/admin/**", "", roles, "url");
//        Account account = createUserIfNotFound("admin", "admin@gmail.com", "123", 10,  roles);

        Set<Role> roles = new HashSet<>();
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        roles.add(adminRole);
        createResourceIfNotFound("/admin/**", "", roles, "url");
        createResourceIfNotFound("execution(public * io.security.corespringsecurity.aopsecurity.*Service.pointcut*(..))", "", roles, "pointcut");
        createUserIfNotFound("admin", "admin@admin.com", "123", 10, roles);
        Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "매니저권한");
        Role userRole = createRoleIfNotFound("ROLE_USER", "사용자권한");
        createRoleHierarchyIfNotFound(managerRole, adminRole);
        createRoleHierarchyIfNotFound(userRole, managerRole);
    }

    @Transactional
    public Role createRoleIfNotFound(String roleName, String roleDesc) {

        Optional<Role> optionalRole = roleRepository.findByRoleName(roleName);

        if (optionalRole.isEmpty()) {
            Role role = Role.builder()
                    .roleName(roleName)
                    .roleDesc(roleDesc)
                    .build();
            return roleRepository.save(role);
        }

        return optionalRole.get();
    }

    @Transactional
    public Account createUserIfNotFound(final String userName, final String email, final String password, final int age, Set<Role> roleSet) {

        Optional<Account> optionalAccount = userRepository.findByUsername(userName);

        if(optionalAccount.isEmpty()) {
            Account account = Account.builder()
                    .username(userName)
                    .email(email)
                    .age(age)
                    .password(passwordEncoder.encode(password))
                    .userRoles(roleSet)
                    .build();
            return userRepository.save(account);
        }

        return optionalAccount.get();
    }

    @Transactional
    public Resources createResourceIfNotFound(String resourceName, String httpMethod, Set<Role> roleSet, String resourceType) {

        Optional<Resources> optionalResources = resourcesRepository.findByResourceNameAndHttpMethod(resourceName, httpMethod);

        if(optionalResources.isEmpty()) {
            Resources build = Resources.builder()
                    .resourceName(resourceName)
                    .roleSet(roleSet)
                    .httpMethod(httpMethod)
                    .resourceType(resourceType)
                    .orderNum(count.incrementAndGet())
                    .build();

            return resourcesRepository.save(build);
        }

        return optionalResources.get();
    }

    @Transactional
    public RoleHierarchy findRoleHierarchyIfNotFound(Role role) {

        Optional<RoleHierarchy> optionalRoleHierarchyByParentRole = roleHierarchyRepository.findByChildName(role.getRoleName());
        if (optionalRoleHierarchyByParentRole.isEmpty()) {
            RoleHierarchy roleHierarchy = RoleHierarchy.builder()
                    .childName(role.getRoleName())
                    .build();
            return roleHierarchyRepository.save(roleHierarchy);
        }

        return optionalRoleHierarchyByParentRole.get();
    }

    public void createRoleHierarchyIfNotFound(Role childRole, Role parentRole) {
        RoleHierarchy rhc = findRoleHierarchyIfNotFound(childRole);
        RoleHierarchy rhp = findRoleHierarchyIfNotFound(parentRole);
        rhc.changeParentName(rhp);
    }

    private void setupAccessIpData() {

        Optional<AccessIp> optionalAccessIp = accessIpRepository.findByIpAddress("0:0:0:0:0:0:0:1");
        if (optionalAccessIp.isEmpty()) {
            AccessIp accessIp = AccessIp.builder()
                    .ipAddress("0:0:0:0:0:0:0:1")
                    .build();
            accessIpRepository.save(accessIp);
        }
    }
}
