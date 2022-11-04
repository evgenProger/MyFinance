package com.company.myfinance.screen.myfinance;

import com.company.myfinance.entity.Transaction;
import com.company.myfinance.entity.Type;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.DateField;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@UiController("Report")
@UiDescriptor("my-finance-screen.xml")
public class Report extends Screen {
    @Autowired
    private CollectionLoader<Transaction> transactionsDl;
    @Autowired
    private CollectionContainer<Transaction> transactionsDc;
    @Autowired
    private CollectionLoader<Type> typesDl;
    @Autowired
    private CollectionContainer<Type> typesDc;
    @Autowired
    private DateField fromDate;
    @Autowired
    private DateField toDate;
    @Autowired
    private ComboBox<String> operation;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        transactionsDl.load();
        typesDl.load();
        findByType();
    }

    @Subscribe
    public void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Снятие");
        list.add("Пополнение");
        list.add("Перевод");
        operation.setOptionsList(list);
    }


    private Transaction sum(List<Transaction> transactions, Type type) {
        long totalSum = transactions.stream().filter(t -> t.getTypes().equals(type))
                .mapToLong(t -> t.getTransfer_amount().longValue()).sum();
        if (transactions.stream().anyMatch(t -> t.getTypes().equals(type))) {
            Transaction transaction = transactions.stream().filter(t -> t.getTypes().equals(type)).findFirst().get();
            transaction.setTransfer_amount(new BigDecimal(totalSum));
            return transaction;
        }
        return null;
    }

    private void findByType() {
        List<Transaction> temp = new ArrayList<>();
        List<Transaction> transactionsDcItems = transactionsDc.getItems();
        List<Type> typesDcItems = typesDc.getItems();
        List<Transaction> finalTemp = temp;
        typesDcItems.forEach(t -> finalTemp.add(sum(transactionsDcItems, t)));
        temp = temp.stream().filter(Objects::nonNull).toList();
        transactionsDc.setItems(temp);
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

    private void findByOperation() {
        transactionsDl.load();
        String valueOperation = operation.getValue();
        List<Transaction> transactionsDcItems = transactionsDc.getItems();
        if (valueOperation == null) {
            return;
        }
        switch (valueOperation) {
            case "Снятие" ->
                    transactionsDc.setItems(transactionsDcItems.stream().filter(t -> t.getTo_acc() == null).toList());
            case "Пополнение" ->
                    transactionsDc.setItems(transactionsDcItems.stream().filter(t -> t.getFrom_acc() == null).toList());
            case "Перевод" -> transactionsDc.setItems(transactionsDcItems.stream().filter(t -> t.getTo_acc() != null
                    && t.getFrom_acc() != null).toList());
        }

    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(Button.ClickEvent event) {
        findByOperation();
        findByDate();
        findByType();
    }
}



