package com.company.myfinance.screen.bancaccount;

import io.jmix.ui.screen.*;
import com.company.myfinance.entity.BancAccount;

@UiController("BancAccount.edit")
@UiDescriptor("banc-account-edit.xml")
@EditedEntityContainer("bancAccountDc")
public class BancAccountEdit extends StandardEditor<BancAccount> {
}