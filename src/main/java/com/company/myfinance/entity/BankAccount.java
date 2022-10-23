package com.company.myfinance.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "BANK_ACCOUNT", indexes = {@Index(name = "IDX_BANK_ACCOUNT_CLIENT", columnList = "CLIENT_ID")})
@Entity
public class BankAccount {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    
    @Column(name = "AMOUNT", precision = 19, scale = 2)
    private BigDecimal amount;
    
    @JoinColumn(name = "CLIENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
    
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;
    
    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}