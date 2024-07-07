package com.example.app.service;

import com.example.app.dto.IncomingComputerDTO;
import com.example.app.dto.OutgoingComputerDTO;
import com.example.app.dto.UpdateComputerDTO;
import com.example.app.entity.Computer;
import com.example.app.exceptions.NotFoundException;
import com.example.app.mapper.ComputerMapper;
import com.example.app.repository.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComputerService {

    private final ComputerRepository computerRepository;
    private final ComputerMapper computerMapper;

    public ComputerService(ComputerRepository computerRepository, ComputerMapper computerMapper) {
        this.computerRepository = computerRepository;
        this.computerMapper = computerMapper;
    }

    @Transactional
    public OutgoingComputerDTO saveComputer(IncomingComputerDTO incomingComputerDTO){

        Computer computer = computerMapper.mapToEntity(incomingComputerDTO);
        computer = computerRepository.save(computer);
        return computerMapper.mapToDTO(computer);
    }

    @Transactional
    public void updateComputer(UpdateComputerDTO updateComputerDTO) throws NotFoundException {

        Optional<Computer> computerOptional = computerRepository.findById(updateComputerDTO.getId());
        if(computerOptional.isPresent()){
            Computer computer = computerMapper.update(updateComputerDTO);
            computerRepository.save(computer);
        }else {
            throw new NotFoundException("There is not such computer");
        }
    }

    @Transactional
    public OutgoingComputerDTO findComputerById(Long id) throws NotFoundException {
        Computer computer = computerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is not such computer"));

        return computerMapper.mapToDTO(computer);
    }

    @Transactional
    public List<OutgoingComputerDTO> findAllComputer(){
        List<Computer> computerList = computerRepository.findAll();
        return computerMapper.mapToOutGoingDtos(computerList);
    }

    @Transactional
    public void deleteComputerById(Long id){
        computerRepository.deleteById(id);
    }

}
