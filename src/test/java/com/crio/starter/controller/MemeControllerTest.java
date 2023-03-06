package com.crio.starter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.net.URI;
import com.crio.starter.dto.MemeDto;
import com.crio.starter.service.MemeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

@AutoConfigureMockMvc
@SpringBootTest
public class MemeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemeService memeService;

    @Test
    void getMemeShouldReturnMemeDto() throws Exception{

        //given
        Mockito.doReturn(new MemeDto("1", "Yash Soni", "Hello", "URL"))
            .when(memeService).getMeme("1");

        //When
        // URI uri = UriComponentsBuilder
        //     .fromPath("/memes")
        //     .queryParam("memeId", "1")
        //     .build().toUri();

        URI uri = UriComponentsBuilder
            .fromHttpUrl("http://localhost:8081/memes/1")
            .build().toUri();

        MockHttpServletResponse response = mockMvc.perform(
            get(uri.toString()).accept(APPLICATION_JSON_VALUE)
        ).andReturn().getResponse();

        //then
        String responseStr = response.getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        MemeDto memeDto = mapper.readValue(responseStr, MemeDto.class);
        MemeDto res = new MemeDto("1", "Yash Soni", "Hello", "URL");

        assertEquals(memeDto, res);
        Mockito.verify(memeService, Mockito.times(1)).getMeme("1");
    }
    
}
