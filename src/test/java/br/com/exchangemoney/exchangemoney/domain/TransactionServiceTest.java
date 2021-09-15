package br.com.exchangemoney.exchangemoney.domain;

import br.com.exchangemoney.exchangemoney.application.transaction.NewTransactionCommand;
import br.com.exchangemoney.exchangemoney.domain.model.Customer;
import br.com.exchangemoney.exchangemoney.domain.model.Transaction;
import br.com.exchangemoney.exchangemoney.domain.port.ExchangePort;
import br.com.exchangemoney.exchangemoney.domain.repository.TransactionRepository;
import br.com.exchangemoney.exchangemoney.domain.transaction.CustomerService;
import br.com.exchangemoney.exchangemoney.domain.transaction.TransactionService;
import br.com.exchangemoney.exchangemoney.port.adapters.repository.TransactionRepositoryJpa;
import br.com.exchangemoney.exchangemoney.port.adapters.service.ExchangeServicePortAdapter;
import br.com.exchangemoney.exchangemoney.port.adapters.web.ExchangeResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TransactionServiceTest {

    private CustomerService customerService = mock(CustomerService.class);
    private ExchangePort exchangePort = mock(ExchangeServicePortAdapter.class);
    private TransactionRepository transactionRepository = mock(TransactionRepositoryJpa.class);

    private TransactionService transactionService = new TransactionService(
            customerService,
            exchangePort,
            transactionRepository);

    @Test
    public void shouldSaveNewExchangeTransaction() {
        var customer = mock(Customer.class);

        given(exchangePort.requestRates(anyString(), anyString())).willReturn(Optional.of(exchangeResource()));
        given(customerService.recoveryCustomer(anyString())).willReturn(customer);

        var transaction = transactionService.save(newTransactionCommand());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        Assertions.assertNotNull(transaction);
    }

    @Test
    public void shouldThrowExceptionWhenCurrencyNotFound() {
        var customer = mock(Customer.class);
        var notNullButInvalidCommand = new NewTransactionCommand(
                "07179227349",
                BigDecimal.TEN,
                "MOEDA",
                "USDE");

        given(exchangePort.requestRates(anyString(), anyString())).willReturn(Optional.of(exchangeResource()));
        given(customerService.recoveryCustomer(anyString())).willReturn(customer);
        assertThrows(ResponseStatusException.class, () -> transactionService.save(notNullButInvalidCommand));
    }

    @Test
    public void shouldVerifyConvertedValueInExchangeTransaction() {
        var customer = mock(Customer.class);
        var tenBrlInDolar = BigDecimal.valueOf(1.91); // retrivied by google;

        var exchangeResource = new ExchangeResource(
                Boolean.TRUE,
                "EUR",
                LocalDate.now(),
                new HashMap<>() {{
                    put("USD", BigDecimal.valueOf(1.18211));
                    put("BRL", BigDecimal.valueOf(6.178657));
                }});

        given(exchangePort.requestRates(anyString(), anyString())).willReturn(Optional.of(exchangeResource));
        given(customerService.recoveryCustomer(anyString())).willReturn(customer);

        var transaction = transactionService.save(newTransactionCommand());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        Assertions.assertEquals(transaction.getDestinyAmount().setScale(2, RoundingMode.HALF_DOWN), tenBrlInDolar);
        Assertions.assertNotNull(transaction.getRate());
    }


    private ExchangeResource exchangeResource() {
        return new ExchangeResource(
                Boolean.TRUE,
                "EUR",
                LocalDate.now(),
                new HashMap<>() {{
                    put("USD", BigDecimal.ONE);
                    put("BRL", BigDecimal.TEN);
                }});
    }

    private NewTransactionCommand newTransactionCommand() {
        return new NewTransactionCommand(
                "07179227349",
                BigDecimal.TEN,
                "BRL",
                "USD");
    }

}

