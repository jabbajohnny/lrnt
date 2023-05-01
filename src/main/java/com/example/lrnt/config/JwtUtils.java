package com.example.lrnt.config;

import com.example.lrnt.database.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JwtUtils {
    private final JwtDecoder decoder;
    private Jwt jwt;
    private final UserDetailsService userDetailsService;
    private final UserRepository repository;

    public JwtUtils(JwtDecoder decoder, UserDetailsService userDetailsService, UserRepository repository) {
        this.decoder = decoder;
        this.userDetailsService = userDetailsService;
        this.repository = repository;
    }

    public Authentication getAuthenticationFromJwt(String token) {
        jwt = decoder.decode(token);

        Map<String, Object> claims = jwt.getClaims();
        UserDetails user = userDetailsService.loadUserByUsername((String) claims.get("sub"));

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    public String getEmail(String token) {
        jwt = decoder.decode(token);
        Map<String, Object> claims = jwt.getClaims();
        return userDetailsService.loadUserByUsername((String) claims.get("sub")).getUsername();
    }

}
