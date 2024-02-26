package com.medicare.service.mapper;

import com.medicare.domain.Patient;
import com.medicare.domain.User;
import com.medicare.service.dto.PatientDTO;
import com.medicare.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Patient} and its DTO {@link PatientDTO}.
 */
@Mapper(componentModel = "spring", uses = {User.class})

public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {
    @Mapping(target = "userId", source = "user.id")
    PatientDTO toDto(Patient s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoId(User user);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
