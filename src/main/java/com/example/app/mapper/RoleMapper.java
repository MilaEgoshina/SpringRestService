package com.example.app.mapper;

import com.example.app.dto.IncomingRoleDTO;
import com.example.app.dto.OutgoingRoleDTO;
import com.example.app.dto.UpdateRoleDTO;
import com.example.app.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    Role mapToEntity(IncomingRoleDTO incomingRoleDTO);

    Role update(UpdateRoleDTO updateRoleDTO);

    OutgoingRoleDTO mapToDTO(Role role);

    List<OutgoingRoleDTO> mapToOutGoingDtos(List<Role> roleList);
}
