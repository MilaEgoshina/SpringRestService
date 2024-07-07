package com.example.app.controller;

import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.exceptions.NotFoundException;
import com.example.app.service.WorkRelationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
