package com.company.myfinance.screen.myfinance;

import com.company.myfinance.entity.Transaction;
import com.company.myfinance.entity.Type;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.DateField;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
    private CollectionContainer<Type> typesDc;
    @Autowired
    private DateField fromDate;
    @Autowired
    private DateField toDate;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        transactionsDl.load();
        typesDl.load();
    }

    private void findByType() {
        transactionsDl.load();
        Type type = typeField.getValue();
        List<Transaction> items = transactionsDc.getItems();
        if (type == null) {
            transactionsDl.load();
            return;
        }
        List<Transaction> transactions = items.stream().filter(t -> t.getTypes()
                        .contains(type))
                        .toList();
        transactionsDc.setItems(transactions);
    }

    private void findByDate() {
        List<Transaction> items = transactionsDc.getItems();
        Date dateFrom = (Date) fromDate.getValue();
        Date dateTo = (Date) toDate.getValue();
        if (dateTo == null) {
            dateTo = new Date();
        }
        if (dateFrom == null) {
            dateFrom = new Date(0);
        }
        LocalDateTime dateFromNew = LocalDateTime.ofInstant(dateFrom.toInstant(), ZoneId.systemDefault());
        LocalDateTime dateToNew = LocalDateTime.ofInstant(dateTo.toInstant(), ZoneId.systemDefault());
        List<Transaction> transactions = items.stream().filter(t -> (t.getCreate_date().isAfter(dateFromNew)
                || t.getCreate_date().equals(dateFromNew))
                && (t.getCreate_date().isBefore(dateToNew)
                || t.getCreate_date().equals(dateToNew))).toList();
        transactionsDc.setItems(transactions);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(Button.ClickEvent event) {
        findByType();
        findByDate();
    }
}
