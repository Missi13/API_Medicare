package com.medicare.service.mapper;

import com.medicare.domain.Patient;
import com.medicare.domain.User;
import com.medicare.service.dto.PatientDTO;
import com.medicare.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Patient} and its DTO {@link PatientDTO}.
 */
@Mapper(componentModel = "spring")
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    PatientDTO toDto(Patient s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
