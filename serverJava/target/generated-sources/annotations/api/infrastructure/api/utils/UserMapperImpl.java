package api.infrastructure.api.utils;

import api.application.dto.PublicUserDto;
import api.application.dto.UserUpdateDto;
import api.domain.models.UserEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T16:45:13-0300",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.12 (Microsoft)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public void updateUserFromDto(UserUpdateDto source, UserEntity target) {
        if ( source == null ) {
            return;
        }

        target.setPassword( source.getPassword() );
    }

    @Override
    public void toPublicUserFromEntity(UserEntity source, PublicUserDto target) {
        if ( source == null ) {
            return;
        }

        target.setUsername( source.getUsername() );
        target.setEmail( source.getEmail() );
    }
}
