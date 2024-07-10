package com.example.app.controller;

import com.example.app.dto.IncomingRoleDTO;
import com.example.app.dto.OutgoingRoleDTO;
import com.example.app.dto.UpdateRoleDTO;
import com.example.app.exceptions.NotFoundException;
import com.example.app.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<OutgoingRoleDTO>> getAllRoles(){

        List<OutgoingRoleDTO> roleDTOS = roleService.findAllRoles();
        return new ResponseEntity<>(roleDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutgoingRoleDTO> getRoleById(@PathVariable Long id){
        try {
            OutgoingRoleDTO roleDTO = roleService.findRoleById(id);
            return new ResponseEntity<>(roleDTO,HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<OutgoingRoleDTO> createRole(@RequestBody IncomingRoleDTO incomingRoleDTO){
        try {
            OutgoingRoleDTO roleDTO = roleService.saveRole(incomingRoleDTO);
            return new ResponseEntity<>(roleDTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id){
        try {
            roleService.deleteRoleById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<String> updatePole(@RequestBody UpdateRoleDTO updateRoleDTO){

        try {
            roleService.updateRole(updateRoleDTO);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Incorrect role Object.");
        }
    }
}
