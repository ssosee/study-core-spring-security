package study.core.spring.security.studycorespringsecurity.service;

import study.core.spring.security.studycorespringsecurity.domain.entity.Resources;

import java.util.List;

public interface ResourcesService {
    Resources getResources(Long id);
    List<Resources> getResources();
    void createResources(Resources resources);
    void deleteResources(Long id);
}
