package com.example.app.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.app.dto.IncomingWorkerDTO;
import com.example.app.dto.OutgoingFullWorkerDTO;
import com.example.app.dto.UpdateWorkerDTO;
import com.example.app.entity.Role;
import com.example.app.service.WorkerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

public class WorkerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WorkerService workerService;

    private ObjectMapper objectMapper;

    @InjectMocks
    private WorkerController workerController;

    @BeforeEach
    public void setup() {

        workerService = mock(WorkerService.class);
        workerController = new WorkerController(workerService);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(workerController).build();
        objectMapper = new ObjectMapper();
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

    @Test
    void testUpdateWorker() throws Exception {
        UpdateWorkerDTO updateWorkerDTO = new UpdateWorkerDTO(1L,"Mike","Ann",null,null,null);

        mockMvc.perform(MockMvcRequestBuilders.put("/worker/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateWorkerDTO)))
                .andExpect(status().isOk());

        verify(workerService, times(1)).updateWorker(updateWorkerDTO);
    }

    @Test
    void testGetAllWorkers() throws Exception {
        List<OutgoingFullWorkerDTO> workerDTOList = new ArrayList<>();

        Mockito.when(workerService.findAllWorker()).thenReturn(workerDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/worker/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(workerDTOList)));

        verify(workerService, times(1)).findAllWorker();
    }

    @Test
    void testGetWorkerById() throws Exception {
        Long id = 1L;
        OutgoingFullWorkerDTO workerDTO = new OutgoingFullWorkerDTO(1L,"Anton","Juice",null,null,null);

        Mockito.when(workerService.findWorkerById(id)).thenReturn(workerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/worker/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(workerDTO)));

        verify(workerService, times(1)).findWorkerById(id);
    }

    @Test
    void testDeleteWorker() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/worker/{id}", id))
                .andExpect(status().isNoContent());

        verify(workerService, times(1)).deleteWorkerById(id);
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
