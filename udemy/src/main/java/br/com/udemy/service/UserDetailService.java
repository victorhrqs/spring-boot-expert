package br.com.udemy.service;

import br.com.udemy.dto.UserDTO;
import br.com.udemy.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired // injeta o password encoder criado na SecurityConfig
    private PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @Override // retorna o usuário do banco de dados
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.findByUsername(username);

        String[] roles = user.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

    public UserDetails auth ( br.com.udemy.entities.User user ) {
        UserDetails userFromDB = this.loadUserByUsername(user.getUsername());

        boolean passMatches = encoder.matches(user.getPassword(), userFromDB.getPassword());

        if ( passMatches ) return userFromDB;

        throw new InvalidPasswordException("Invalid password");
    }
}
