package com.company.myfinance.screen.bancaccount;

import io.jmix.ui.screen.*;
import com.company.myfinance.entity.BancAccount;

@UiController("BancAccount.browse")
@UiDescriptor("banc-account-browse.xml")
@LookupComponent("bancAccountsTable")
public class BancAccountBrowse extends StandardLookup<BancAccount> {
}