package com.example.app.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.app.dto.IncomingComputerDTO;
import com.example.app.dto.OutgoingComputerDTO;
import com.example.app.dto.OutgoingFieldsWorkerDTO;
import com.example.app.service.ComputerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


public class ComputerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ComputerService computerService;

    @InjectMocks
    private ComputerController computerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(computerController).build();
    }

    @Test
    public void testCreateComputer() throws Exception {
        IncomingComputerDTO incomingDTO = new IncomingComputerDTO("1235");
        OutgoingComputerDTO outgoingDTO = new OutgoingComputerDTO(1l,"123456",new OutgoingFieldsWorkerDTO());

        when(computerService.saveComputer(any(IncomingComputerDTO.class))).thenReturn(outgoingDTO);

        mockMvc.perform(post("/computer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(outgoingDTO)));

        verify(computerService, times(1)).saveComputer(any(IncomingComputerDTO.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}