package dev.api.controller;

import dev.api.ApplicationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.test.api.model.dto.request.LoginRequest;
import ru.test.api.model.dto.respomse.TokenResponse;
import ru.test.api.security.AuthService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
class AuthControllerTest extends ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void loginWithValidCredentialsReturnsToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest("validUser", "validPassword");
        TokenResponse tokenResponse = new TokenResponse("validToken");

        when(authService.login(loginRequest)).thenReturn("validToken");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"validUser\",\"password\":\"validPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("validToken"));
    }

    @Test
    void loginWithInvalidCredentialsReturnsUnauthorized() throws Exception {
        LoginRequest loginRequest = new LoginRequest("invalidUser", "invalidPassword");

        when(authService.login(loginRequest)).thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"invalidUser\",\"password\":\"invalidPassword\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginWithEmptyUsernameReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\",\"password\":\"somePassword\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginWithEmptyPasswordReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"someUser\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginWithMissingUsernameReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"somePassword\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginWithMissingPasswordReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"someUser\"}"))
                .andExpect(status().isBadRequest());
    }
}
