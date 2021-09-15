package br.com.exchangemoney.exchangemoney.port.adapters.web;

import br.com.exchangemoney.exchangemoney.application.transaction.NewTransactionCommand;
import br.com.exchangemoney.exchangemoney.application.transaction.TransactionApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionApplicationService transactionApplicationService;

    public TransactionController(TransactionApplicationService transactionApplicationService) {
        this.transactionApplicationService = transactionApplicationService;
    }

    @PostMapping
    public ResponseEntity<TransactionResource> exchange(@Validated @RequestBody NewTransactionCommand command) {
        var transaction = transactionApplicationService.newTransaction(command);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{taxId}")
    public ResponseEntity<List<TransactionResource>> listExchange(@PathVariable("taxId") String taxId) throws Exception {
        return new ResponseEntity<>(transactionApplicationService.listTransactions(taxId), HttpStatus.OK);
    }
}
