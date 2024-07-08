package com.example.app.service;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.dto.UpdateWorkRelationsDTO;
import com.example.app.entity.WorkRelations;
import com.example.app.entity.Worker;
import com.example.app.exceptions.NotFoundException;
import com.example.app.mapper.WorkRelationsMapper;
import com.example.app.repository.WorkRelationsRepository;
import com.example.app.repository.WorkerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WorkRelationsServiceTest {

    @Mock
    private WorkRelationsRepository workRelationsRepository;

    @Mock
    private WorkerRepository workerRepository;

    @Mock
    private WorkRelationsMapper workRelationsMapper;

    @InjectMocks
    private WorkRelationsService workRelationsService;

    // Test data
    private final Long WORK_RELATIONS_ID = 1L;
    private final Long WORKER_ID = 2L;

    private final WorkRelations workRelations = new WorkRelations();
    private final IncomingWorkRelationsDTO incomingWorkRelationsDTO = new IncomingWorkRelationsDTO();
    private final OutgoingWorkRelationsDTO outgoingWorkRelationsDTO = new OutgoingWorkRelationsDTO();
    private final UpdateWorkRelationsDTO updateWorkRelationsDTO = new UpdateWorkRelationsDTO();
    private final Worker worker = new Worker();

    @Test
    void saveWorkRelations() {
        when(workRelationsMapper.mapToEntity(incomingWorkRelationsDTO)).thenReturn(workRelations);
        when(workRelationsRepository.save(workRelations)).thenReturn(workRelations);
        when(workRelationsMapper.mapToDTO(workRelations)).thenReturn(outgoingWorkRelationsDTO);

        OutgoingWorkRelationsDTO result = workRelationsService.saveWorkRelations(incomingWorkRelationsDTO);

        assertEquals(outgoingWorkRelationsDTO, result);
        verify(workRelationsMapper, times(1)).mapToEntity(incomingWorkRelationsDTO);
        verify(workRelationsRepository, times(1)).save(workRelations);
        verify(workRelationsMapper, times(1)).mapToDTO(workRelations);
    }

    @Test
    void updateWorkRelations() throws NotFoundException {
        when(workRelationsRepository.findById(updateWorkRelationsDTO.getId())).thenReturn(Optional.of(workRelations));
        when(workRelationsMapper.update(updateWorkRelationsDTO)).thenReturn(workRelations);

        workRelationsService.updateWorkRelations(updateWorkRelationsDTO);

        verify(workRelationsRepository, times(1)).findById(updateWorkRelationsDTO.getId());
        verify(workRelationsMapper, times(1)).update(updateWorkRelationsDTO);
        verify(workRelationsRepository, times(1)).save(workRelations);
    }

    @Test
    void updateWorkRelations_notFound() {
        when(workRelationsRepository.findById(updateWorkRelationsDTO.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> workRelationsService.updateWorkRelations(updateWorkRelationsDTO));

        verify(workRelationsRepository, times(1)).findById(updateWorkRelationsDTO.getId());
        verify(workRelationsMapper, never()).update(updateWorkRelationsDTO);
        verify(workRelationsRepository, never()).save(workRelations);
    }

    @Test
    void findWorkRelationsById() throws NotFoundException {
        when(workRelationsRepository.findById(WORK_RELATIONS_ID)).thenReturn(Optional.of(workRelations));
        when(workRelationsMapper.mapToDTO(workRelations)).thenReturn(outgoingWorkRelationsDTO);

        OutgoingWorkRelationsDTO result = workRelationsService.findWorkRelationsById(WORK_RELATIONS_ID);

        assertEquals(outgoingWorkRelationsDTO, result);
        verify(workRelationsRepository, times(1)).findById(WORK_RELATIONS_ID);
        verify(workRelationsMapper, times(1)).mapToDTO(workRelations);
    }
    @Test
    public void addWorkerToRelations_Success() throws NotFoundException {
        // Arrange
        Long workRelationsId = 1L;
        Long workerId = 2L;
        WorkRelations workRelations = new WorkRelations();
        Worker worker = new Worker();
        when(workRelationsRepository.findById(workRelationsId)).thenReturn(Optional.of(workRelations));
        when(workerRepository.findById(workerId)).thenReturn(Optional.of(worker));

        // Act
        workRelationsService.addWorkerToRelations(workRelationsId, workerId);

        // Assert
        verify(workRelationsRepository, times(1)).findById(workRelationsId);
        verify(workerRepository, times(1)).findById(workerId);
        verify(workRelationsRepository, times(1)).save(workRelations);
        verify(workerRepository, times(1)).save(worker);
    }

    @Test
    public void addWorkerToRelations_NotFoundException() {
        // Arrange
        Long workRelationsId = 1L;
        Long workerId = 2L;
        when(workRelationsRepository.findById(workRelationsId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> workRelationsService.addWorkerToRelations(workRelationsId, workerId));
        verify(workRelationsRepository, times(1)).findById(workRelationsId);
        verify(workerRepository, never()).findById(workerId);
        verify(workRelationsRepository, never()).save(any());
        verify(workerRepository, never()).save(any());
    }

    @Test
    public void deleteWorkerFromRelations_Success() throws NotFoundException {
        // Arrange
        Long workRelationsId = 1L;
        Long workerId = 2L;
        WorkRelations workRelations = new WorkRelations();
        Worker worker = new Worker();
        when(workRelationsRepository.findById(workRelationsId)).thenReturn(Optional.of(workRelations));
        when(workerRepository.findById(workerId)).thenReturn(Optional.of(worker));

        // Act
        workRelationsService.deleteWorkerFromRelations(workRelationsId, workerId);

        // Assert
        verify(workRelationsRepository, times(1)).findById(workRelationsId);
        verify(workerRepository, times(1)).findById(workerId);
        verify(workRelationsRepository, times(1)).save(workRelations);
        verify(workerRepository, times(1)).save(worker);
    }

    @Test
    public void deleteWorkerFromRelations_NotFoundException() {
        // Arrange
        Long workRelationsId = 1L;
        Long workerId = 2L;
        when(workRelationsRepository.findById(workRelationsId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> workRelationsService.deleteWorkerFromRelations(workRelationsId, workerId));
        verify(workRelationsRepository, times(1)).findById(workRelationsId);
        verify(workerRepository, never()).findById(workerId);
        verify(workRelationsRepository, never()).save(any());
        verify(workerRepository, never()).save(any());
    }

    @Test
    void findAllWorkRelations() {
        // Arrange
        List<WorkRelations> workRelationsList = new ArrayList<>();
        workRelationsList.add(new WorkRelations()); // Add some sample WorkRelations objects
        workRelationsList.add(new WorkRelations());

        List<OutgoingWorkRelationsDTO> outgoingDtos = new ArrayList<>();
        outgoingDtos.add(new OutgoingWorkRelationsDTO()); // Add some sample DTOs
        outgoingDtos.add(new OutgoingWorkRelationsDTO());

        when(workRelationsRepository.findAll()).thenReturn(workRelationsList);
        when(workRelationsMapper.mapToOutGoingDtos(workRelationsList)).thenReturn(outgoingDtos);

        // Act
        List<OutgoingWorkRelationsDTO> result = workRelationsService.findAllWorkRelations();

        // Assert
        verify(workRelationsRepository, times(1)).findAll();
        verify(workRelationsMapper, times(1)).mapToOutGoingDtos(workRelationsList);
        assertEquals(outgoingDtos, result);
    }
}
