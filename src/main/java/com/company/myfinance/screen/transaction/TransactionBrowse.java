package com.company.myfinance.screen.transaction;

import io.jmix.ui.screen.*;
import com.company.myfinance.entity.Transaction;

@UiController("Transaction_.browse")
@UiDescriptor("transaction-browse.xml")
@LookupComponent("transactionsTable")
public class TransactionBrowse extends StandardLookup<Transaction> {
}