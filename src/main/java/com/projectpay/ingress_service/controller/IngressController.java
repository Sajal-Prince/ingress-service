package com.projectpay.ingress_service.controller;

import com.projectpay.ingress_service.services.IngressService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.projectpay.dtos.TransactionDTO;
import org.projectpay.dtos.TransactionPayloadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transactions")
@RequiredArgsConstructor
public class IngressController {
    private final IngressService ingressService;

    @GetMapping("/initiate")
    public ResponseEntity<@NonNull TransactionDTO> transactionInitiated(@RequestBody TransactionPayloadDTO transactionPayloadDTO, @RequestHeader("X-Idempotency-Key") String idempotencyKey ){
        return ingressService.transactionInitiated(transactionPayloadDTO);
    }
}
