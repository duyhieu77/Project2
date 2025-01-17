package com.restaurantmanagement.app.service;

import com.restaurantmanagement.app.entity.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MenuService {

    private ObservableList<MenuItem> menuList;

    public MenuService() {
        menuList = FXCollections.observableArrayList(
                new MenuItem("M001", "Pizza", 12.99, "Main Course"),
                new MenuItem("M002", "Pasta", 8.99, "Main Course"),
                new MenuItem("M003", "Cola", 2.50, "Beverage")
        );
    }

    public ObservableList<MenuItem> getMenuList() {
        return menuList;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuList.add(menuItem);
    }

    public void editMenuItem(MenuItem oldMenuItem, MenuItem newMenuItem) {
        int index = menuList.indexOf(oldMenuItem);
        if (index >= 0) {
            menuList.set(index, newMenuItem);
        }
    }

    public void deleteMenuItem(MenuItem menuItem) {
        menuList.remove(menuItem);
    }
}
