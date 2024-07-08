package com.example.app.service;

import com.example.app.dto.IncomingRoleDTO;
import com.example.app.dto.OutgoingRoleDTO;
import com.example.app.dto.UpdateRoleDTO;
import com.example.app.entity.Role;
import com.example.app.exceptions.NotFoundException;
import com.example.app.mapper.RoleMapper;
import com.example.app.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    @Test
    void saveRole() {
        // Arrange
        IncomingRoleDTO incomingRoleDTO = new IncomingRoleDTO();
        Role role = new Role();
        OutgoingRoleDTO outgoingRoleDTO = new OutgoingRoleDTO();

        when(roleMapper.mapToEntity(incomingRoleDTO)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.mapToDTO(role)).thenReturn(outgoingRoleDTO);

        // Act
        OutgoingRoleDTO result = roleService.saveRole(incomingRoleDTO);

        // Assert
        assertEquals(outgoingRoleDTO, result);
        verify(roleMapper, times(1)).mapToEntity(incomingRoleDTO);
        verify(roleRepository, times(1)).save(role);
        verify(roleMapper, times(1)).mapToDTO(role);
    }

    @Test
    void updateRole() throws NotFoundException {
        // Arrange
        UpdateRoleDTO updateRoleDTO = new UpdateRoleDTO();
        updateRoleDTO.setId(1L);
        Role role = new Role();
        role.setId(1L);

        when(roleRepository.findById(updateRoleDTO.getId())).thenReturn(Optional.of(role));
        when(roleMapper.update(updateRoleDTO)).thenReturn(role);

        // Act
        roleService.updateRole(updateRoleDTO);

        // Assert
        verify(roleRepository, times(1)).findById(updateRoleDTO.getId());
        verify(roleMapper, times(1)).update(updateRoleDTO);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void updateRoleNotFound() {
        // Arrange
        UpdateRoleDTO updateRoleDTO = new UpdateRoleDTO();
        updateRoleDTO.setId(1L);

        when(roleRepository.findById(updateRoleDTO.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> roleService.updateRole(updateRoleDTO));
    }

    @Test
    void findRoleById() throws NotFoundException {
        // Arrange
        Long id = 1L;
        Role role = new Role();
        role.setId(id);
        OutgoingRoleDTO outgoingRoleDTO = new OutgoingRoleDTO();

        when(roleRepository.findById(id)).thenReturn(Optional.of(role));
        when(roleMapper.mapToDTO(role)).thenReturn(outgoingRoleDTO);

        // Act
        OutgoingRoleDTO result = roleService.findRoleById(id);

        // Assert
        assertEquals(outgoingRoleDTO, result);
        verify(roleRepository, times(1)).findById(id);
        verify(roleMapper, times(1)).mapToDTO(role);
    }

    @Test
    void findRoleByIdNotFound() {
        // Arrange
        Long id = 1L;

        when(roleRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> roleService.findRoleById(id));
    }

    @Test
    void findAllRoles() {
        // Arrange
        List<Role> roleList = List.of(new Role(), new Role());
        List<OutgoingRoleDTO> outgoingRoleDTOs = List.of(new OutgoingRoleDTO(), new OutgoingRoleDTO());

        when(roleRepository.findAll()).thenReturn(roleList);
        when(roleMapper.mapToOutGoingDtos(roleList)).thenReturn(outgoingRoleDTOs);

        // Act
        List<OutgoingRoleDTO> result = roleService.findAllRoles();

        // Assert
        assertEquals(outgoingRoleDTOs, result);
        verify(roleRepository, times(1)).findAll();
        verify(roleMapper, times(1)).mapToOutGoingDtos(roleList);
    }

    @Test
    public void deleteRoleById_ExistingRole() throws NotFoundException {
        Long id = 1L;
        Optional<Role> optionalRole = Optional.of(new Role());

        Mockito.when(roleRepository.findById(id)).thenReturn(optionalRole);

        roleService.deleteRoleById(id);

        verify(roleRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteRoleById_NonExistingRole() {
        Long id = 1L;
        Mockito.when(roleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleService.deleteRoleById(id));

        verify(roleRepository, times(0)).deleteById(id);
    }
}