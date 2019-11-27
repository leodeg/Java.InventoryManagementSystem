package com.main.controller;

import com.main.model.entity.CategoryEntity;
import com.main.model.jpa.JpaCategoryDao;
import com.mysql.cj.protocol.x.ResultMessageListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public AnchorPane uiMainWindow;
    public AnchorPane categories;
    public AnchorPane products;
    public AnchorPane fact;
    public AnchorPane arrival;
    public AnchorPane consumption;
    public AnchorPane customers;
    public AnchorPane orders;
    public AnchorPane sales;


    public static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

