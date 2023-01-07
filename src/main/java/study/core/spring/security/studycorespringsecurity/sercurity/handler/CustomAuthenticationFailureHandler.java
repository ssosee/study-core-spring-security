package study.core.spring.security.studycorespringsecurity.sercurity.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "아이디 또는 비밀번호를 확인해주세요.";
        String encodeErrorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);

        if(exception instanceof BadCredentialsException) {
            log.error("비밀번호 오류");
        } else if (exception instanceof InsufficientAuthenticationException) {
            log.error("시크릿키 오류");
        }
        setDefaultFailureUrl("/login?error=true&exception="+encodeErrorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
