package br.com.udemy.controller;

import br.com.udemy.dto.CredentialsDTO;
import br.com.udemy.dto.TokenDTO;
import br.com.udemy.dto.UserDTO;
import br.com.udemy.entities.User;
import br.com.udemy.exception.InvalidPasswordException;
import br.com.udemy.security.JwtService;
import br.com.udemy.service.UserDetailService;
import br.com.udemy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    private JwtService jwtService;

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

    @PostMapping("/auth")
    public TokenDTO authUser (@RequestBody CredentialsDTO credentials) {
        try {

            User user = User.builder()
                            .username(credentials.getUsername())
                            .password(credentials.getPassword())
                            .build();

            UserDetails authUser = userDetailService.auth(user);

            String token = jwtService.generateToken(user);

            return new TokenDTO(user.getUsername(), token);

        } catch (UsernameNotFoundException | InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
