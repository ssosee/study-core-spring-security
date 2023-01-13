package study.core.spring.security.studycorespringsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.core.spring.security.studycorespringsecurity.domain.entity.RoleHierarchy;
import study.core.spring.security.studycorespringsecurity.repository.RoleHierarchyRepository;
import study.core.spring.security.studycorespringsecurity.service.RoleHierarchyService;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    private final RoleHierarchyRepository roleHierarchyRepository;

    @Override
    public String findAllHierarchy() {

        List<RoleHierarchy> roleHierarchies = roleHierarchyRepository.findAll();
        StringBuilder sb = new StringBuilder();

        Iterator<RoleHierarchy> iterator = roleHierarchies.iterator();
        while (iterator.hasNext()) {
            RoleHierarchy next = iterator.next();
            if(next.getParentName() != null) {
                sb.append(next.getParentName().getChildName());
                sb.append(" > ");
                sb.append(next.getChildName());
                sb.append("\n");
            }
        }
        /**
         * 아래와 같은 문자열이 되도록
         * ROLE_ADMIN > ROLE_MANAGER\nROLE_MANAGER > ROLE_USER\n
         */
        return sb.toString();
    }
}
