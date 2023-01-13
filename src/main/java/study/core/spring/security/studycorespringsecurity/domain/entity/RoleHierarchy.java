package study.core.spring.security.studycorespringsecurity.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoleHierarchy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String childName;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_name", referencedColumnName = "childName")
    private RoleHierarchy parentName;
    @OneToMany(mappedBy = "parentName", cascade = CascadeType.ALL)
    private Set<RoleHierarchy> roleHierarchy = new HashSet<>();

    @Builder
    public RoleHierarchy(String childName, RoleHierarchy parentName, Set<RoleHierarchy> roleHierarchy) {
        this.childName = childName;
        this.parentName = parentName;
        this.roleHierarchy = roleHierarchy;
    }

    public void changeChildName(String childName) {
        this.childName = childName;
    }

    public void changeParentName(RoleHierarchy parentName) {
        this.parentName = parentName;
    }

    public void changeRoleHierarchy(Set<RoleHierarchy> roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }
}
