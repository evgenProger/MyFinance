package com.company.myfinance.screen.myfinance;

import com.company.myfinance.entity.Transaction;
import com.company.myfinance.entity.Type;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@UiController("Report")
@UiDescriptor("my-finance-screen.xml")
public class Report extends Screen {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private CollectionLoader<Transaction> transactionsDl;
    @Autowired
    private CollectionContainer<Transaction> transactionsDc;
    @Autowired
    private CollectionLoader<Type> typesDl;
    @Autowired
    private Notifications notifications;
    @Autowired
    private EntityComboBox<Type> typeField;
    @Autowired
    private CollectionContainer<Type> typesDc ;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        transactionsDl.load();
        typesDl.load();
    }

    private void findByType() {
        Type type = typeField.getValue();
        List<Transaction> items = transactionsDc.getItems();
        if (type == null) {
            transactionsDl.load();
            return;
        }
        List<Transaction> transactions = items.stream().filter(t -> t.getTypes().contains(type)).toList();
        transactionsDc.setItems(transactions);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(Button.ClickEvent event) {
        findByType();
    }
}
