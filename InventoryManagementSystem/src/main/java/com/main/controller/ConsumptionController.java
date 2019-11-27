package com.main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ConsumptionController {
    @FXML
    public TableColumn tableColumnId;
    @FXML
    public TableColumn tableColumnProductName;
    @FXML
    public TableColumn tableColumnPrice;
    @FXML
    public TableColumn tableColumnAmount;
    @FXML
    public TableColumn tableColumnTotalPrice;
    @FXML
    public TableColumn tableColumnDate;
    @FXML
    public TableView tableView;
    @FXML
    public Button buttonRefreshTable;
}
