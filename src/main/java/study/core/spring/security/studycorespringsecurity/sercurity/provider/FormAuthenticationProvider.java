package study.core.spring.security.studycorespringsecurity.sercurity.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import study.core.spring.security.studycorespringsecurity.sercurity.common.FormWebAuthenticationDetails;
import study.core.spring.security.studycorespringsecurity.sercurity.service.AccountContext;

@Slf4j
@Component
@RequiredArgsConstructor
public class FormAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 실제 인증에 관련된 로직
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // DB에서 조회
        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, accountContext.getPassword())) {
            log.error("[BadCredentialsException]");
            throw new BadCredentialsException("암호를 확인해주세요.");
        }

        FormWebAuthenticationDetails formWebAuthenticationDetails = (FormWebAuthenticationDetails) authentication.getDetails();
        String secretKey = formWebAuthenticationDetails.getSecretKey();
        if(secretKey == null || !"secret".equals(secretKey)) {
            log.error("[InsufficientAuthenticationException]");
            throw new InsufficientAuthenticationException("시크릿키를 확인해주세요.");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
