package study.core.spring.security.studycorespringsecurity.sercurity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errMsg = "비밀번호 또는 아이디를 확인해주세요.";
        String encodeErrMsg = URLEncoder.encode(errMsg, StandardCharsets.UTF_8);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if(exception instanceof BadCredentialsException) {
            log.error("비밀번호 오류");
        } else if (exception instanceof DisabledException) {
            log.error("비활성화 계정");
        } else if (exception instanceof CredentialsExpiredException) {
            log.error("비밀번호 만료");
        }

        mapper.writeValue(response.getWriter(), encodeErrMsg);
    }
}
