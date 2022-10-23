package com.company.myfinance.screen.bankaccount;

import io.jmix.ui.screen.*;
import com.company.myfinance.entity.BankAccount;

@UiController("BankAccount.browse")
@UiDescriptor("bank-account-browse.xml")
@LookupComponent("bankAccountsTable")
public class BankAccountBrowse extends StandardLookup<BankAccount> {
}