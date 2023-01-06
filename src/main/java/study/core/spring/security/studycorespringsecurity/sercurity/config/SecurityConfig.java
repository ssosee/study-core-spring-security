package study.core.spring.security.studycorespringsecurity.sercurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import study.core.spring.security.studycorespringsecurity.sercurity.service.CustomUserDetailsService;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String USER = "ROLE_USER";
    public static final String MANAGER = "ROLE_MANAGER";
    public static final String ADMIN = "ROLE_ADMIN";

    private final CustomUserDetailsService customUserDetailsService;

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
        auth.userDetailsService(customUserDetailsService);

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
                .antMatchers("/", "/users").permitAll() // 보안필터에서 검사는 받음
                .antMatchers("/mypage").hasRole(USER)
                .antMatchers("/messages").hasRole(MANAGER)
                .antMatchers("/config").hasRole(ADMIN)
                .anyRequest().authenticated();

        http
                .formLogin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
