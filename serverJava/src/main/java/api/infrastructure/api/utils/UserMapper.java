package api.infrastructure.api.utils;

import api.application.dto.PublicUserDto;
import api.application.dto.UserUpdateDto;
import api.domain.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(UserUpdateDto source, @MappingTarget UserEntity target);
    void toPublicUserFromEntity(UserEntity source, @MappingTarget PublicUserDto target);

}
