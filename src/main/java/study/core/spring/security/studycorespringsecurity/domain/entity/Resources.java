package study.core.spring.security.studycorespringsecurity.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Resources {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_resources", joinColumns = {
            @JoinColumn(name = "resource_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roleSet = new HashSet<>();

    @Builder
    public Resources(Long id, String resourceName, String httpMethod, int orderNum, String resourceType, Set<Role> roleSet) {
        this.id = id;
        this.resourceName = resourceName;
        this.httpMethod = httpMethod;
        this.orderNum = orderNum;
        this.resourceType = resourceType;
        this.roleSet = roleSet;
    }

    public void changeResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void changeHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void changeOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public void changeResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public void changeRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }
}
