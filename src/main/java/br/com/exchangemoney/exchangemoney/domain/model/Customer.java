package br.com.exchangemoney.exchangemoney.domain.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "tax_id")
    private String taxId;

    public Customer() {}

    public Customer(String taxId) {
        this.taxId = taxId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
}
