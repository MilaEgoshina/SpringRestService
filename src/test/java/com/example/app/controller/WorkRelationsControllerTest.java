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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

public class WorkRelationsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WorkRelationsService workRelationsService;

    @InjectMocks
    private WorkRelationsController workRelationsController;

    @BeforeEach
    public void setup() {

        workRelationsService = mock(WorkRelationsService.class);
        workRelationsController = new WorkRelationsController(workRelationsService);
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
    @Test
    public void testGetWorkRelationById() throws Exception {
        Long id = 1L;
        OutgoingWorkRelationsDTO outgoingDTO = new OutgoingWorkRelationsDTO(1L,"From one department",null);
        when(workRelationsService.findWorkRelationsById(id)).thenReturn(outgoingDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/workRelations/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(outgoingDTO)));

        verify(workRelationsService, times(1)).findWorkRelationsById(id);
    }

    @Test
    public void testGetAllWorkRelations() throws Exception {
        List<OutgoingWorkRelationsDTO> outgoingDTOs = new ArrayList<>();
        when(workRelationsService.findAllWorkRelations()).thenReturn(outgoingDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/workRelations/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(outgoingDTOs)));

        verify(workRelationsService, times(1)).findAllWorkRelations();
    }
    @Test
    public void testDeleteWorkRelationsById() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/workRelations/{id}", id))
                .andExpect(status().isNoContent());

        verify(workRelationsService, times(1)).deleteWorkRelationsById(id);
    }

    @Test
    public void testDeleteWorkRelations() throws Exception {
        Long id = 1L;
        Long workerId = 2L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/workRelations/{id}/deleteWorker/{workerId}", id, workerId))
                .andExpect(status().isNoContent());

        verify(workRelationsService, times(1)).deleteWorkerFromRelations(id, workerId);
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
