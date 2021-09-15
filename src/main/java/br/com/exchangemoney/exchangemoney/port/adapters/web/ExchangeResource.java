package br.com.exchangemoney.exchangemoney.port.adapters.web;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Value
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ExchangeResource {

    Boolean success;
    String base;
    LocalDate date;
    Map<String, BigDecimal> rates;

    public Boolean containsCurrency(String from, String to) {
        return rates.containsKey(from) && rates.containsKey(to);
    }
}
