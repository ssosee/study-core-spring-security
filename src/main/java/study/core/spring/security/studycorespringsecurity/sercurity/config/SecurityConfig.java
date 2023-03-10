package study.core.spring.security.studycorespringsecurity.sercurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import study.core.spring.security.studycorespringsecurity.sercurity.factory.UrlResourceMapFactoryBean;
import study.core.spring.security.studycorespringsecurity.sercurity.filter.PermitAllFilter;
import study.core.spring.security.studycorespringsecurity.sercurity.handler.CustomAccessDeniedHandler;
import study.core.spring.security.studycorespringsecurity.sercurity.handler.CustomAuthenticationFailureHandler;
import study.core.spring.security.studycorespringsecurity.sercurity.handler.CustomAuthenticationSuccessHandler;
import study.core.spring.security.studycorespringsecurity.sercurity.metadatsource.UrlFilterInvocationSecurityMetadataSource;
import study.core.spring.security.studycorespringsecurity.sercurity.provider.FormAuthenticationProvider;
import study.core.spring.security.studycorespringsecurity.sercurity.voter.IpAddressVoter;
import study.core.spring.security.studycorespringsecurity.service.SecurityResourceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Order(0)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String USER = "USER";
    public static final String MANAGER = "MANAGER";
    public static final String ADMIN = "ADMIN";

    private final UserDetailsService userDetailsService;
    private final FormAuthenticationProvider formAuthenticationProvider;
    private final AuthenticationDetailsSource authenticationDetailsSource;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final SecurityResourceService securityResourceService;
    private String[] permitAllResources = {"/", "/login", "/user/login/**"};


    /**
     * ?????? ??????
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //???????????????
//        String password = passwordEncoder().encode("123");
//
//        auth.inMemoryAuthentication().withUser(USER.toLowerCase()).password(password).roles(USER);
//        auth.inMemoryAuthentication().withUser(MANAGER.toLowerCase()).password(password).roles(MANAGER, USER);
//        auth.inMemoryAuthentication().withUser(ADMIN.toLowerCase()).password(password).roles(ADMIN, USER, MANAGER);

        // DB ?????? ??????
//        auth.userDetailsService(customUserDetailsService);

        auth.authenticationProvider(formAuthenticationProvider);

    }

    /**
     * ?????? ?????? ?????? ??????
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // ?????? ?????? ?????? ?????? ??????
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/users", "/login**").permitAll() // ?????????????????? ????????? ??????
                .antMatchers("/mypage").hasRole(USER)
                .antMatchers("/messages").hasRole(MANAGER)
                .antMatchers("/config").hasRole(ADMIN)
                .anyRequest().authenticated();

        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_proc") // ????????? form action url
                .defaultSuccessUrl("/")
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .permitAll();

        http
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler);

        http
                .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class); // ?????? ???????????? ??????
    }

    // ?????? ??????
    @Bean
    public PermitAllFilter customFilterSecurityInterceptor() throws Exception {
        PermitAllFilter permitAllFilter = new PermitAllFilter(permitAllResources);
        permitAllFilter.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource()); // ?????? ??????
        permitAllFilter.setAccessDecisionManager(affirmativeBased()); // ????????????
        permitAllFilter.setAuthenticationManager(authenticationManagerBean()); // ????????????

        return permitAllFilter;
    }

    @Bean
    public FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() throws Exception {
        return new UrlFilterInvocationSecurityMetadataSource(urlResourcesMapFactoryBean().getObject(), securityResourceService);
    }

    private UrlResourceMapFactoryBean urlResourcesMapFactoryBean() {
        UrlResourceMapFactoryBean urlResourceMapFactoryBean = new UrlResourceMapFactoryBean();
        urlResourceMapFactoryBean.setSecurityResourceService(securityResourceService);

        return urlResourceMapFactoryBean;
    }

    @Bean
    public AccessDecisionManager affirmativeBased() {
        AffirmativeBased affirmativeBased = new AffirmativeBased(getAccessDecisionVoters());
        return affirmativeBased;
    }


    /*
        List<Object>??? List<? extends Object>??? ?????????.
        List<Number>??? List<? extends Object>??? ?????????????????? ?????????,
        List<Object>??? ??????????????? ?????? ?????????.
     */
    @Bean
    public List<AccessDecisionVoter<?>> getAccessDecisionVoters() {

        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(new IpAddressVoter(securityResourceService)); // ipAddress voter??? ?????? ???????????????.
        accessDecisionVoters.add(roleVoter());
        //accessDecisionVoters.add(new RoleVoter()); // roleHierarchy ?????? ??????

        return accessDecisionVoters;
    }

    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter() {
        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
        return roleHierarchyVoter;
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        return roleHierarchy;
    }
}
