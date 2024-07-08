package com.example.app.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.service.WorkRelationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class WorkRelationsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WorkRelationsService workRelationsService;

    @InjectMocks
    private WorkRelationsController workRelationsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(workRelationsController).build();
    }

    @Test
    public void testCreateWorkRelations() throws Exception {
        IncomingWorkRelationsDTO incomingDTO = new IncomingWorkRelationsDTO("From one department");
        OutgoingWorkRelationsDTO outgoingDTO = new OutgoingWorkRelationsDTO(2L,"Friends",null);

        when(workRelationsService.saveWorkRelations(any(IncomingWorkRelationsDTO.class))).thenReturn(outgoingDTO);

        mockMvc.perform(post("/workRelations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(outgoingDTO)));

        verify(workRelationsService, times(1)).saveWorkRelations(any(IncomingWorkRelationsDTO.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
