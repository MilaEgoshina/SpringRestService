package com.example.app.mapper;

import com.example.app.dto.*;
import com.example.app.entity.Computer;
import com.example.app.entity.Role;
import com.example.app.entity.WorkRelations;
import com.example.app.entity.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {RoleMapper.class, ComputerMapper.class, WorkRelationsMapper.class})
public interface WorkerMapper {

       RoleMapper roleMapper = RoleMapper.INSTANCE;
       ComputerMapper computerMapper = ComputerMapper.INSTANCE;
       WorkRelationsMapper workRelationsMapper = WorkRelationsMapper.INSTANCE;


    WorkerMapper INSTANCE = Mappers.getMapper(WorkerMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "computerList", ignore = true),
            @Mapping(target = "workRelationsList", ignore = true),
    })
    Worker mapToEntity(IncomingWorkerDTO incomingWorkerDTO);

    @Mappings({
            @Mapping(target = "role", source = "role", qualifiedByName = "roleMapping"),
            @Mapping(target = "workRelationsList", source = "workRelationsList", qualifiedByName = "workRelationsListUpdateMapping"),
            @Mapping(target = "computerList", source = "computerList", qualifiedByName = "computerListMapping"),

    })
    OutgoingFullWorkerDTO mapToDTO(Worker worker);

    @Mappings({
            @Mapping(target = "role", source = "role",qualifiedByName = "roleMapping"),
            @Mapping(target = "workRelationsList", source = "workRelationsList", qualifiedByName = "workRelationsListUpdateMapping"),
            @Mapping(target = "computerList", source = "computerList", qualifiedByName = "computerListUpdateMapping"),
    })
    Worker update(UpdateWorkerDTO updateWorkerDTO);

    List<OutgoingFullWorkerDTO> mapToOutGoingDtos(List<Worker> workerList);

    @Named("roleMapping")
    default OutgoingRoleDTO roleToDto(Role role) {
        return roleMapper.mapToDTO(role);
    }
    @Named("computerListMapping")
    default List<OutgoingComputerDTO> computerMapper(List<Computer> computerDTOList) {
        return computerMapper.mapToOutGoingDtos(computerDTOList);
    }


    @Named("workRelationsListMapping")
    default List<OutgoingWorkRelationsDTO> workRelationsMapper(List<WorkRelations> workRelationsDTOList) {
        return workRelationsMapper.mapToOutGoingDtos(workRelationsDTOList);
    }
    @Named("computerListUpdateMapping")
    default List<Computer> computerUpdateMapper(List<UpdateComputerDTO> computerDTOList) {
        return computerMapper.mapToUpdateEntities(computerDTOList);
    }

    @Named("workRelationsListUpdateMapping")
    default List<WorkRelations>  workRelationsUpdateMapper(List<UpdateWorkRelationsDTO> workRelationsDTOList) {
        return workRelationsMapper.mapToUpdateEntities(workRelationsDTOList);
    }
}

