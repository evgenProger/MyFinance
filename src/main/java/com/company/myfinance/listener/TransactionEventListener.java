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
    public void onTransactionChangedBeforeCommit(EntityChangedEvent<Transaction> event) throws Exception {
        if(event.getType() != EntityChangedEvent.Type.DELETED) {
            Id<Transaction> transactionId = event.getEntityId();
            Transaction transaction = dataManager.load(transactionId).one();
            if (transaction.getFrom_acc() == null) {
                replenishAccount(transaction);
            }
            if (transaction.getTo_acc() == null) {
                widhdrowAccount(transaction);
            }
            if (transaction.getFrom_acc() != null && transaction.getTo_acc() != null) {
                transferMoney(transaction);
            }
        }
    }

    private void widhdrowAccount(Transaction transaction) throws Exception {
        BankAccount bankAccount;
        bankAccount = transaction.getFrom_acc();
        BigDecimal subtract = bankAccount.getAmount().subtract(transaction.getTransfer_amount());
        if (subtract.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Недостаточно средств");
        }
        bankAccount.setAmount(subtract);
        dataManager.save(bankAccount);
    }

    private void replenishAccount(Transaction transaction) {
        BankAccount bankAccount;
        bankAccount = transaction.getTo_acc();
        bankAccount.setAmount(bankAccount.getAmount().add(transaction.getTransfer_amount()));
        dataManager.save(bankAccount);
    }

    private void transferMoney(Transaction transaction) throws Exception {
        widhdrowAccount(transaction);
        replenishAccount(transaction);
    }
}