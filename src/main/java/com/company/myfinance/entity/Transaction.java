package com.company.myfinance.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "TRANSACTION_", indexes = {
        @Index(name = "IDX_TRANSACTION__FROM_ACC", columnList = "FROM_ACC_ID"),
        @Index(name = "IDX_TRANSACTION__TO_ACC", columnList = "TO_ACC_ID")
})
@Entity(name = "Transaction_")
public class Transaction {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "FROM_ACC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount from_acc;

    @JoinColumn(name = "TO_ACC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount to_acc;

    @Column(name = "CREATE_DATE", nullable = false)
    @NotNull
    private LocalDateTime create_date;

    @Column(name = "TRANSFER_AMOUNT", nullable = false, precision = 19, scale = 2)
    @NotNull
    private BigDecimal transfer_amount;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @JoinTable(name = "TYPE_TRANSACTION_LINK", joinColumns = @JoinColumn(name = "TRANSACTION_ID",
            referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "TYPE_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Type> types;

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public BigDecimal getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(BigDecimal transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public BankAccount getTo_acc() {
        return to_acc;
    }

    public void setTo_acc(BankAccount to_acc) {
        this.to_acc = to_acc;
    }

    public BankAccount getFrom_acc() {
        return from_acc;
    }

    public void setFrom_acc(BankAccount from_acc) {
        this.from_acc = from_acc;
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