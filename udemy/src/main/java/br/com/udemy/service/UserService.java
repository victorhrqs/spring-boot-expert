package br.com.udemy.service;

import br.com.udemy.entities.User;
import br.com.udemy.dto.UserDTO;
import br.com.udemy.exception.NotFoundException;
import br.com.udemy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    public User store ( User user ) {

        // encrypting user password
        String cryptPass = encoder.encode(user.getPassword());

        user.setPassword(cryptPass);

        return userRepository.save(user);
    }

    public UserDTO findByUsername (String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Username not found"));

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .admin(user.isAdmin())
                .build();
    }
}
