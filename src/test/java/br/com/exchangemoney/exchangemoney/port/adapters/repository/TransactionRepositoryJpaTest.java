package br.com.exchangemoney.exchangemoney.port.adapters.repository;

import br.com.exchangemoney.exchangemoney.domain.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class TransactionRepositoryJpaTest {
	
	private final TransactionRepositorySpringData springData = mock(TransactionRepositorySpringData.class);
	
	private final TransactionRepositoryJpa repository = new TransactionRepositoryJpa(springData);
	
	@Test
	public void shouldSave() {
		
		Transaction transaction = new Transaction(UUID.randomUUID(),
				BigDecimal.ONE,
				"BRL",
				BigDecimal.ONE,
				"USD",
				BigDecimal.ONE,
				OffsetDateTime.now());
		
		repository.save(transaction);
		
		verify(springData, times(1)).save(transaction);
		
	}

}
