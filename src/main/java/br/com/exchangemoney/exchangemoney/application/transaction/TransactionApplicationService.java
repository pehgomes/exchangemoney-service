package br.com.exchangemoney.exchangemoney.application.transaction;

import br.com.exchangemoney.exchangemoney.domain.transaction.CustomerService;
import br.com.exchangemoney.exchangemoney.domain.transaction.TransactionService;
import br.com.exchangemoney.exchangemoney.port.adapters.web.TransactionResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionApplicationService {

    private final TransactionService transactionService;
    private final CustomerService customerService;

    public TransactionApplicationService(
            TransactionService transactionService, CustomerService customerService) {
        this.transactionService = transactionService;
        this.customerService = customerService;
    }

    public TransactionResource newTransaction(NewTransactionCommand command) {
        var transaction = transactionService.save(command);
        return transaction.toTransactionResource();
    }

    public List<TransactionResource> listTransactions(String taxId) throws Exception {
        var customer = customerService.findCustomerByTaxId(taxId);
        return transactionService.listByCustomer(customer.getCustomerId())
                .stream()
                .map(a -> new TransactionResource(a.getTransactionId(),
                        a.getCustomerId(),
                        a.getCurrencyFrom(),
                        a.getAmount(),
                        a.getCurrencyTo(),
                        null,
                        a.getRate(),
                        a.getOccurredOn())).collect(Collectors.toList());
    }

}
