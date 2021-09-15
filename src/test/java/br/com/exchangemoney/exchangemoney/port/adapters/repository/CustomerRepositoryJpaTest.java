package br.com.exchangemoney.exchangemoney.port.adapters.repository;

import br.com.exchangemoney.exchangemoney.domain.model.Customer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CustomerRepositoryJpaTest {
	
	private final CustomerRepositorySpringData springData = mock(CustomerRepositorySpringData.class);
	
	private final CustomerRepositoryJpa repository = new CustomerRepositoryJpa(springData);
	
	@Test
	public void shouldSave() {

		Customer customer = new Customer("07182992983");
		
		repository.save(customer);
		
		verify(springData, times(1)).save(customer);
		
	}

}

