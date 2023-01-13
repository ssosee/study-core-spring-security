package study.core.spring.security.studycorespringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.core.spring.security.studycorespringsecurity.repository.AccessIpRepository;
import study.core.spring.security.studycorespringsecurity.repository.ResourcesRepository;
import study.core.spring.security.studycorespringsecurity.service.SecurityResourceService;

@Configuration
public class AppConfig {
    @Bean
    public SecurityResourceService securityResourceService(ResourcesRepository resourcesRepository, AccessIpRepository accessIpRepository) {
        SecurityResourceService securityResourceService = new SecurityResourceService(resourcesRepository, accessIpRepository);
        return securityResourceService;
    }
}
