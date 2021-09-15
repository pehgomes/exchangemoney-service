package br.com.exchangemoney.exchangemoney.port.adapters.service;

import br.com.exchangemoney.exchangemoney.application.configuration.CustomJacksonObjectMapper;
import br.com.exchangemoney.exchangemoney.application.configuration.RestTemplateConfiguration;
import br.com.exchangemoney.exchangemoney.application.transaction.NewTransactionCommand;
import br.com.exchangemoney.exchangemoney.domain.port.ExchangePort;
import br.com.exchangemoney.exchangemoney.port.adapters.web.ExchangeResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class ExchangeServiceAdapterTest {

    private static final String URL = "http://localhost:8000";
    private static final String API_KEY = "6b1d2d04-157a-11ec-82a8-0242ac130003";
    private MockRestServiceServer server;
    private ObjectMapper objectMapper = CustomJacksonObjectMapper.configure();

    public static final ObjectMapper MAPPER = CustomJacksonObjectMapper.configure();
    public static final RestTemplate REST_TEMPLATE = new RestTemplateConfiguration().restTemplate(new MappingJackson2HttpMessageConverter(MAPPER));

    private ExchangePort exchangeServicePortAdapter;

    @BeforeEach
    void setUp() {
        server = MockRestServiceServer.bindTo(REST_TEMPLATE).build();
        this.exchangeServicePortAdapter = new ExchangeServicePortAdapter(REST_TEMPLATE, URL, API_KEY);
    }

    @Test
    public void shouldReturnNewExchange() {

        server.expect(once(), requestTo((URL + "/latest?access_key=" + API_KEY + "&symbols=" + NewTransactionCommand.Rate.getBaseSymbols()
                        .concat("," + "BRL" + "," + "USD")
                        .replaceAll("\\s", "%20"))
                        .trim()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\n" +
                                "    \"success\": true,\n" +
                                "    \"timestamp\": 1631641444,\n" +
                                "    \"base\": \"EUR\",\n" +
                                "    \"date\": \"2021-09-14\",\n" +
                                "    \"rates\": {\n" +
                                "        \"BRL\": 6.184007,\n" +
                                "        \"USD\": 1.182208,\n" +
                                "        \"EUR\": 1,\n" +
                                "        \"JPY\": 129.57884\n" +
                                "    }\n" +
                                "}"));

        ExchangeResource response = exchangeServicePortAdapter.requestRates("BRL", "USD").orElseThrow();
        assertTrue(response.containsCurrency("BRL", "USD"));
    }

}
