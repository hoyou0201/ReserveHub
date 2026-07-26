package com.reservehub.resource.presentation;

public record ResourceCreateRequest(
        String name,
        String description
) {
}