package com.example.app.controller;

import com.example.app.dto.IncomingComputerDTO;
import com.example.app.dto.OutgoingComputerDTO;
import com.example.app.dto.UpdateComputerDTO;
import com.example.app.exceptions.NotFoundException;
import com.example.app.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/computer")
public class ComputerController {

    private final ComputerService computerService;

    @Autowired
    public ComputerController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @PostMapping
    public ResponseEntity<OutgoingComputerDTO> createComputer(@RequestBody IncomingComputerDTO incomingComputerDTO){

        try {
            OutgoingComputerDTO computerDTO = computerService.saveComputer(incomingComputerDTO);
            return new ResponseEntity<>(computerDTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OutgoingComputerDTO>> getAllComputers(){
        List<OutgoingComputerDTO> computerDTOList = computerService.findAllComputer();
        return new ResponseEntity<>(computerDTOList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutgoingComputerDTO> getComputerById(@PathVariable Long id){
        try {
            OutgoingComputerDTO computerDTO = computerService.findComputerById(id);
            return new ResponseEntity<>(computerDTO,HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateComputer(@RequestBody UpdateComputerDTO computerDTO) {
        try {
            computerService.updateComputer(computerDTO);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Incorrect computer Object.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComputer(@PathVariable Long id){
        try {
            computerService.deleteComputerById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
