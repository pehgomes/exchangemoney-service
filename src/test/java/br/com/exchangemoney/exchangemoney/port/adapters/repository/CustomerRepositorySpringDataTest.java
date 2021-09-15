package br.com.exchangemoney.exchangemoney.port.adapters.repository;

import br.com.exchangemoney.exchangemoney.ExchangemoneyApplicationTests;
import br.com.exchangemoney.exchangemoney.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerRepositorySpringDataTest extends ExchangemoneyApplicationTests {

    @Autowired
    private CustomerRepositorySpringData repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void shouldSave() {

        Customer transaction = new Customer("07182992983");

        assertThat(repository.count()).isEqualTo(0);

        repository.save(transaction);

        assertThat(repository.count()).isEqualTo(1);

    }
}
