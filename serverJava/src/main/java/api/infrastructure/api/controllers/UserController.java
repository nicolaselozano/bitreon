package api.infrastructure.api.controllers;

import api.domain.models.UserEntity;
import api.application.services.UserService;
import api.application.dto.LoginDto;
import api.application.dto.PublicUserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> getUsers() {

        return userService.getAll();

    }

    @PostMapping
    public UserEntity addUser(@RequestBody UserEntity userEntity) {
        return userService.save(userEntity);
    }

    @PostMapping("/register")
    public PublicUserDto registerUser(@RequestBody UserEntity userEntity) {
        try {
            return userService.registerUser(userEntity);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/login")
    public PublicUserDto loginUser(@RequestParam String username,
                                   @RequestParam String email,
                                   @RequestParam String password) {

        LoginDto loginData = new LoginDto();
        loginData.setUsername(username);
        loginData.setPassword(password);
        loginData.setEmail(email);

        return userService.loginUser(loginData);
    }

    @DeleteMapping
    public UserEntity deleteUser(@RequestParam Long id) {
        return userService.delete(id);
    }

}
