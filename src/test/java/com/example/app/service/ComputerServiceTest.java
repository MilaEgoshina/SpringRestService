package com.example.app.service;

import com.example.app.dto.IncomingComputerDTO;
import com.example.app.dto.OutgoingComputerDTO;
import com.example.app.dto.UpdateComputerDTO;
import com.example.app.entity.Computer;
import com.example.app.exceptions.NotFoundException;
import com.example.app.mapper.ComputerMapper;
import com.example.app.repository.ComputerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComputerServiceTest {

    @Mock
    private ComputerRepository computerRepository;

    @Mock
    private ComputerMapper computerMapper;

    @InjectMocks
    private ComputerService computerService;

    @Test
    void saveComputer() {
        // Arrange
        IncomingComputerDTO incomingComputerDTO = new IncomingComputerDTO();
        Computer computer = new Computer();
        OutgoingComputerDTO outgoingComputerDTO = new OutgoingComputerDTO();

        when(computerMapper.mapToEntity(incomingComputerDTO)).thenReturn(computer);
        when(computerRepository.save(computer)).thenReturn(computer);
        when(computerMapper.mapToDTO(computer)).thenReturn(outgoingComputerDTO);

        // Act
        OutgoingComputerDTO result = computerService.saveComputer(incomingComputerDTO);

        // Assert
        assertEquals(outgoingComputerDTO, result);
        verify(computerMapper, times(1)).mapToEntity(incomingComputerDTO);
        verify(computerRepository, times(1)).save(computer);
        verify(computerMapper, times(1)).mapToDTO(computer);
    }

    @Test
    void updateComputer() throws NotFoundException {
        // Arrange
        UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(1L);
        Computer computer = new Computer();
        computer.setId(1L);

        when(computerRepository.findById(updateComputerDTO.getId())).thenReturn(Optional.of(computer));
        when(computerMapper.update(updateComputerDTO)).thenReturn(computer);

        // Act
        computerService.updateComputer(updateComputerDTO);

        // Assert
        verify(computerRepository, times(1)).findById(updateComputerDTO.getId());
        verify(computerMapper, times(1)).update(updateComputerDTO);
        verify(computerRepository, times(1)).save(computer);
    }

    @Test
    void updateComputer_notFound() {
        // Arrange
        UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(1L);
        when(computerRepository.findById(updateComputerDTO.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> computerService.updateComputer(updateComputerDTO));
        verify(computerRepository, times(1)).findById(updateComputerDTO.getId());
    }

    @Test
    void findComputerById() throws NotFoundException {
        // Arrange
        Long id = 1L;
        Computer computer = new Computer();
        computer.setId(id);
        OutgoingComputerDTO outgoingComputerDTO = new OutgoingComputerDTO();

        when(computerRepository.findById(id)).thenReturn(Optional.of(computer));
        when(computerMapper.mapToDTO(computer)).thenReturn(outgoingComputerDTO);

        // Act
        OutgoingComputerDTO result = computerService.findComputerById(id);

        // Assert
        assertEquals(outgoingComputerDTO, result);
        verify(computerRepository, times(1)).findById(id);
        verify(computerMapper, times(1)).mapToDTO(computer);
    }

    @Test
    void findComputerById_notFound() {
        // Arrange
        Long id = 1L;
        when(computerRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> computerService.findComputerById(id));
        verify(computerRepository, times(1)).findById(id);
    }
}
