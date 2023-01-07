package study.core.spring.security.studycorespringsecurity.sercurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String USER = "USER";
    public static final String MANAGER = "MANAGER";
    public static final String ADMIN = "ADMIN";

    private final UserDetailsService userDetailsService;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationDetailsSource authenticationDetailsSource;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AccessDeniedHandler accessDeniedHandler;

    /**
     * 메모리방식으로 권한 설정
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //메모리방식
//        String password = passwordEncoder().encode("123");
//
//        auth.inMemoryAuthentication().withUser(USER.toLowerCase()).password(password).roles(USER);
//        auth.inMemoryAuthentication().withUser(MANAGER.toLowerCase()).password(password).roles(MANAGER, USER);
//        auth.inMemoryAuthentication().withUser(ADMIN.toLowerCase()).password(password).roles(ADMIN, USER, MANAGER);

        // DB 사용 방식
//        auth.userDetailsService(customUserDetailsService);

        auth.authenticationProvider(authenticationProvider);

    }

    /**
     * 보안 필터 제외 설정
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 보안 필터 범위 밖에 존재
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/users", "/login**").permitAll() // 보안필터에서 검사는 받음
                .antMatchers("/mypage").hasRole(USER)
                .antMatchers("/messages").hasRole(MANAGER)
                .antMatchers("/config").hasRole(ADMIN)
                .anyRequest().authenticated();

        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_proc") // 로그인 form action url
                .defaultSuccessUrl("/")
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll();

        http
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }
}
