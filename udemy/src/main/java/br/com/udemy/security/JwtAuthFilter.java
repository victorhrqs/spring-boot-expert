package br.com.udemy.security;

import br.com.udemy.service.UserDetailService;
import br.com.udemy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserDetailService userDetailService;

    public JwtAuthFilter(JwtService jwtService, UserDetailService userDetailService) {
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");

        if ( authorization != null && authorization.startsWith("Bearer") ) {
            String token = authorization.split(" ")[1];

            boolean validToken = jwtService.isValidToken(token);

            if (validToken) {
                String userCredentials = jwtService.getUserCredentials(token);

                UserDetails userFromDB = userDetailService.loadUserByUsername(userCredentials);

                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userFromDB, null, userFromDB.getAuthorities());

                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(user);
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
