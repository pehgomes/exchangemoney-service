package br.com.exchangemoney.exchangemoney.domain;

import br.com.exchangemoney.exchangemoney.domain.model.Customer;
import br.com.exchangemoney.exchangemoney.domain.repository.CustomerRepository;
import br.com.exchangemoney.exchangemoney.domain.transaction.CustomerService;
import br.com.exchangemoney.exchangemoney.port.adapters.repository.CustomerRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

public class CustomerServiceTest {

    private CustomerRepository customerRepository = BDDMockito.mock(CustomerRepositoryJpa.class);
    private CustomerService customerService = new CustomerService(customerRepository);

    @Test
    public void shouldRecoveryInsistentUser() {
        BDDMockito.when(customerRepository.findByTaxId(BDDMockito.any()))
                .thenReturn(Optional.empty());

        customerService.recoveryCustomer("07171891357");

        BDDMockito.verify(customerRepository, Mockito.times(1))
                .save(BDDMockito.any(Customer.class));

    }

    @Test
    public void shouldRecoveryExistentUser() {
        var taxId = "07171891356";
        var customer = new Customer(taxId);

        BDDMockito.given(customerRepository.findByTaxId(taxId))
                .willReturn(Optional.of(customer));

        customerService.recoveryCustomer(taxId);

        BDDMockito.verify(customerRepository, Mockito.times(0))
                .save(BDDMockito.any(Customer.class));

    }

}
