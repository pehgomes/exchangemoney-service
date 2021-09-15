package br.com.exchangemoney.exchangemoney.port.adapters.repository;

import br.com.exchangemoney.exchangemoney.ExchangemoneyApplicationTests;
import br.com.exchangemoney.exchangemoney.domain.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionRepositorySpringDataTest extends ExchangemoneyApplicationTests {

    @Autowired
    private TransactionRepositorySpringData repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void shouldSave() {

        Transaction transaction = new Transaction(UUID.randomUUID(),
                BigDecimal.ONE,
                "BRL",
                BigDecimal.ONE,
                "USD",
                BigDecimal.ONE,
                OffsetDateTime.now());

        assertThat(repository.count()).isEqualTo(0);

        repository.save(transaction);

        assertThat(repository.count()).isEqualTo(1);

    }
}
