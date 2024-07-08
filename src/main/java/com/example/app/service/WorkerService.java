package com.example.app.service;

import com.example.app.dto.IncomingWorkerDTO;
import com.example.app.dto.OutgoingFullWorkerDTO;
import com.example.app.dto.UpdateWorkerDTO;
import com.example.app.entity.Worker;
import com.example.app.exceptions.NotFoundException;
import com.example.app.mapper.WorkerMapper;
import com.example.app.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final WorkerMapper workerMapper;

    public WorkerService(WorkerRepository workerRepository, WorkerMapper workerMapper) {
        this.workerRepository = workerRepository;
        this.workerMapper = workerMapper;
    }

    public OutgoingFullWorkerDTO saveWorker(IncomingWorkerDTO incomingWorkerDTO){

        Worker worker = workerMapper.mapToEntity(incomingWorkerDTO);
        worker = workerRepository.save(worker);
        return workerMapper.mapToDTO(worker);
    }

    public void updateWorker(UpdateWorkerDTO updateWorkerDTO) throws NotFoundException {

        Optional<Worker> workerOptional = workerRepository.findById(updateWorkerDTO.getId());
        if(workerOptional.isPresent()){
            Worker worker = workerMapper.update(updateWorkerDTO);
            workerRepository.save(worker);
        }else {
            throw new NotFoundException("There is not such worker");
        }
    }

    public OutgoingFullWorkerDTO findWorkerById(Long id) throws NotFoundException {

        Worker worker = workerRepository.findById(id).orElseThrow(() -> new NotFoundException("There is not such worker"));
        return workerMapper.mapToDTO(worker);

    }

    public List<OutgoingFullWorkerDTO> findAllWorker(){

        List<Worker> workerList = workerRepository.findAll();
        return workerMapper.mapToOutGoingDtos(workerList);
    }

    /*    @Transactional
    public void deleteWorkRelationsById(Long id) throws NotFoundException {

        if(workerRelationsRepository.exitsById(id)){
            workerRelationsRepository.deleteById(id);
        }else {
            throw new NotFoundException("WorkRelations not found.");
        }
    }*/
    public void deleteWorkerById(Long id) throws NotFoundException {

        if(workerRepository.exitsById(id)){
            workerRepository.deleteById(id);
        }else {
            throw new NotFoundException("Worker not found.");
        }

    }
}
