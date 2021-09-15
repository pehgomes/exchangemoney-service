package br.com.exchangemoney.exchangemoney.port.adapters.repository;

import br.com.exchangemoney.exchangemoney.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepositorySpringData extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByTaxId(String taxId);

}
