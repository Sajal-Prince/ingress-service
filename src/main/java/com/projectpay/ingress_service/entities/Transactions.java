package com.projectpay.ingress_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.projectpay.enums.Status;

@Entity
@Data
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {
    @Id
    private String transactionId;
    private Long userId;
    private Long merchantId;
    private Double amount;
    private String currency;
    private Status status;
    @Column(unique = true)
    private String idempotencyKey;
}
