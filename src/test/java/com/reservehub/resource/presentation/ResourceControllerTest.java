package com.reservehub.resource.presentation;

import com.reservehub.resource.application.ResourceService;
import com.reservehub.resource.domain.Resource;
import com.reservehub.resource.domain.ResourceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResourceController.class)
class ResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private ResourceService resourceService;

    @Test
    void 자원을_등록한다() throws Exception {
        Resource resource = new Resource(
                "1번 스터디룸",
                "최대 6명 이용 가능",
                ResourceStatus.AVAILABLE
        );

        ResourceCreateRequest request = new ResourceCreateRequest(
                "1번 스터디룸",
                "최대 6명 이용 가능"
        );

        when(resourceService.register(
                "1번 스터디룸",
                "최대 6명 이용 가능"
        )).thenReturn(resource);

        mockMvc.perform(post("/api/resources")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("1번 스터디룸"))
                .andExpect(jsonPath("$.description").value("최대 6명 이용 가능"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));


        verify(resourceService).register(
            "1번 스터디룸",
            "최대 6명 이용 가능"
        );      
    }
}