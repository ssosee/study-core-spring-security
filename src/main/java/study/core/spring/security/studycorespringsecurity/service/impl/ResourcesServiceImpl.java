package study.core.spring.security.studycorespringsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.core.spring.security.studycorespringsecurity.domain.entity.Resources;
import study.core.spring.security.studycorespringsecurity.repository.ResourcesRepository;
import study.core.spring.security.studycorespringsecurity.service.ResourcesService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResourcesServiceImpl implements ResourcesService {

    private final ResourcesRepository resourcesRepository;

    @Override
    public Resources getResources(Long id) {
        return resourcesRepository.findById(id).orElseThrow(() -> new IllegalStateException("Resources가 없습니다."));
    }

    @Override
    public List<Resources> getResources() {
        return resourcesRepository.findAll(Sort.by(Sort.Order.asc("orderNum")));
    }

    @Transactional
    @Override
    public void createResources(Resources resources) {
        resourcesRepository.save(resources);
    }

    @Transactional
    @Override
    public void deleteResources(Long id) {
        resourcesRepository.deleteById(id);
    }
}
