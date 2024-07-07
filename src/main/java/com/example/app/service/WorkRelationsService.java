package com.example.app.service;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.dto.UpdateWorkRelationsDTO;
import com.example.app.entity.WorkRelations;
import com.example.app.exceptions.NotFoundException;
import com.example.app.mapper.WorkRelationsMapper;
import com.example.app.repository.WorkRelationsRepository;
import com.example.app.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WorkRelationsService {

    @Autowired
    private WorkRelationsRepository workerRelationsRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkRelationsMapper workRelationsMapper;

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

    
}
