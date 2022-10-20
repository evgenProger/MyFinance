package com.company.myfinance.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "TYPE_", indexes = {
        @Index(name = "IDX_TYPE__USER", columnList = "USER_ID")
})
@Entity(name = "Type_")
public class Type {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinTable(name = "TYPE_TRANSACTION_LINK",
            joinColumns = @JoinColumn(name = "TYPE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "TRANSACTION_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Transaction> transaction_id;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(List<Transaction> transaction_id) {
        this.transaction_id = transaction_id;
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