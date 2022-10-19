package com.company.myfinance.listener;

import com.company.myfinance.entity.BankAccount;
import com.company.myfinance.entity.Transaction;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionEventListener {

    @Autowired
    private DataManager dataManager;


    @EventListener
    public void onTransactionChangedBeforeCommit(EntityChangedEvent<Transaction> event) {
        if(event.getType() != EntityChangedEvent.Type.DELETED) {
            Id<Transaction> transactionId = event.getEntityId();
            Transaction transaction = dataManager.load(transactionId).one();
            if (transaction.getFrom_acc() == null) {
                replenishAccount(transaction);
            }
            if (transaction.getTo_acc() == null) {
                widhdrowAccount(transaction);
            }
        }
    }

    private void widhdrowAccount(Transaction transaction) {
        BankAccount bankAccount;
        bankAccount = transaction.getFrom_acc();
        BigDecimal subtract = bankAccount.getAmount().subtract(transaction.getTransfer_amount());
        bankAccount.setAmount(subtract);
        dataManager.save(bankAccount);
    }

    private void replenishAccount(Transaction transaction) {
        BankAccount bankAccount;
        bankAccount = transaction.getTo_acc();
        bankAccount.setAmount(bankAccount.getAmount().add(transaction.getTransfer_amount()));
        dataManager.save(bankAccount);
    }

}