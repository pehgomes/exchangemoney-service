package br.com.exchangemoney.exchangemoney.port.adapters.service;

import br.com.exchangemoney.exchangemoney.domain.port.ExchangePort;
import br.com.exchangemoney.exchangemoney.port.adapters.web.ExchangeResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static br.com.exchangemoney.exchangemoney.application.transaction.NewTransactionCommand.Rate;

@Service
@Slf4j
public class ExchangeServicePortAdapter implements ExchangePort {

    private final RestTemplate restTemplate;
    private final String urlExchangeRates;
    private final String apiKey;

    public ExchangeServicePortAdapter(RestTemplate restTemplate,
                                      @Value("${exchange-rates.url}") String urlExchangeRates,
                                      @Value("${exchange-rates.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.urlExchangeRates = urlExchangeRates;
        this.apiKey = apiKey;
    }

    @Override
    public Optional<ExchangeResource> requestRates(String from, String to) {
        try {
            UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(urlExchangeRates.concat("/latest"))
                    .queryParam("access_key", apiKey)
                    .queryParam("symbols", Rate.getBaseSymbols().concat("," + from + "," + to));

            return Optional.ofNullable(restTemplate.getForObject(uri.toUriString(), ExchangeResource.class));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("unexpected error while fetching rates: {}", e);
            return Optional.empty();
        }
    }
}
