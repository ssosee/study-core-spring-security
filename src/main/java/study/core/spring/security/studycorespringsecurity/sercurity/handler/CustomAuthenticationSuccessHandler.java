package study.core.spring.security.studycorespringsecurity.sercurity.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <a href="https://www.inflearn.com/questions/193737/defaultsuccessurl-%EC%9E%91%EB%8F%99-%EC%88%9C%EC%84%9C">defaultSuccessUrl 작동 순서</a>
 */
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * defaultSuccessUrl 은 로그인 성공 후에 이동할 경로를 정해주는 것은 맞지만 우선순위는 가장 마지막 입니다.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        /**
         * RequestCache requestCache = new HttpSessionRequestCache(); // 요청 캐시와 관련된 작업
         * final HttpSession session = request.getSession(false); // 세션 관련 작업
         * Object principal = authentication.getPrincipal(); // 인증된 사용자 관련작업
         * redirectStrategy.sendRedirect(request, response, targetUrl); // 인증 성공 후 이동
         **/

        setDefaultTargetUrl("/");

        // 로그인 전에 사용자가 요청했던 정보를 갖고 있음
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }
    }
}
