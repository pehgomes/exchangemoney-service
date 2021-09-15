package br.com.exchangemoney.exchangemoney.domain.port;

import br.com.exchangemoney.exchangemoney.port.adapters.web.ExchangeResource;

import java.util.Optional;

public interface ExchangePort {

    Optional<ExchangeResource> requestRates(String from, String to);
}
