package study.core.spring.security.studycorespringsecurity.sercurity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import study.core.spring.security.studycorespringsecurity.domain.Account;
import study.core.spring.security.studycorespringsecurity.domain.AccountDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Account account = (Account) authentication.getPrincipal();
        AccountDto dto = AccountDto.builder()
                .age(account.getAge())
                .email(account.getEmail())
                .password(account.getPassword())
                .role(account.getRole())
                .username(account.getUsername())
                .build();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        mapper.writeValue(response.getWriter(), dto);
    }
}
