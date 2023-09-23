package com.freebox.controller;

import com.freebox.model.UserModel;
import com.freebox.repository.UserRepository;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Endpoints de Usuarios")
public class UserController {

    final
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<UserModel> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/auth/login")
    public Optional<UserModel> loginByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @PostMapping("/user")
    public UserModel saveUser(@RequestBody @Valid UserModel user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/user/{name}")
    public void deleteUser(@PathVariable(value = "name") String name) {
        Optional<UserModel> user = userRepository.findByName(name);
        userRepository.deleteById(user.get().getId());
    }

}
