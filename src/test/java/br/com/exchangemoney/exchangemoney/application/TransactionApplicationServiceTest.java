package br.com.exchangemoney.exchangemoney.application;

import br.com.exchangemoney.exchangemoney.application.transaction.NewTransactionCommand;
import br.com.exchangemoney.exchangemoney.application.transaction.TransactionApplicationService;
import br.com.exchangemoney.exchangemoney.domain.model.Transaction;
import br.com.exchangemoney.exchangemoney.domain.transaction.CustomerService;
import br.com.exchangemoney.exchangemoney.domain.transaction.TransactionService;
import br.com.exchangemoney.exchangemoney.port.adapters.web.TransactionResource;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TransactionApplicationServiceTest {

    private TransactionService transactionService = mock(TransactionService.class);
    private CustomerService customerService = mock(CustomerService.class);

    private TransactionApplicationService transactionApplicationService =
            new TransactionApplicationService(transactionService, customerService);

    @Test
    public void shouldCreateTransaction() {
        var command = new NewTransactionCommand("07179227349", BigDecimal.TEN, "BRL", "USD");
        var transaction = mock(Transaction.class);
        var transactionResponse = new TransactionResource(UUID.randomUUID(),
                UUID.randomUUID(),
                "BRL",
                BigDecimal.TEN,
                "USD",
                BigDecimal.TEN,
                BigDecimal.TEN,
                OffsetDateTime.now());

        given(transactionService.save(any(NewTransactionCommand.class))).willReturn(transaction);
        given(transaction.toTransactionResource()).willReturn(transactionResponse);

        var response = transactionApplicationService.newTransaction(command);

        verify(transactionService, times(1)).save(any(NewTransactionCommand.class));
        Assert.notNull(response, "null object unexpected");
    }
}
