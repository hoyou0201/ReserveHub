package com.reservehub.resource.presentation;

import com.reservehub.resource.domain.Resource;
import com.reservehub.resource.domain.ResourceStatus;

public record ResourceResponse(
        Long id,
        String name,
        String description,
        ResourceStatus status
) {

    public static ResourceResponse from(Resource resource){
        return new ResourceResponse(
                        resource.getId(), 
                        resource.getName(),
                        resource.getDescription(),
                        resource.getStatus()
                    );
    }
} 
