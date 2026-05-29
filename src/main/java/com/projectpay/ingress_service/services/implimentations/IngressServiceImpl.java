package com.projectpay.ingress_service.services.implimentations;

import com.projectpay.ingress_service.entities.Transactions;
import com.projectpay.ingress_service.exception.TransactionNotFoundException;
import com.projectpay.ingress_service.repositories.IngressRepository;
import com.projectpay.ingress_service.services.IngressService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.projectpay.dtos.TransactionPayloadDTO;
import org.projectpay.dtos.TransactionStatus;
import org.projectpay.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngressServiceImpl implements IngressService {
    private final IngressRepository ingressRepository;
    private final KafkaTemplate<@NonNull String,@NonNull Object> kafkaTemplate;

    @Override
    @Transactional
    public void transactionInitiated(String transactionId, TransactionPayloadDTO transactionPayloadDTO, String idempotencyKey) {
        Transactions transactions = new Transactions(
                transactionId,
                transactionPayloadDTO.getUserId(),
                transactionPayloadDTO.getMerchantId(),
                transactionPayloadDTO.getAmount(),
                transactionPayloadDTO.getCurrency(),
                Status.PENDING,
                idempotencyKey
        );

        ingressRepository.save(transactions);


        kafkaTemplate.send("payment-initiated-topic",transactionId,transactionPayloadDTO)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Message sent successfully to offset: {}", result.getRecordMetadata().offset());
                    } else {
                        log.error("Failed to send message: {}", ex.getMessage());
                    }
                });
    }

    @Override
    public boolean isIdempotencyDuplicate(String idempotencyKey) {
        return ingressRepository.existsByIdempotencyKey(idempotencyKey);
    }

    @Override
    public ResponseEntity<@NonNull TransactionStatus> getTransactionStatus(String transactionId){
        Transactions transactions = ingressRepository
                .findById(transactionId)
                .orElseThrow(()->new TransactionNotFoundException("Transaction not found with TID = "+transactionId));

        return new ResponseEntity<>(new TransactionStatus(
                transactions.getUserId(),
                transactionId,
                transactions.getAmount(),
                transactions.getStatus()),HttpStatus.OK);
    }
}
