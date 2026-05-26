package com.projectpay.ingress_service.services;

import lombok.NonNull;
import org.projectpay.dtos.TransactionDTO;
import org.projectpay.dtos.TransactionPayloadDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class IngressService {
    public ResponseEntity<@NonNull TransactionDTO> transactionInitiated(TransactionPayloadDTO transactionPayloadDTO) {
        return new ResponseEntity<>(new TransactionDTO("122323dsdadf"),HttpStatus.OK);
    }
}
