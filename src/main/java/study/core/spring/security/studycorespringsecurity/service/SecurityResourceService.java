package study.core.spring.security.studycorespringsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import study.core.spring.security.studycorespringsecurity.domain.entity.AccessIp;
import study.core.spring.security.studycorespringsecurity.domain.entity.Resources;
import study.core.spring.security.studycorespringsecurity.repository.AccessIpRepository;
import study.core.spring.security.studycorespringsecurity.repository.ResourcesRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityResourceService {
    private final ResourcesRepository resourcesRepository;
    private final AccessIpRepository accessIpRepository;

    public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> getResourceList() {

        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> result = new LinkedHashMap<>();

        List<Resources> resourcesList = resourcesRepository.findAllResources();

        // DB에 resource 전체 조회
        resourcesList.forEach(re -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            re.getRoleSet().forEach(role -> {
                configAttributeList.add(new SecurityConfig(role.getRoleName()));
                result.put(new AntPathRequestMatcher(re.getResourceName()), configAttributeList);
            });
        });

        return result;
    }

    public List<String> getAccessIpList() {
        return accessIpRepository.findAll().stream()
                .map(AccessIp::getIpAddress)
                .collect(Collectors.toList());
    }
}
