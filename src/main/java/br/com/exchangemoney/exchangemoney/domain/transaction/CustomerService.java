package br.com.exchangemoney.exchangemoney.domain.transaction;

import br.com.exchangemoney.exchangemoney.domain.model.Customer;
import br.com.exchangemoney.exchangemoney.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer recoveryCustomer(String taxId) {
        Optional<Customer> customer = customerRepository.findByTaxId(taxId);
        if (customer.isEmpty()) {
            return customerRepository.save(new Customer(taxId));
        }
        return customer.orElseThrow();
    }

    public Customer findCustomerByTaxId(String taxId) throws BusinessException {
        return customerRepository.findByTaxId(taxId)
                .orElseThrow(() -> new BusinessException("Customer not found for taxId: "+ taxId));
    }
}
