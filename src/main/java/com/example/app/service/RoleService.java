package com.example.app.service;

import com.example.app.dto.IncomingRoleDTO;
import com.example.app.dto.OutgoingRoleDTO;
import com.example.app.dto.UpdateRoleDTO;
import com.example.app.entity.Role;
import com.example.app.exceptions.NotFoundException;
import com.example.app.mapper.RoleMapper;
import com.example.app.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Transactional
    public OutgoingRoleDTO saveRole(IncomingRoleDTO incomingRoleDTO){

        Role role = roleMapper.mapToEntity(incomingRoleDTO);
        role = roleRepository.save(role);
        return roleMapper.mapToDTO(role);
    }

    @Transactional
    public void updateRole(UpdateRoleDTO updateRoleDTO) throws NotFoundException {

        Optional<Role> optionalRole = roleRepository.findById(updateRoleDTO.getId());
        if(optionalRole.isPresent()){
            Role role = roleMapper.update(updateRoleDTO);
            roleRepository.save(role);
        }else {
            throw new NotFoundException("There is not such role");
        }
    }

    @Transactional
    public OutgoingRoleDTO findRoleById(Long id) throws NotFoundException {

        Role role = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found."));
        return roleMapper.mapToDTO(role);
    }

    @Transactional
    public List<OutgoingRoleDTO> findAllRoles(){

        List<Role> roleList = roleRepository.findAll();
        return roleMapper.mapToOutGoingDtos(roleList);
    }

    @Transactional
    public void deleteRoleById(Long id) throws NotFoundException {

        Optional<Role> optionalRole = roleRepository.findById(id);
        if(optionalRole.isPresent()){

            roleRepository.deleteById(id);
        }else {
            throw new NotFoundException("There is not such role");
        }
    }
}
