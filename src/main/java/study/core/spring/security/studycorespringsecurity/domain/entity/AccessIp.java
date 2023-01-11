package study.core.spring.security.studycorespringsecurity.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccessIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ipAddress;

    @Builder
    public AccessIp(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void changeIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
