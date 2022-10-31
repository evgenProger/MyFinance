package com.company.myfinance.screen.myfinance;

import com.company.myfinance.entity.Client;
import com.company.myfinance.entity.Transaction;
import com.company.myfinance.entity.Type;
import com.company.myfinance.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.component.Label;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("MyFinanceScreen")
@UiDescriptor("my-finance-screen.xml")
public class MyFinanceScreen extends Screen {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private CollectionLoader<Transaction> transactionsDl;
    @Autowired
    private CollectionContainer<Transaction> transactionsDc;
    @Autowired
    private Label totalAmount;
    @Autowired
    private CollectionLoader<Type> typesDl;


    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        transactionsDl.load();
        typesDl.load();
        updateLabels();
    }

//    @Subscribe(id = "transactionsDc", target = Target.DATA_CONTAINER)
//    public void onTransactionsDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<Transaction> event) {
//        updateLabels();
//
//    }

    private void updateLabels() {
        long completedAmount = transactionsDc.getItems().stream()
                .mapToLong(a -> a.getTransfer_amount().longValue()).sum();
        totalAmount.setValue("Total amount: " + completedAmount);
    }
}