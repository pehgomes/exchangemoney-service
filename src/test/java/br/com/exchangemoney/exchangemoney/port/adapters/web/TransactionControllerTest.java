package br.com.exchangemoney.exchangemoney.port.adapters.web;

import br.com.exchangemoney.exchangemoney.application.configuration.CustomJacksonObjectMapper;
import br.com.exchangemoney.exchangemoney.application.transaction.NewTransactionCommand;
import br.com.exchangemoney.exchangemoney.application.transaction.TransactionApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest {

    private MockMvc mvc;

    private final TransactionApplicationService transactionApplicationService = BDDMockito.mock(TransactionApplicationService.class);

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(new TransactionController(transactionApplicationService))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(CustomJacksonObjectMapper.configure()))
                .build();
    }

    @Test
    public void shouldRequestToTransactionController() throws Exception {
        var command = new NewTransactionCommand("07181912303",
                BigDecimal.TEN,
                "JPY",
                "USD");

        var transactionResponse = new TransactionResource(UUID.randomUUID(),
                UUID.randomUUID(),
                "JPY",
                BigDecimal.TEN,
                "USD",
                BigDecimal.TEN,
                BigDecimal.TEN,
                OffsetDateTime.now());

        String payload = CustomJacksonObjectMapper.configure().writeValueAsString(command);

        this.mvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

        BDDMockito.given(transactionApplicationService.newTransaction(command))
                .willReturn(transactionResponse);
    }

    @Test
    public void shouldRequestTransactionsByTaxId() throws Exception {

        var transactionResponse = new TransactionResource(UUID.randomUUID(),
                UUID.randomUUID(),
                "JPY",
                BigDecimal.TEN,
                "USD",
                BigDecimal.TEN,
                BigDecimal.TEN,
                OffsetDateTime.now());

        var transactions = List.of(transactionResponse);

        String payload = CustomJacksonObjectMapper.configure().writeValueAsString(transactions);

        this.mvc.perform(get(
                "/transactions/customers/07181912303")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().is(200));

        BDDMockito.given(transactionApplicationService.listTransactions("07181912303"))
                .willReturn(transactions);
    }

}
