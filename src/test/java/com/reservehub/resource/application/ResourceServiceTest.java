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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

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
        List<Resource> resources = resourceService.findAllResources();

        //then
        assertThat(resources).hasSize(1);
        assertThat(resources.get(0).getName()).isEqualTo("1번 스터디룸");
        assertThat(resources.get(0).getDescription()).isEqualTo("최대 6명 입장 가능");
        assertThat(resources.get(0).getStatus()).isEqualTo(ResourceStatus.AVAILABLE);

        verify(resourceRepository).findAll();

    }

    @Test
    void 단일공유자원을_조회한다(){
        //given
        Resource resource = new Resource(
            "1번 스터디룸", 
            "최대 6명 입장 가능", 
            ResourceStatus.AVAILABLE
        );

        when(resourceRepository.findById(1l))
                .thenReturn(Optional.of(resource));
                
        //when
        Resource foundResource = resourceService.findById(1l);

        //then
        assertThat(foundResource.getName()).isEqualTo("1번 스터디룸");
        assertThat(foundResource.getDescription()).isEqualTo("최대 6명 입장 가능");
        assertThat(foundResource.getStatus()).isEqualTo(ResourceStatus.AVAILABLE);

        verify(resourceRepository).findById(1l);
    }

    @Test
    void 목록에_없는_단일공유자원을_조회하면_예외가_발생한다(){
        //given
        when(resourceRepository.findById(1L))
                .thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> resourceService.findById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 자원입니다.");

        verify(resourceRepository).findById(1l);
    }
}