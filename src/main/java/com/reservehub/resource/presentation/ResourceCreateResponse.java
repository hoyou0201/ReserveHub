package com.reservehub.resource.presentation;

import java.time.LocalDateTime;

import com.reservehub.resource.domain.Resource;
import com.reservehub.resource.domain.ResourceStatus;

public record ResourceCreateResponse(
        Long id,
        String name,
        String description,
        ResourceStatus status,
        LocalDateTime createdAt
) {

    public static ResourceCreateResponse from(Resource resource) {
        return new ResourceCreateResponse(
            resource.getId(), 
            resource.getName(),
            resource.getDescription(), 
            resource.getStatus(), 
            resource.getCreatedAt()
        );
    }
}