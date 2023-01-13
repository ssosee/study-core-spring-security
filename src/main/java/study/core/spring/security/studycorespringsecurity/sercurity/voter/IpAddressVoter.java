package study.core.spring.security.studycorespringsecurity.sercurity.voter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import study.core.spring.security.studycorespringsecurity.service.SecurityResourceService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class IpAddressVoter implements AccessDecisionVoter<Object> {

    private final SecurityResourceService securityResourceService;

    public IpAddressVoter(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * 특정한 IP 만 접근이 가능하도록 심의하는 Voter 추가
     * Voter 중에서 가장 먼저 심사하도록 하여 허용된 IP 일 경우에만 최종 승인 및 거부 결정을 하도록 한다
     * ****************************************************************************************************
     * 허용된 IP 이면 ACCESS_GRANTED(접근 허용) 가 아닌 ACCESS_ABSTAIN(접근 보류) 을 리턴해서 추가 심의를 계속 진행하도록 한다.
     * 허용된 IP 가 아니면 ACCESS_DENIED(접근 거부) 를 리턴하지 않고 즉시 예외 발생하여 최종 자원 접근 거부한다.
     */
    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {

        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();

        List<String> accessIpList = securityResourceService.getAccessIpList();

        int result = ACCESS_DENIED;

        /**
         * AccessDecisionVoter(판단을심사하는것(위원))
         *
         * 결정 방식
         *  ACCESS_GRANTED : 접근 허용(1)
         *  ACCESS_DENIED  : 접근 거부(-1)
         *  ACCESS_ABSTAIN : 접근 보류(0)
         */
        for(String ipAddress : accessIpList) {
            if(remoteAddress.equals(ipAddress)) {
                return ACCESS_ABSTAIN; // 접근 보류로 리턴하여 추가 심의 진행
            }
        }

        if(result == ACCESS_DENIED) {
            throw new AccessDeniedException("Invalid IpAddress");
        }

        return result;
    }
}
