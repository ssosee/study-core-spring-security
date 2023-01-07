package study.core.spring.security.studycorespringsecurity.sercurity.common;

import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 사용자가 전달하는 추가적인 파라미터를 저장
 */
@Getter
public class FormWebAuthenticationDetails extends WebAuthenticationDetails {

    private String secretKey;

    public FormWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        secretKey = request.getParameter("secret_key");
    }
}
