package br.com.exchangemoney.exchangemoney.domain.repository;

import br.com.exchangemoney.exchangemoney.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);
    Optional<Customer> findByTaxId(String taxId);
}
