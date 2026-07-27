package com.reservehub.resource.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservehub.resource.domain.Resource;
import com.reservehub.resource.domain.ResourceRepository;
import com.reservehub.resource.domain.ResourceStatus;

@Service
@Transactional
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource register(String name, String description) {
        Resource resource = new Resource(name, description, ResourceStatus.AVAILABLE);
        return resourceRepository.save(resource);
    }

    public List<Resource> findAllResources(){
        return resourceRepository.findAll();
    }

    public Resource findById(Long id){
        return resourceRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 자원입니다."));
    }
}