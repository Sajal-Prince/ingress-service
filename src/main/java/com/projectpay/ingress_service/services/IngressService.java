package com.projectpay.ingress_service.services;

import org.projectpay.dtos.TransactionPayloadDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface IngressService {
    @Async
    void transactionInitiated(String transactionId, TransactionPayloadDTO transactionPayloadDTO, String idempotencyKey);

    boolean isIdempotencyDuplicate(String idempotencyKey);
}
