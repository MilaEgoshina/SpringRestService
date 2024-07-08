package com.example.app.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.app.dto.IncomingRoleDTO;
import com.example.app.dto.OutgoingRoleDTO;
import com.example.app.exceptions.NotFoundException;
import com.example.app.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

public class RoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    private ObjectMapper objectMapper;


    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void setup() {

        roleService = mock(RoleService.class);
        roleController = new RoleController(roleService);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateRole() throws Exception {
        IncomingRoleDTO incomingRoleDTO = new IncomingRoleDTO("Manager");
        OutgoingRoleDTO outgoingRoleDTO = new OutgoingRoleDTO(1l,"HR");

        when(roleService.saveRole(any(IncomingRoleDTO.class))).thenReturn(outgoingRoleDTO);

        mockMvc.perform(post("/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingRoleDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(outgoingRoleDTO)));

        verify(roleService, times(1)).saveRole(any(IncomingRoleDTO.class));
    }

    @Test
    public void testGetAllRoles() throws Exception {
        List<OutgoingRoleDTO> roleDTOS = Collections.singletonList(new OutgoingRoleDTO());
        when(roleService.findAllRoles()).thenReturn(roleDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get("/role/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(roleDTOS)));

        verify(roleService, times(1)).findAllRoles();
    }

    @Test
    public void testGetRoleById() throws Exception {
        Long id = 1L;
        OutgoingRoleDTO roleDTO = new OutgoingRoleDTO(1L,"Director");
        when(roleService.findRoleById(id)).thenReturn(roleDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/role/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(roleDTO)));

        verify(roleService, times(1)).findRoleById(id);
    }

    @Test
    public void testGetRoleByIdNotFound() throws Exception {
        Long id = 1L;
        when(roleService.findRoleById(id)).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/role/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(roleService, times(1)).findRoleById(id);
    }

    @Test
    public void testCreateRoleBadRequest() throws Exception {
        IncomingRoleDTO incomingRoleDTO = new IncomingRoleDTO("Office Manager");
        when(roleService.saveRole(any(IncomingRoleDTO.class))).thenThrow(Exception.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(incomingRoleDTO)))
                .andExpect(status().isBadRequest());

        verify(roleService, times(1)).saveRole(any(IncomingRoleDTO.class));
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}