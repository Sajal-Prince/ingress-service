package com.projectpay.ingress_service.services;

import com.projectpay.ingress_service.entities.Transactions;
import com.projectpay.ingress_service.exception.TransactionNotFoundException;
import com.projectpay.ingress_service.repositories.IngressRepository;
import lombok.RequiredArgsConstructor;
import org.projectpay.dtos.TransactionStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IngressConsumer {

    private final IngressRepository ingressRepository;

    @KafkaListener(topics = "final-transaction-status",groupId = "transaction-consumed")
    public void transactionConsumer(TransactionStatus transactionStatus){
        Transactions transactions = ingressRepository.findById(transactionStatus.getTransactionId()).orElseThrow(()->new TransactionNotFoundException("Transaction not found"));
        transactions.setStatus(transactionStatus.getStatus());
        ingressRepository.save(transactions);
    }
}
