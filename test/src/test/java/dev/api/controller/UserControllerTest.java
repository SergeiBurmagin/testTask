package dev.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.test.api.model.dto.email.EmailRequest;
import ru.test.api.model.dto.email.EmailUpdateRequest;
import ru.test.api.model.dto.phone.PhoneRequest;
import ru.test.api.model.dto.phone.PhoneUpdateRequest;
import ru.test.api.model.dto.request.AccountTransferRequest;
import ru.test.api.model.dto.request.UserSearchRequest;
import ru.test.api.model.dto.respomse.UserResponse;
import ru.test.api.security.AuthService;
import ru.test.api.service.UserService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Test
    void searchWithValidRequestReturnsUserList() throws Exception {
        UserSearchRequest request = new UserSearchRequest("John", "Doe");
        List<UserResponse> response = List.of(new UserResponse(1L, "John", "Doe", "john.doe@example.com"));

        when(userService.search(request, 1, 10)).thenReturn(response);

        mockMvc.perform(post("/api/users/search")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    void addUserEmailWithValidRequestAddsEmail() throws Exception {
        EmailRequest request = new EmailRequest(1L, "john.doe@example.com");

        mockMvc.perform(put("/api/users/add/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void removeUserEmailWithValidRequestRemovesEmail() throws Exception {
        EmailRequest request = new EmailRequest(1L, "john.doe@example.com");

        mockMvc.perform(delete("/api/users/remove/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserEmailWithValidRequestUpdatesEmail() throws Exception {
        EmailUpdateRequest request = new EmailUpdateRequest(1L, "john.doe@example.com", "john.new@example.com");

        mockMvc.perform(put("/api/users/update/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"oldEmail\":\"john.doe@example.com\",\"newEmail\":\"john.new@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void addUserPhoneWithValidRequestAddsPhone() throws Exception {
        PhoneRequest request = new PhoneRequest(1L, "1234567890");

        mockMvc.perform(put("/api/users/add/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"phone\":\"1234567890\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void removeUserPhoneWithValidRequestRemovesPhone() throws Exception {
        PhoneRequest request = new PhoneRequest(1L, "1234567890");

        mockMvc.perform(delete("/api/users/remove/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"phone\":\"1234567890\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserPhoneWithValidRequestUpdatesPhone() throws Exception {
        PhoneUpdateRequest request = new PhoneUpdateRequest(1L, "1234567890", "0987654321");

        mockMvc.perform(put("/api/users/update/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"oldPhone\":\"1234567890\",\"newPhone\":\"0987654321\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void transferWithValidRequestTransfersAmount() throws Exception {
        AccountTransferRequest request = new AccountTransferRequest(1L, 2L, new BigDecimal("100.00"));

        mockMvc.perform(post("/api/users/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromUser\":1,\"toUser\":2,\"amount\":100.00}"))
                .andExpect(status().isOk());
    }
}
