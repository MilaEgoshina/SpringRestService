package com.example.app.service;

import com.example.app.dto.IncomingWorkerDTO;
import com.example.app.dto.OutgoingFullWorkerDTO;
import com.example.app.dto.UpdateWorkerDTO;
import com.example.app.entity.Worker;
import com.example.app.exceptions.NotFoundException;
import com.example.app.mapper.WorkerMapper;
import com.example.app.repository.WorkerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkerServiceTest {

    @Mock
    private WorkerRepository workerRepository;

    @Mock
    private WorkerMapper workerMapper;

    @InjectMocks
    private WorkerService workerService;

    @Test
    void saveWorker() {
        // Arrange
        IncomingWorkerDTO incomingWorkerDTO = new IncomingWorkerDTO();
        Worker worker = new Worker();
        OutgoingFullWorkerDTO outgoingFullWorkerDTO = new OutgoingFullWorkerDTO();

        when(workerMapper.mapToEntity(incomingWorkerDTO)).thenReturn(worker);
        when(workerRepository.save(worker)).thenReturn(worker);
        when(workerMapper.mapToDTO(worker)).thenReturn(outgoingFullWorkerDTO);

        // Act
        OutgoingFullWorkerDTO result = workerService.saveWorker(incomingWorkerDTO);

        // Assert
        assertEquals(outgoingFullWorkerDTO, result);
        verify(workerMapper, times(1)).mapToEntity(incomingWorkerDTO);
        verify(workerRepository, times(1)).save(worker);
        verify(workerMapper, times(1)).mapToDTO(worker);
    }

    @Test
    void updateWorker_ExistingWorker() throws NotFoundException {
        // Arrange
        UpdateWorkerDTO updateWorkerDTO = new UpdateWorkerDTO();
        updateWorkerDTO.setId(1L);
        Worker existingWorker = new Worker();
        existingWorker.setId(1L);

        when(workerRepository.findById(updateWorkerDTO.getId())).thenReturn(Optional.of(existingWorker));
        when(workerMapper.update(updateWorkerDTO)).thenReturn(existingWorker);

        // Act
        workerService.updateWorker(updateWorkerDTO);

        // Assert
        verify(workerRepository, times(1)).findById(updateWorkerDTO.getId());
        verify(workerMapper, times(1)).update(updateWorkerDTO);
        verify(workerRepository, times(1)).save(existingWorker);
    }

    @Test
    void updateWorker_NonExistingWorker() {
        // Arrange
        UpdateWorkerDTO updateWorkerDTO = new UpdateWorkerDTO(); // Create your DTO instance
        updateWorkerDTO.setId(1L); // Set a valid ID

        when(workerRepository.findById(updateWorkerDTO.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> workerService.updateWorker(updateWorkerDTO));
        verify(workerRepository, times(1)).findById(updateWorkerDTO.getId());
        verify(workerMapper, never()).update(updateWorkerDTO);
        verify(workerRepository, never()).save(any());
    }

    @Test
    void findWorkerById_ExistingWorker() throws NotFoundException {
        // Arrange
        Long id = 1L;
        Worker worker = new Worker();
        worker.setId(id);
        OutgoingFullWorkerDTO outgoingFullWorkerDTO = new OutgoingFullWorkerDTO();

        when(workerRepository.findById(id)).thenReturn(Optional.of(worker));
        when(workerMapper.mapToDTO(worker)).thenReturn(outgoingFullWorkerDTO);

        // Act
        OutgoingFullWorkerDTO result = workerService.findWorkerById(id);

        // Assert
        assertEquals(outgoingFullWorkerDTO, result);
        verify(workerRepository, times(1)).findById(id);
        verify(workerMapper, times(1)).mapToDTO(worker);
    }

    @Test
    void findAllWorker() {
        // Arrange
        List<Worker> workerList = new ArrayList<>();
        workerList.add(new Worker());
        workerList.add(new Worker());
        when(workerRepository.findAll()).thenReturn(workerList);
        when(workerMapper.mapToOutGoingDtos(workerList)).thenReturn(new ArrayList<>());

        // Act
        List<OutgoingFullWorkerDTO> result = workerService.findAllWorker();

        // Assert
        assertEquals(0, result.size());
        verify(workerRepository, times(1)).findAll();
        verify(workerMapper, times(1)).mapToOutGoingDtos(workerList);
    }

    @Test
    void deleteWorkerById_success() throws NotFoundException {
        // Arrange
        Long id = 1L;
        when(workerRepository.exitsById(id)).thenReturn(true);

        // Act
        workerService.deleteWorkerById(id);

        // Assert
        verify(workerRepository, times(1)).exitsById(id);
        verify(workerRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteWorkerById_notFound() {
        // Arrange
        Long id = 1L;
        when(workerRepository.exitsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> workerService.deleteWorkerById(id));

        verify(workerRepository, times(1)).exitsById(id);
        verify(workerRepository, never()).deleteById(id);
    }

    @Test
    void findWorkerById_success() throws NotFoundException {
        // Arrange
        Long id = 1L;
        Worker worker = new Worker();
        when(workerRepository.findById(id)).thenReturn(Optional.of(worker));
        when(workerMapper.mapToDTO(worker)).thenReturn(new OutgoingFullWorkerDTO());

        // Act
        OutgoingFullWorkerDTO result = workerService.findWorkerById(id);

        // Assert
        assertNotNull(result);
        verify(workerRepository, times(1)).findById(id);
        verify(workerMapper, times(1)).mapToDTO(worker);
    }

    @Test
    void findWorkerById_notFound() {
        // Arrange
        Long id = 1L;
        when(workerRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> workerService.findWorkerById(id));

        verify(workerRepository, times(1)).findById(id);
        verify(workerMapper, never()).mapToDTO(any(Worker.class));
    }
}
