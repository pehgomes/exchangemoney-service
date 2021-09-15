package br.com.exchangemoney.exchangemoney.port.adapters.web;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Value
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class TransactionResource {

    UUID transactionId;
    UUID customerId;
    String from;
    BigDecimal origin;
    String to;
    BigDecimal destiny;
    BigDecimal rate;
    OffsetDateTime occurredOn;

}
