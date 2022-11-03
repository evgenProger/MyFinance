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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private Transaction sum(List<Transaction> transactions, Type type) {
        long totalSum = transactions.stream().filter(t -> t.getTypes().contains(type))
                .mapToLong(t -> t.getTransfer_amount().longValue()).sum();
        if (transactions.stream().anyMatch(t -> t.getTypes().contains(type))) {
            Transaction transaction = transactions.stream().filter(t -> t.getTypes().contains(type)).findFirst().get();
            transaction.setTransfer_amount(new BigDecimal(totalSum));
            return transaction;
        }
        return null;
    }

    private void findByType() {
        List<Transaction> temp = new ArrayList<>();
        Type type = typeField.getValue();
        List<Transaction> transactionsDcItems = transactionsDc.getItems();
        List<Type> typesDcItems = typesDc.getItems();
        if (type == null) {
            List<Transaction> finalTemp = temp;
            typesDcItems.forEach(t -> finalTemp.add(sum(transactionsDcItems, t)));
        } else {
            temp.add(sum(transactionsDcItems, type));
        }
        temp = temp.stream().filter(Objects::nonNull).toList();
        transactionsDc.setItems(temp);
    }

    private void findByDate() {
        transactionsDl.load();
        List<Transaction> items = transactionsDc.getItems();
        Type type = typeField.getValue();
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
        findByDate();
        findByType();
    }


}
