package ru.test.api.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.test.api.model.dto.email.Email;
import ru.test.api.model.dto.request.LoginRequest;
import ru.test.api.model.entity.User;
import ru.test.api.service.UserService;

import java.util.Collections;

import static ru.test.api.security.JwtUtil.generateJwtToken;
import static ru.test.api.security.JwtUtil.getUserId;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public void authenticate(HttpServletRequest request, HttpServletResponse response) throws UsernameNotFoundException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        var userId = getUserId(authorizationHeader);

        if (userId != null) {
            User user = userService.findById(userId);

            UserDetails details = new UserSecurity(
                    user.getId(),
                    user.getPassword(),
                    user.getName(),
                    Collections.singleton(new SimpleGrantedAuthority("USER")));
            Authentication auth = new UsernamePasswordAuthenticationToken(details, "", details.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }


    public String login(@NonNull @Valid LoginRequest request) {
        var email = new Email(request.email());
        var password = request.password();

        User user = userService.findByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("Invalid username or password!");

        return generateJwtToken(user.getId());
    }


    public Long getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserSecurity details = (UserSecurity) authentication.getPrincipal();
        if (details == null)
            throw new RuntimeException("UserDetails is Empty");

        return details.getId();
    }
}
