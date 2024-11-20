package api.ejemplo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import api.application.dto.LoginDto;
import api.application.dto.PublicUserDto;
import api.application.services.UserService;
import api.domain.models.UserEntity;
import api.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository repository;

    @Test
    void testRegisterUser_Success() {
        // Arrange
        UserEntity inputUser = new UserEntity();
        inputUser.setPassword("plainPassword");
        inputUser.setUsername("testUser");
        inputUser.setEmail("test@example.com");

        UserEntity savedUser = new UserEntity();
        savedUser.setPassword(BCrypt.hashpw("plainPassword", BCrypt.gensalt()));
        savedUser.setUsername("testUser");
        savedUser.setEmail("test@example.com");

        when(repository.save(any(UserEntity.class))).thenReturn(savedUser);

        // Act
        PublicUserDto result = userService.registerUser(inputUser);

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testRegisterUser_ThrowsException() {
        // Arrange
        UserEntity inputUser = new UserEntity();
        inputUser.setPassword("plainPassword");
        inputUser.setUsername("testUser");
        inputUser.setEmail("test@example.com");

        when(repository.save(any(UserEntity.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(inputUser);
        });
        assertEquals("Error al registrar el usuario", exception.getMessage());
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testLoginUser_Success_ByEmail() {
        // Arrange
        LoginDto loginData = new LoginDto();
        loginData.setEmail("test@example.com");
        loginData.setPassword("plainPassword");

        UserEntity foundUser = new UserEntity();
        foundUser.setEmail("test@example.com");
        foundUser.setUsername("testUser");
        foundUser.setPassword(BCrypt.hashpw("plainPassword", BCrypt.gensalt()));

        when(repository.findByEmail("test@example.com")).thenReturn(Optional.of(foundUser));

        // Act
        PublicUserDto result = userService.loginUser(loginData);

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(repository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testLoginUser_InvalidPassword() {
        // Arrange
        LoginDto loginData = new LoginDto();
        loginData.setEmail("test@example.com");
        loginData.setPassword("wrongPassword");

        UserEntity foundUser = new UserEntity();
        foundUser.setEmail("test@example.com");
        foundUser.setUsername("testUser");
        foundUser.setPassword(BCrypt.hashpw("plainPassword", BCrypt.gensalt()));

        when(repository.findByEmail("test@example.com")).thenReturn(Optional.of(foundUser));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.loginUser(loginData);
        });
        assertEquals("Credenciales inválidas", exception.getMessage());
        verify(repository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testLoginUser_UserNotFound_ByEmail() {
        // Arrange
        LoginDto loginData = new LoginDto();
        loginData.setEmail("nonexistent@example.com");
        loginData.setPassword("plainPassword");

        when(repository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.loginUser(loginData);
        });
        assertEquals("No se encontró el usuario con ese email", exception.getMessage());
        verify(repository, times(1)).findByEmail("nonexistent@example.com");
        System.out.println("Test de Autenticacion finalizado");
    }
}
