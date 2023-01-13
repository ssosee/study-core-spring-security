package study.core.spring.security.studycorespringsecurity.sercurity.metadatsource;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.CollectionUtils;
import study.core.spring.security.studycorespringsecurity.service.SecurityResourceService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 권한 정보 조회 클래스(DB 연동)
 */
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    /**
     * e.g)
     *      key: /admin
     *      value: ROLE_ADMIN, ROLE_MANAGER
     */
    private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();
    private DefaultFilterInvocationSecurityMetadataSource defaultFilterInvocationSecurityMetadataSource = new DefaultFilterInvocationSecurityMetadataSource(requestMap);
    private SecurityResourceService securityResourceService;

    public UrlFilterInvocationSecurityMetadataSource(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap,
                                                     SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
        this.requestMap = requestMap;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        // 사용자가 어떤 url로 요청했는지 추출
        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        // requestMap.put(new AntPathRequestMatcher("/mypage"), Arrays.asList(new SecurityConfig("ROLE_USER")));

        if(!CollectionUtils.isEmpty(requestMap)) {
            // 전체 출력
            for(Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entries : requestMap.entrySet()) {
                RequestMatcher matcher = entries.getKey();
                // url 정보 비교(DB정보, 사용자 요청 정보 비교)
                if(matcher.matches(request)) {
                    return entries.getValue(); // 권한 정보 리턴
                }
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return defaultFilterInvocationSecurityMetadataSource.getAllConfigAttributes();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return defaultFilterInvocationSecurityMetadataSource.supports(clazz);
    }

    /**
     * DB정보 실시간 반영
     */
    public void reload() {
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> reloadedMap = securityResourceService.getResourceList();
        Iterator<Map.Entry<RequestMatcher, Collection<ConfigAttribute>>> iterator = reloadedMap.entrySet().iterator();

        requestMap.clear();

        while (iterator.hasNext()) {
            Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry = iterator.next();
            requestMap.put(entry.getKey(), entry.getValue());
        }
    }
}
