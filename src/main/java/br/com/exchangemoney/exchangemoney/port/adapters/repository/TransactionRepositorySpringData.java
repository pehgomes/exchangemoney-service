package br.com.exchangemoney.exchangemoney.port.adapters.repository;

import br.com.exchangemoney.exchangemoney.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepositorySpringData extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByCustomerId(UUID customerId);
}
