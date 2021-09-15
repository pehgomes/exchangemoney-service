package br.com.exchangemoney.exchangemoney.port.adapters.repository;

import br.com.exchangemoney.exchangemoney.domain.model.Customer;
import br.com.exchangemoney.exchangemoney.domain.repository.CustomerRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class CustomerRepositoryJpa implements CustomerRepository {

    private final CustomerRepositorySpringData customerRepositorySpringData;

    public CustomerRepositoryJpa(CustomerRepositorySpringData customerRepositorySpringData) {
        this.customerRepositorySpringData = customerRepositorySpringData;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepositorySpringData.save(customer);
    }

    @Override
    public Optional<Customer> findByTaxId(String taxId) {
        return customerRepositorySpringData.findByTaxId(taxId);
    }
}
