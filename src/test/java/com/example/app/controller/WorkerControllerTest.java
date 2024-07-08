package com.example.app.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.app.dto.IncomingWorkerDTO;
import com.example.app.dto.OutgoingFullWorkerDTO;
import com.example.app.entity.Role;
import com.example.app.service.WorkerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class WorkerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WorkerService workerService;

    @InjectMocks
    private WorkerController workerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(workerController).build();
    }

    @Test
    public void testCreateWorker() throws Exception {
        IncomingWorkerDTO incomingDTO = new IncomingWorkerDTO("John","Brown",new Role());
        OutgoingFullWorkerDTO outgoingDTO = new OutgoingFullWorkerDTO(1L,"Nike","Stark",null,null,null);

        when(workerService.saveWorker(any(IncomingWorkerDTO.class))).thenReturn(outgoingDTO);

        mockMvc.perform(post("/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(outgoingDTO)));

        verify(workerService, times(1)).saveWorker(any(IncomingWorkerDTO.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
