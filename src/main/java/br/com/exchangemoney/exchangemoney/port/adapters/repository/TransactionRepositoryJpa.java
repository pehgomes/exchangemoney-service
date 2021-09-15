package br.com.exchangemoney.exchangemoney.port.adapters.repository;

import br.com.exchangemoney.exchangemoney.domain.model.Transaction;
import br.com.exchangemoney.exchangemoney.domain.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class TransactionRepositoryJpa implements TransactionRepository {

    private final TransactionRepositorySpringData transactionRepositorySpringData;

    public TransactionRepositoryJpa(TransactionRepositorySpringData transactionRepositorySpringData) {
        this.transactionRepositorySpringData = transactionRepositorySpringData;
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepositorySpringData.save(transaction);
    }

    @Override
    public List<Transaction> findByCustomer(UUID customerId) {
        return transactionRepositorySpringData.findByCustomerId(customerId);
    }
}
