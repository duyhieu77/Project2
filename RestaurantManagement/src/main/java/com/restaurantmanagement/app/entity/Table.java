package com.restaurantmanagement.app.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Table {

    private StringProperty tableId;
    private IntegerProperty seats;
    private StringProperty status;

    public Table(String tableId, int seats, String status) {
        this.tableId = new SimpleStringProperty(tableId);
        this.seats = new SimpleIntegerProperty(seats);
        this.status = new SimpleStringProperty(status);
    }

    public String getTableId() {
        return tableId.get();
    }

    public StringProperty tableIdProperty() {
        return tableId;
    }

    public int getSeats() {
        return seats.get();
    }

    public IntegerProperty seatsProperty() {
        return seats;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }
}
