package com.example.app.controller;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.dto.UpdateWorkRelationsDTO;
import com.example.app.exceptions.NotFoundException;
import com.example.app.service.WorkRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workRelations")
public class WorkRelationsController {

    private final WorkRelationsService workRelationsService;

    @Autowired
    public WorkRelationsController(WorkRelationsService workRelationsService) {
        this.workRelationsService = workRelationsService;
}

    @GetMapping("/{id}")
    public ResponseEntity<OutgoingWorkRelationsDTO> getWorkRelationById(@PathVariable Long id) {

        try {
            return new ResponseEntity<>(workRelationsService.findWorkRelationsById(id),HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OutgoingWorkRelationsDTO>> getAllWorkRelations(){
        List<OutgoingWorkRelationsDTO> workRelationsDTOList = workRelationsService.findAllWorkRelations();
        return new ResponseEntity<>(workRelationsDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OutgoingWorkRelationsDTO> createWorkRelations(@RequestBody IncomingWorkRelationsDTO incomingWorkRelationsDTO){

        try {

            OutgoingWorkRelationsDTO workRelationsDTO = workRelationsService.saveWorkRelations(incomingWorkRelationsDTO);
            return new ResponseEntity<>(workRelationsDTO,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkRelationsById(@PathVariable Long id){

        try {
            workRelationsService.deleteWorkRelationsById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<String> updateWorkRelations(@RequestBody UpdateWorkRelationsDTO updateWorkRelationsDTO) {
        try {
            workRelationsService.updateWorkRelations(updateWorkRelationsDTO);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Incorrect work relation Object.");
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
