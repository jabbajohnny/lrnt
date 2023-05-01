package com.example.lrnt.config;

import com.example.lrnt.account.AccountVerifier;
import com.example.lrnt.database.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder encoder;
    private final UserDetailsService userDetailsService;
    private final UserRepository repository;

    public TokenService(JwtEncoder encoder, UserDetailsService userDetailsService, UserRepository repository) {
        this.encoder = encoder;
        this.userDetailsService = userDetailsService;
        this.repository = repository;
    }


    public String generateToken(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (AccountVerifier.checkCredentials(email, password, repository)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
            Instant now = Instant.now();
            String scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plus(3, ChronoUnit.DAYS))
                    .subject(authentication.getName())
                    .claim("scope", scope)
                    .build();
            return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        } else {
            throw new BadCredentialsException("Wrong username or password!");
        }

    }
}
