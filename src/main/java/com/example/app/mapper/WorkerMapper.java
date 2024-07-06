package com.example.app.mapper;

import com.example.app.dto.IncomingWorkerDTO;
import com.example.app.dto.OutgoingFullWorkerDTO;
import com.example.app.dto.UpdateWorkerDTO;
import com.example.app.entity.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkerMapper {

    WorkerMapper INSTANCE = Mappers.getMapper(WorkerMapper.class);

    @Mappings({
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "workRelationsList", source = "workRelationsList", qualifiedByName = "outgoingWorkRelationsDTOS"),
            @Mapping(target = "computerList", source = "computerList", qualifiedByName = "outgoingComputerNumberDTOS"),
    })
    Worker mapToEntity(IncomingWorkerDTO incomingWorkerDTO);

    @Mappings({
            @Mapping(target = "role", source = "role.id"),
            @Mapping(target = "workRelationsList", source = "workRelationsList", qualifiedByName = "outgoingWorkRelationsDTOS"),
            @Mapping(target = "computerList", source = "computerList", qualifiedByName = "outgoingComputerNumberDTOS"),

    })
    OutgoingFullWorkerDTO mapToDTO(Worker worker);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "workRelationsList", source = "workRelationsList", qualifiedByName = "outgoingWorkRelationsDTOS"),
            @Mapping(target = "computerList", source = "computerList", qualifiedByName = "outgoingComputerNumberDTOS"),
    })
    Worker update(UpdateWorkerDTO updateWorkerDTO);

    List<OutgoingFullWorkerDTO> mapToOutGoingDtos(List<Worker> workerList);

}
