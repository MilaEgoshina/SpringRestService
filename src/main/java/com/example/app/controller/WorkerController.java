package com.example.app.controller;

import com.example.app.dto.*;
import com.example.app.exceptions.NotFoundException;
import com.example.app.service.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }
    @GetMapping
    public ResponseEntity<List<OutgoingFullWorkerDTO>> getAllWorkers(){
        List<OutgoingFullWorkerDTO> workerDTOList = workerService.findAllWorker();
        return new ResponseEntity<>(workerDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutgoingFullWorkerDTO> getWorkerById(@PathVariable Long id){
        try {
            OutgoingFullWorkerDTO workerDTO = workerService.findWorkerById(id);
            return new ResponseEntity<>(workerDTO,HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<OutgoingFullWorkerDTO> createWorker(@RequestBody IncomingWorkerDTO incomingWorkerDTO){
        try {
            OutgoingFullWorkerDTO workerDTO = workerService.saveWorker(incomingWorkerDTO);
            return new ResponseEntity<>(workerDTO,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id){
        try {
            workerService.deleteWorkerById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateWorker(@RequestBody UpdateWorkerDTO updateWorkerDTO){
        try {
            workerService.updateWorker(updateWorkerDTO);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Incorrect worker Object.");
        }
    }
}
