package com.company.myfinance.screen.myfinance;

import com.company.myfinance.entity.Client;
import com.company.myfinance.entity.Transaction;
import com.company.myfinance.entity.Type;
import com.company.myfinance.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Table;
import io.jmix.ui.component.data.TableItems;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;


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
