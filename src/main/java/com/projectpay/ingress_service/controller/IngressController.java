package com.projectpay.ingress_service.controller;

import com.projectpay.ingress_service.exception.UUIDAlreadyPresentException;
import com.projectpay.ingress_service.services.IngressService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.projectpay.dtos.TransactionDTO;
import org.projectpay.dtos.TransactionPayloadDTO;
import org.projectpay.dtos.TransactionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.*;

@RestController
@RequestMapping("/v1/transactions")
@RequiredArgsConstructor
public class IngressController {
    private final IngressService ingressService;

    @PostMapping("/initiate")
    public ResponseEntity<?> transactionInitiated(@RequestBody TransactionPayloadDTO transactionPayloadDTO, @RequestHeader("X-Idempotency-Key") String idempotencyKey ){
        if(ingressService.isIdempotencyDuplicate(idempotencyKey))
            throw new UUIDAlreadyPresentException("A transaction is already being processed.");

        String transactionId = UUID.randomUUID().toString();

        ingressService.transactionInitiated(transactionId,transactionPayloadDTO,idempotencyKey);

        return new ResponseEntity<>(new TransactionDTO(transactionId), HttpStatusCode.valueOf(202));
    }

    @GetMapping("/status/{transactionId}")
    public ResponseEntity<?> transactionStatusResponseEntity(@PathVariable String transactionId) {
        return ingressService.getTransactionStatus(transactionId);
    }
}
