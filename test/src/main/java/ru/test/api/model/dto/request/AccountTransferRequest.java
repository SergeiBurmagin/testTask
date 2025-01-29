package ru.test.api.model.dto.request;

import java.math.BigDecimal;

public record AccountTransferRequest(Long fromUser, Long toUser, BigDecimal amount) {
}