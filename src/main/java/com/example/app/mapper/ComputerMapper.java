package com.example.app.mapper;

import com.example.app.dto.IncomingComputerDTO;
import com.example.app.dto.OutgoingComputerDTO;
import com.example.app.dto.UpdateComputerDTO;
import com.example.app.entity.Computer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComputerMapper {

    ComputerMapper INSTANCE = Mappers.getMapper(ComputerMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "worker", ignore = true)
    })
    Computer mapToEntity(IncomingComputerDTO incomingComputerDTO);

    @Mappings({
            @Mapping(source = "worker.id", target = "worker.id"),
            @Mapping(source = "worker.firstName", target = "worker.firstName"),
            @Mapping(source = "worker.lastName", target = "worker.lastName")
    })
     OutgoingComputerDTO mapToDTO(Computer computer);

    List<OutgoingComputerDTO> mapToOutGoingDtos(List<Computer> computerList);

    @Mappings({
            @Mapping(target = "worker.id", source = "workerId"),
            @Mapping(target = "worker.firstName", ignore = true),
            @Mapping(target = "worker.lastName", ignore = true),
            @Mapping(target = "worker.role", ignore = true),
            @Mapping(target = "worker.workRelationsList", ignore = true),
            @Mapping(target = "worker.computerList", ignore = true),

    })
    Computer update(UpdateComputerDTO updateComputerDTO);

    List<Computer> mapToUpdateEntities(List<UpdateComputerDTO> updateComputerDTOS);
}
