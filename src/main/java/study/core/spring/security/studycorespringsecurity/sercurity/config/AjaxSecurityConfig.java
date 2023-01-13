package study.core.spring.security.studycorespringsecurity.sercurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.core.spring.security.studycorespringsecurity.sercurity.common.AjaxLoginAuthenticationEntryPoint;
import study.core.spring.security.studycorespringsecurity.sercurity.filter.AjaxLoginProcessingFilter;
import study.core.spring.security.studycorespringsecurity.sercurity.handler.AjaxAccessDeniedHandler;
import study.core.spring.security.studycorespringsecurity.sercurity.handler.AjaxAuthenticationFailureHandler;
import study.core.spring.security.studycorespringsecurity.sercurity.handler.AjaxAuthenticationSuccessHandler;
import study.core.spring.security.studycorespringsecurity.sercurity.provider.AjaxAuthenticationProvider;

@Order(1)
@EnableWebSecurity
@RequiredArgsConstructor
public class AjaxSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AjaxAuthenticationProvider ajaxAuthenticationProvider;
    private final AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
    private final AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;
    private final AjaxLoginAuthenticationEntryPoint ajaxLoginAuthenticationEntryPoint;
    private final AjaxAccessDeniedHandler ajaxAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ajaxAuthenticationProvider);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AbstractAuthenticationProcessingFilter getAjaxLoginProcessingFilter() throws Exception {

        AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();

        ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
        ajaxLoginProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);

        return ajaxLoginProcessingFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers("/api/message").hasRole("MANAGER")
                .anyRequest().authenticated();

        http
                .addFilterBefore(getAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

//        http
//                .csrf().disable();

        http
                .exceptionHandling()
                .authenticationEntryPoint(ajaxLoginAuthenticationEntryPoint)
                .accessDeniedHandler(ajaxAccessDeniedHandler);

        //customConfigureAjax(http);
    }

    /**
     * DSL로 설정
     */
    private void customConfigureAjax(HttpSecurity http) throws Exception {
        http
                .apply(new AjaxLoginConfigurer<>())
                .successHandlerAjax(ajaxAuthenticationSuccessHandler)
                .failureHandlerAjax(ajaxAuthenticationFailureHandler)
                .setAuthenticationManager(authenticationManagerBean())
                .createLoginProcessingUrlMatcher("/api/login");
    }
}
