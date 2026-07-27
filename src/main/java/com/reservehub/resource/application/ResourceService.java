package com.reservehub.resource.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservehub.resource.domain.Resource;
import com.reservehub.resource.domain.ResourceRepository;
import com.reservehub.resource.domain.ResourceStatus;
import com.reservehub.resource.presentation.ResourceResponse;

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

    public List<ResourceResponse> findAllResources(){
        List<Resource> resources = resourceRepository.findAll();
        return resources.stream()
                    .map(ResourceResponse::from)
                    .toList();
    }

    public ResourceResponse findById(Long id){
        Resource resource = resourceRepository.findById(id)
                    .orElseThrow();;
        return ResourceResponse.from(resource);
    }
}