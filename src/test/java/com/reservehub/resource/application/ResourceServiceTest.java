package com.reservehub.resource.application;

import com.reservehub.resource.domain.Resource;
import com.reservehub.resource.domain.ResourceRepository;
import com.reservehub.resource.domain.ResourceStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceService resourceService;

    @Test
    void 자원을_등록한다() {
        // given
        String name = "1번 스터디룸";
        String description = "최대 6명 이용 가능";

        when(resourceRepository.save(any(Resource.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Resource resource = resourceService.register(name, description);

        // then
        assertThat(resource.getName()).isEqualTo(name);
        assertThat(resource.getDescription()).isEqualTo(description);
        assertThat(resource.getStatus()).isEqualTo(ResourceStatus.AVAILABLE);
        verify(resourceRepository).save(any(Resource.class));
    }
}