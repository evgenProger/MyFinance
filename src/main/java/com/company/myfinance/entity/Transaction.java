package com.company.myfinance.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "TRANSACTION_", indexes = {
        @Index(name = "IDX_TRANSACTION__FROM_ACC", columnList = "FROM_ACC_ID"),
        @Index(name = "IDX_TRANSACTION__TO_ACC_ID", columnList = "TO_ACC_ID_ID")
})
@Entity(name = "Transaction_")
public class Transaction {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "FROM_ACC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private BancAccount from_acc;

    @JoinColumn(name = "TO_ACC_ID_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private BancAccount to_acc_id;

    @Column(name = "CREATE_DATE")
    private LocalDateTime create_date;

    @Column(name = "TRANSFER_AMOUNT", precision = 19, scale = 2)
    private BigDecimal transfer_amount;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

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

    public BancAccount getTo_acc_id() {
        return to_acc_id;
    }

    public void setTo_acc_id(BancAccount to_acc_id) {
        this.to_acc_id = to_acc_id;
    }

    public BancAccount getFrom_acc() {
        return from_acc;
    }

    public void setFrom_acc(BancAccount from_acc) {
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