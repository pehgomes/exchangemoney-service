package br.com.exchangemoney.exchangemoney.domain.transaction;

import br.com.exchangemoney.exchangemoney.application.transaction.NewTransactionCommand;
import br.com.exchangemoney.exchangemoney.domain.model.Customer;
import br.com.exchangemoney.exchangemoney.domain.model.Transaction;
import br.com.exchangemoney.exchangemoney.domain.port.ExchangePort;
import br.com.exchangemoney.exchangemoney.domain.repository.TransactionRepository;
import br.com.exchangemoney.exchangemoney.port.adapters.web.ExchangeResource;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class TransactionService {

    private final CustomerService customerService;
    private final ExchangePort exchangePort;
    private final TransactionRepository transactionRepository;

    public TransactionService(CustomerService customerService,
                              ExchangePort exchangePort,
                              TransactionRepository transactionRepository) {
        this.customerService = customerService;
        this.exchangePort = exchangePort;
        this.transactionRepository = transactionRepository;
    }

    public Transaction save(NewTransactionCommand command) {
        Customer customer = customerService.recoveryCustomer(command.getTaxId());
        ExchangeResource exchange = exchangePort.requestRates(command.getFrom(), command.getTo()).orElseThrow();

        BigDecimal destinationValue = convertToDestinationValue(command, exchange);
        BigDecimal rate = command.getAmount().divide(destinationValue, 2, RoundingMode.DOWN);

        var transaction = new Transaction(customer.getCustomerId(), destinationValue,
                command.getFrom(), command.getAmount(),
                command.getTo(), rate, OffsetDateTime.now());

        transactionRepository.save(transaction);
        return transaction;
    }

    public List<Transaction> listByCustomer(UUID customerId) {
        return transactionRepository.findByCustomer(customerId);
    }

    private BigDecimal convertToDestinationValue(NewTransactionCommand command, ExchangeResource exchange) {
        if (!exchange.containsCurrency(command.getFrom(), command.getTo()))
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "currency uncknow");

        BigDecimal from = exchange.getRates().get(command.getFrom());
        BigDecimal to = exchange.getRates().get(command.getTo());
        return command.getAmount()
                .divide(from, 4, RoundingMode.DOWN)
                .multiply(to).setScale(4, RoundingMode.DOWN);
    }

}
