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
        IncomingWorkerDTO incomingWorkerDTO = new IncomingWorkerDTO(); // Create your DTO instance
        Worker worker = new Worker(); // Create your Entity instance
        OutgoingFullWorkerDTO outgoingFullWorkerDTO = new OutgoingFullWorkerDTO(); // Create your DTO instance

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
        UpdateWorkerDTO updateWorkerDTO = new UpdateWorkerDTO(); // Create your DTO instance
        updateWorkerDTO.setId(1L); // Set a valid ID
        Worker existingWorker = new Worker(); // Create your Entity instance
        existingWorker.setId(1L); // Set the same ID as in the DTO

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
        Worker worker = new Worker(); // Create your Entity instance
        worker.setId(id);
        OutgoingFullWorkerDTO outgoingFullWorkerDTO = new OutgoingFullWorkerDTO(); // Create your DTO instance

        when(workerRepository.findById(id)).thenReturn(Optional.of(worker));
        when(workerMapper.mapToDTO(worker)).thenReturn(outgoingFullWorkerDTO);

        // Act
        OutgoingFullWorkerDTO result = workerService.findWorkerById(id);

        // Assert
        assertEquals(outgoingFullWorkerDTO, result);
        verify(workerRepository, times(1)).findById(id);
        verify(workerMapper, times(1)).mapToDTO(worker);
    }
}
