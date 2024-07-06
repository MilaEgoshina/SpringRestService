package com.example.app.mapper;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.dto.UpdateWorkRelationsDTO;
import com.example.app.entity.WorkRelations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkRelationsMapper {

    WorkRelationsMapper INSTANCE = Mappers.getMapper(WorkRelationsMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "workerList", ignore = true)
    })
    WorkRelations mapToEntity(IncomingWorkRelationsDTO incomingWorkRelationsDTO);

    @Mappings({
            @Mapping(target = "outgoingFieldsWorkerDTOList", source = "workRelations.workerList")
    })
    OutgoingWorkRelationsDTO mapToDTO(WorkRelations workRelations);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "workerList", ignore = true)
    })
    WorkRelations update(UpdateWorkRelationsDTO updateWorkRelationsDTO);

    List<OutgoingWorkRelationsDTO> mapToOutGoingDtos(List<WorkRelations> workRelationsList);

    List<WorkRelations> mapToUpdateEntities(List<UpdateWorkRelationsDTO> updateWorkRelationsDTOS);

}
