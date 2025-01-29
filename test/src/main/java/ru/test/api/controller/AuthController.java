package ru.test.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.api.model.dto.request.LoginRequest;
import ru.test.api.model.dto.respomse.TokenResponse;
import ru.test.api.security.AuthService;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Аутентификация user")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Авторизация пользователя"
    )
    @PostMapping("/login")
    private TokenResponse login(@RequestBody LoginRequest body) {

        return new TokenResponse(authService.login(body));
    }
}
