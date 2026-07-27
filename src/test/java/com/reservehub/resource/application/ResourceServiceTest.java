package com.reservehub.resource.application;

import com.reservehub.resource.domain.Resource;
import com.reservehub.resource.domain.ResourceRepository;
import com.reservehub.resource.domain.ResourceStatus;
import com.reservehub.resource.presentation.ResourceResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

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

    @Test
    void 공유자원목록을_조회한다() {
        //given
        Resource resource = new Resource(
            "1번 스터디룸", 
            "최대 6명 입장 가능", 
            ResourceStatus.AVAILABLE
        );

        when(resourceRepository.findAll())
                .thenReturn(List.of(resource));
                
        //when
        List<ResourceResponse> resources = resourceService.findAllResources();

        //then
        assertThat(resources).hasSize(1);
        assertThat(resources.get(0).name()).isEqualTo("1번 스터디룸");
        assertThat(resources.get(0).description()).isEqualTo("최대 6명 입장 가능");
        assertThat(resources.get(0).status()).isEqualTo(ResourceStatus.AVAILABLE);

        verify(resourceRepository).findAll();


    }
}