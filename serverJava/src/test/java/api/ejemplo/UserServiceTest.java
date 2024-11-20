package api.ejemplo;

import api.domain.models.UserEntity;
import api.domain.repository.UserRepository;
import api.application.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<UserEntity> mockUsers = new ArrayList<>();
        UserEntity user = new UserEntity();
        user.setEmail("EMAIL@email.com");
        user.setUsername("Ejemplo");
        user.setPassword("USUARIO");
        mockUsers.add(user);

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<UserEntity> users = userService.getAll();
        assertEquals(1, users.size());
        assertEquals("EMAIL@email.com", users.get(0).getEmail());
    }

    @Test
    void testSave() {
        UserEntity user = new UserEntity();
        user.setEmail("EMAIL@email.com");
        user.setUsername("Nombre");
        user.setPassword("USUARIO");

        when(userRepository.save(user)).thenReturn(user);

        UserEntity savedUser = userService.save(user);
        assertEquals("EMAIL@email.com", savedUser.getEmail());
    }

    @Test
    void testDelete() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity deletedUser = userService.delete(1L);
        assertNotNull(deletedUser);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDeleteUserNotFound() {
        UserService userService = new UserService(userRepository);

        RuntimeException thrownException = assertThrows(
                RuntimeException.class,
                () -> userService.delete(999L)
        );

        assertEquals("No se encontro el usuario", thrownException.getMessage());
    }
}
