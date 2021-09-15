package br.com.exchangemoney.exchangemoney.domain.model;

import br.com.exchangemoney.exchangemoney.port.adapters.web.TransactionResource;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "transaction_id", columnDefinition = "uuid")
    private UUID transactionId;

    @Column(name = "customer_id", columnDefinition = "uuid")
    private UUID customerId;

    @Column
    private String currencyFrom;

    @Column
    private BigDecimal amount;

    @Column
    private String currencyTo;

    @Column
    private BigDecimal rate;

    @Column(name = "occurred_on", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime occurredOn;

    @Transient
    private BigDecimal destinyAmount;

    public Transaction() {
    }

    public Transaction(UUID customerId, BigDecimal destinyAmount, String from,
                       BigDecimal amount, String to,
                       BigDecimal rate, OffsetDateTime occurredOn) {
        this.destinyAmount = destinyAmount;
        this.customerId = customerId;
        this.currencyFrom = from;
        this.amount = amount;
        this.currencyTo = to;
        this.rate = rate;
        this.occurredOn = occurredOn;
    }

    public TransactionResource toTransactionResource() {
        return new TransactionResource(
                this.transactionId,
                this.customerId,
                this.currencyFrom,
                this.amount,
                this.currencyTo,
                this.destinyAmount,
                this.rate,
                this.occurredOn);
    }


    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(OffsetDateTime occurredOn) {
        this.occurredOn = occurredOn;
    }

    public BigDecimal getDestinyAmount() {
        return destinyAmount;
    }

    public void setDestinyAmount(BigDecimal destinyAmount) {
        this.destinyAmount = destinyAmount;
    }
}
