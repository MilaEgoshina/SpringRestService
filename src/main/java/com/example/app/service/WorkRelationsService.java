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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WorkRelationsService {


    private final WorkRelationsRepository workerRelationsRepository;


    private final WorkerRepository workerRepository;


    private final WorkRelationsMapper workRelationsMapper;

    public WorkRelationsService(WorkRelationsRepository workerRelationsRepository, WorkerRepository workerRepository, WorkRelationsMapper workRelationsMapper) {
        this.workerRelationsRepository = workerRelationsRepository;
        this.workerRepository = workerRepository;
        this.workRelationsMapper = workRelationsMapper;
    }

    @Transactional
    public OutgoingWorkRelationsDTO saveWorkRelations(IncomingWorkRelationsDTO incomingWorkRelationsDTO){

        WorkRelations workRelations = workRelationsMapper.mapToEntity(incomingWorkRelationsDTO);
        workRelations = workerRelationsRepository.save(workRelations);
        return workRelationsMapper.mapToDTO(workRelations);
    }

    @Transactional
    public void updateWorkRelations(UpdateWorkRelationsDTO updateWorkRelationsDTO) throws NotFoundException{
        Optional<WorkRelations> workRelationsOptional = workerRelationsRepository.findById(updateWorkRelationsDTO.getId());
        if(workRelationsOptional.isPresent()){
            WorkRelations workRelations = workRelationsMapper.update(updateWorkRelationsDTO);
            workerRelationsRepository.save(workRelations);
        }else {
            throw new NotFoundException("WorkRelations not found.");
        }
    }

    public OutgoingWorkRelationsDTO findWorkRelationsById(Long id) throws NotFoundException{

        Optional<WorkRelations> workRelationsOptional = workerRelationsRepository.findById(id);
        if(workRelationsOptional.isPresent()){

            return workRelationsMapper.mapToDTO(workRelationsOptional.get());
        }else {
            throw new NotFoundException("WorkRelations not found.");
        }
    }

    public List<OutgoingWorkRelationsDTO> findAllWorkRelations(){

        List<WorkRelations> workRelationsList = workerRelationsRepository.findAll();
        return workRelationsMapper.mapToOutGoingDtos(workRelationsList);
    }

    @Transactional
    public void deleteWorkRelationsById(Long id) throws NotFoundException {

        if(workerRelationsRepository.exitsById(id)){
            workerRelationsRepository.deleteById(id);
        }else {
            throw new NotFoundException("WorkRelations not found.");
        }
    }

    public void addWorkerToRelations(Long id, Long workerId) throws NotFoundException{
        checkExistWorkRelations(id);
        Optional<WorkRelations> workRelationsOptional = workerRelationsRepository.findById(id);
        Optional<Worker> workerOptional = workerRepository.findById(workerId);
        if(workerOptional.isPresent() && workRelationsOptional.isPresent()){
            WorkRelations workRelationsExisting= workRelationsOptional.get();
            Worker workerExisting = workerOptional.get();
            workRelationsExisting.getWorkerList().add(workerExisting);
            workerExisting.getWorkRelationsList().add(workRelationsExisting);

            workerRelationsRepository.save(workRelationsExisting);
            workerRepository.save(workerExisting);
        }else{
            throw new NotFoundException("Entities not found");
        }
    }

    public void deleteWorkerFromRelations(Long id, Long workerId) throws NotFoundException{

        checkExistWorkRelations(id);
        Optional<WorkRelations> workRelationsOptional = workerRelationsRepository.findById(id);
        Optional<Worker> workerOptional = workerRepository.findById(workerId);
        if(workerOptional.isPresent() && workRelationsOptional.isPresent()) {
            WorkRelations workRelationsExisting = workRelationsOptional.get();
            Worker workerExisting = workerOptional.get();

            workRelationsExisting.getWorkerList().remove(workerExisting);
            workerExisting.getWorkRelationsList().remove(workRelationsExisting);

            workerRelationsRepository.save(workRelationsExisting);
            workerRepository.save(workerExisting);

        }else{
            throw new NotFoundException("Entities not found");
        }

    }
    private void checkExistWorkRelations(Long id) throws NotFoundException {
        if (!workerRelationsRepository.existsById(id)) {
            throw new NotFoundException("WorkRelations not found.");
        }
    }
}
