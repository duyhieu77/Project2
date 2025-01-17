package com.restaurantmanagement.app.service;

import com.restaurantmanagement.app.entity.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableService {

    private ObservableList<Table> tableList;

    public TableService() {
        tableList = FXCollections.observableArrayList(
                new Table("T001", 4, "Available"),
                new Table("T002", 2, "Occupied"),
                new Table("T003", 6, "Available")
        );
    }

    public ObservableList<Table> getTableList() {
        return tableList;
    }

    public void addTable(Table table) {
        tableList.add(table);
    }

    public void editTable(Table oldTable, Table newTable) {
        int index = tableList.indexOf(oldTable);
        if (index >= 0) {
            tableList.set(index, newTable);
        }
    }

    public void deleteTable(Table table) {
        tableList.remove(table);
    }
}
