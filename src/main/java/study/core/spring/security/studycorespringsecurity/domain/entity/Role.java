package study.core.spring.security.studycorespringsecurity.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    private String roleDesc;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleSet")
    @OrderBy("orderNum desc")
    private Set<Resources> resourcesSet = new LinkedHashSet<>();

    @Builder
    public Role(String roleName, String roleDesc, Set<Resources> resourcesSet) {
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.resourcesSet = resourcesSet;
    }

    public void changeRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void changeRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public void changeResourcesSet(Set<Resources> resourcesSet) {
        this.resourcesSet = resourcesSet;
    }
}
