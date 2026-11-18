package com.finguard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTop100ByOrderByCreatedAtDesc();
    long countByRiskLevel(String riskLevel);
}
