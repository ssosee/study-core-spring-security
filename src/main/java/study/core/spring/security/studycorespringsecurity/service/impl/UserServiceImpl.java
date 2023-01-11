package study.core.spring.security.studycorespringsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.core.spring.security.studycorespringsecurity.domain.entity.Account;
import study.core.spring.security.studycorespringsecurity.domain.dto.AccountDto;
import study.core.spring.security.studycorespringsecurity.domain.entity.Role;
import study.core.spring.security.studycorespringsecurity.repository.RoleRepository;
import study.core.spring.security.studycorespringsecurity.repository.UserRepository;
import study.core.spring.security.studycorespringsecurity.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public void createUser(AccountDto dto) {
        // 암호화
        String encodePassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodePassword);

        // 권한 부여
        Optional<Role> optionalRole = roleRepository.findByRoleName("ROLE_USER");
        Role role = optionalRole.orElseThrow(() -> new IllegalStateException("Role이 없습니다."));

        Account account = Account.createAccount(dto, role);

        userRepository.save(account);
    }

    @Transactional
    @Override
    public void modifyUser(AccountDto dto) {

        Account account = modelMapper.map(dto, Account.class);

        if(dto.getRoles() != null) {
            Set<Role> roleSet = new HashSet<>();
            dto.getRoles().forEach(role -> {
                Optional<Role> roleOptional = roleRepository.findByRoleName(role);
                Role r = roleOptional.orElseThrow(() -> new IllegalStateException("Role이 없습니다."));
                roleSet.add(r);
            });
            account.changeRole(roleSet);
        }
        account.changePassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(account);
    }

    @Override
    public List<Account> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public AccountDto getUser(Long id) {
        Account account = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User가 없습니다."));

        ModelMapper modelMapper = new ModelMapper();
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);

        List<String> roles = account.getUserRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        accountDto.setRoles(roles);

        return accountDto;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Secured("ROLE_MANAGER")
    @Override
    public void order() {
        System.out.println("order");
    }
}
