package ru.test.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.test.api.model.dto.email.EmailRequest;
import ru.test.api.model.dto.email.EmailUpdateRequest;
import ru.test.api.model.dto.phone.PhoneRequest;
import ru.test.api.model.dto.phone.PhoneUpdateRequest;
import ru.test.api.model.dto.request.AccountTransferRequest;
import ru.test.api.model.dto.request.UserSearchRequest;
import ru.test.api.model.dto.respomse.UserResponse;
import ru.test.api.security.AuthService;
import ru.test.api.service.AccountService;
import ru.test.api.service.UserService;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {

    private final AccountService accountService;
    private final UserService userService;
    private final AuthService authService;


    private void checkAccess(@NonNull Long userId) {
        var current = authService.getCurrentUserId();
        if (!Objects.equals(current, userId))
            throw new RuntimeException("НЕТ ДОСТУПА ДЛЯ ВЫПОЛНЕНИЯ ДАННОЙ ОПЕРАЦИИ");
    }


    @Operation(
            summary = "Search users"
    )
    @PostMapping("/search")
    public List<UserResponse> search(@RequestParam(name = "page", required = false) Integer page,
                                     @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                     @RequestBody UserSearchRequest userSearchRequest) {
        return userService.search(userSearchRequest, page, pageSize);
    }


    @PutMapping("/add/email")
    public void addUserEmail(@RequestBody EmailRequest request) {
        checkAccess(request.userId());
        userService.addUserEmail(request);
    }


    @DeleteMapping("/remove/email")
    public void removeUserEmail(@RequestBody EmailRequest request) {
        checkAccess(request.userId());
        userService.removeUserEmail(request);
    }


    @PutMapping("/update/email")
    public void updateUserEmail(@RequestBody EmailUpdateRequest request) {
        checkAccess(request.userId());
        userService.updateUserEmail(request);
    }


    @PutMapping("/add/phone")
    public void addUserPhone(@RequestBody PhoneRequest request) {
        checkAccess(request.userId());
        userService.addUserPhone(request);
    }


    @DeleteMapping("/remove/phone")
    public void removeUserPhone(@RequestBody PhoneRequest request) {
        checkAccess(request.userId());
        userService.removeUserPhone(request);
    }


    @PutMapping("/update/phone")
    public void updateUserPhone(@RequestBody PhoneUpdateRequest phoneUpdateRequest) {
        checkAccess(phoneUpdateRequest.userId());
        userService.updateUserPhone(phoneUpdateRequest);
    }

    @Operation(
            summary = "transfer"
    )
    @PostMapping("/transfer")
    public void transfer(@RequestBody AccountTransferRequest request) {
        checkAccess(request.fromUser());
        accountService.transfer(request);
    }
}
