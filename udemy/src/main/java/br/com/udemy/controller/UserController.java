package br.com.udemy.controller;

import br.com.udemy.dto.UserDTO;
import br.com.udemy.entities.User;
import br.com.udemy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO store (@RequestBody @Valid User user) {

        User u = userService.store(user);

        return UserDTO.builder()
                .id(u.getId())
                .username(u.getUsername())
                .password(u.getPassword())
                .admin(u.isAdmin())
                .build();
    }
}
