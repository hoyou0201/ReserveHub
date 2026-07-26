package com.reservehub.resource.presentation;

import com.reservehub.resource.application.ResourceService;
import com.reservehub.resource.domain.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    public ResponseEntity<ResourceCreateResponse> register(
            @RequestBody ResourceCreateRequest request
    ) {
        Resource resource = resourceService.register(request.name(), request.description());
        ResourceCreateResponse response = ResourceCreateResponse.from(resource);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }
}