package br.com.exchangemoney.exchangemoney.domain.repository;

import br.com.exchangemoney.exchangemoney.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    void save(Transaction transaction);
    List<Transaction> findByCustomer(UUID customerId);
}
