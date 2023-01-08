package study.core.spring.security.studycorespringsecurity.sercurity.token;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AjaxAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -7404333774025070993L;
    private final Object principal;
    private Object credential;

    public AjaxAuthenticationToken(Object principal, Object credential) {
        super(null);
        this.principal = principal;
        this.credential = credential;
        setAuthenticated(false);
    }

    public AjaxAuthenticationToken(Object principal, Object credential, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credential = credential;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credential;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
