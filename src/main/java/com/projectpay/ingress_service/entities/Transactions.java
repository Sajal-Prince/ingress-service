package com.projectpay.ingress_service.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.projectpay.enums.Status;

@Entity
@Data
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;
    private Long userId;
    private Long merchantId;
    private Double amount;
    private String currency;
    private Status status;
}
