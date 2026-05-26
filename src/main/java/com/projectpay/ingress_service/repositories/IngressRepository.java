package com.projectpay.ingress_service.repositories;

import com.projectpay.ingress_service.entities.Transactions;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngressRepository extends JpaRepository<@NonNull Transactions,@NonNull String> {
    boolean existsByIdempotencyKey(String idempotencyKey);
}
