package study.core.spring.security.studycorespringsecurity.sercurity.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.MapUtils;
import study.core.spring.security.studycorespringsecurity.service.SecurityResourceService;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DB로 부터 얻은 권한/자원 정보를 ResourceMap 을 빈으로 생성해서
 * UrlFilterInvocationSecurityMetadataSource 에 전달
 */
public class UrlResourceMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>> {

    private SecurityResourceService securityResourceService;
    private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> resourceMap;

    public void setSecurityResourceService(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    @Override
    public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> getObject() throws Exception {

        if(CollectionUtils.isEmpty(resourceMap)) {
            init();
        }

        return resourceMap;
    }

    private void init() {
       resourceMap = securityResourceService.getResourceList();
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
