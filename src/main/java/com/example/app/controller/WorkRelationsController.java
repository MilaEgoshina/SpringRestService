package com.example.app.controller;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.dto.UpdateWorkRelationsDTO;
import com.example.app.exceptions.NotFoundException;
import com.example.app.service.WorkRelationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workRelations")
public class WorkRelationsController {

    private final WorkRelationsService workRelationsService;
    private final ObjectMapper objectMapper;

    @Autowired
    public WorkRelationsController(WorkRelationsService workRelationsService, ObjectMapper objectMapper) {
        this.workRelationsService = workRelationsService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutgoingWorkRelationsDTO> getWorkRelationById(@PathVariable Long id) {

        try {
            return new ResponseEntity<>(workRelationsService.findWorkRelationsById(id),HttpStatus.OK);
        }catch (NotFoundException e){

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OutgoingWorkRelationsDTO>> getAllWorkRelations(){
        List<OutgoingWorkRelationsDTO> workRelationsDTOList = workRelationsService.findAllWorkRelations();
        return new ResponseEntity<>(workRelationsDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OutgoingWorkRelationsDTO> createWorkRelations(@RequestBody IncomingWorkRelationsDTO incomingWorkRelationsDTO){

        OutgoingWorkRelationsDTO workRelationsDTO = workRelationsService.saveWorkRelations(incomingWorkRelationsDTO);
        return new ResponseEntity<>(workRelationsDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkRelations(@PathVariable Long id){

        try {
            workRelationsService.deleteWorkRelationsById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/deleteWorker/{workerId}")
    public ResponseEntity<Void> deleteWorkRelations(@PathVariable Long id, @PathVariable Long workerId){

        try {
            workRelationsService.deleteWorkerFromRelations(id,workerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){

            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateWorkRelations(@RequestBody UpdateWorkRelationsDTO updateWorkRelationsDTO) {
        try {
            workRelationsService.updateWorkRelations(updateWorkRelationsDTO);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/add-worker/{id}/{workerId}")
    public ResponseEntity<Void> addWorkerToRelations(@PathVariable Long id, @PathVariable Long workerId) {
        try {
            workRelationsService.addWorkerToRelations(id, workerId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
