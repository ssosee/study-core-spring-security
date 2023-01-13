package study.core.spring.security.studycorespringsecurity.sercurity.filter;

import org.springframework.http.HttpMethod;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 인증 및 권한심사를 할 필요가 없는 자원( /, /home, /login ..)들을 미리 설정해서 바로 리소스 접근이 가능하게 하는 필터 구현
 * 권한심사 없이 바로 통과되도록 하는것이 핵심
 *
 * FilterSecurityInterceptor의 beforeInvocation(object)가 호출되면 권한심사를 하는 것
 */
public class PermitAllFilter extends FilterSecurityInterceptor {

    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
    private boolean observeOncePerRequest = true;
    private List<RequestMatcher> permitAllRequestMatchers = new ArrayList<>();

    public PermitAllFilter(String ... permitAllResources) {
        for(String resources : permitAllResources) {
            permitAllRequestMatchers.add(new AntPathRequestMatcher(resources));
        }
    }

    @Override
    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        if (isApplied(filterInvocation) && this.observeOncePerRequest) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            return;
        }
        // first time this request being called, so perform security checking
        if (filterInvocation.getRequest() != null && this.observeOncePerRequest) {
            filterInvocation.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
        }
        // 권한 심사가 필요없는 자원들을 미리 설정해서 바로 리소스 접근이 가능하게 하는 필터 설정
        InterceptorStatusToken token = beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        }
        finally {
            super.finallyInvocation(token);
        }
        super.afterInvocation(token, null);
    }

    private boolean isApplied(FilterInvocation filterInvocation) {
        return (filterInvocation.getRequest() != null)
                && (filterInvocation.getRequest().getAttribute(FILTER_APPLIED) != null);
    }

    @Override
    protected InterceptorStatusToken beforeInvocation(Object object) {

        boolean permitAll = false;
        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        // 권한 심사 여부 파악
        for (RequestMatcher requestMatcher : permitAllRequestMatchers) {
            if(requestMatcher.matches(request)) {
                permitAll = true;
                break;
            }
        }

        // 권한 심사 없이 바로 통과
        if(permitAll) {
            return null;
        }

        // 부모 클래스에 권한 심사 넘김
        return super.beforeInvocation(object);
    }
}
