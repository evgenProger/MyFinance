package com.company.myfinance.screen.transaction;

import io.jmix.ui.screen.*;
import com.company.myfinance.entity.Transaction;

@UiController("Transaction_.edit")
@UiDescriptor("transaction-edit.xml")
@EditedEntityContainer("transactionDc")
public class TransactionEdit extends StandardEditor<Transaction> {
}