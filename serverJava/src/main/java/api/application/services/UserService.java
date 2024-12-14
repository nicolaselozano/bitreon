package api.application.services;

import api.domain.models.UserEntity;
import api.domain.repository.UserRepository;
import api.application.dto.LoginDto;
import api.application.dto.PublicUserDto;
import api.application.dto.UserUpdateDto;
import api.infrastructure.api.utils.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> getAll() {
        return repository.findAll();
    }
    public Optional<UserEntity> findById(Long id) {
        return repository.findById(id);
    }
    public PublicUserDto registerUser(UserEntity userEntity) {
        try {

            Optional<UserEntity> userExist = repository.findByEmail(userEntity.getEmail());

            if (userExist.isPresent()) {
                throw new RuntimeException("Email already exists");
            }

            String hashPassword = BCrypt.hashpw(userEntity.getPassword(), BCrypt.gensalt());

            UserEntity newUser = new UserEntity();
            newUser.setPassword(hashPassword);
            newUser.setUsername(userEntity.getUsername());
            newUser.setEmail(userEntity.getEmail());

            PublicUserDto publicUserDto = new PublicUserDto();
            UserMapper.INSTANCE.toPublicUserFromEntity(newUser, publicUserDto);

            repository.save(newUser);

            return publicUserDto;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al registrar el usuario : "+ e.getMessage());
        }
    }


    public PublicUserDto loginUser(LoginDto loginData) {
        UserEntity userEntity;
        if (!loginData.getEmail().isEmpty()) {
            userEntity = repository.findByEmail(loginData.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "No se encontró el usuario con ese email"
                    ));
        } else {
            userEntity = repository.findByUsername(loginData.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "No se encontró el usuario con ese nombre de usuario"
                    ));
        }

        if (!BCrypt.checkpw(loginData.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        PublicUserDto publicUserDto = new PublicUserDto();
        UserMapper.INSTANCE.toPublicUserFromEntity(userEntity, publicUserDto);
        return publicUserDto;
    }


    public UserEntity save(UserEntity userData) {
        return repository.save(userData);
    }

    public UserEntity delete(Long id) {
        try {
            UserEntity userEntity = repository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "No se encontró el usuario con ese email"
                    ));

            repository.delete(userEntity);

            return userEntity;

        } catch (RuntimeException e) {
            throw e;
        }
    }


    public UserEntity update(Long id, UserUpdateDto newUserData) {
        try {
            UserEntity userExist = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encontro el usuario"));

            UserMapper.INSTANCE.updateUserFromDto(newUserData, userExist);

            return repository.save(userExist);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}