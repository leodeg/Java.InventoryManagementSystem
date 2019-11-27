package com.main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ArrivalController {
    @FXML
    public Button buttonRefreshTable;
    @FXML
    public TableView tableView;
    @FXML
    public TableColumn tableColumnId;
    @FXML
    public TableColumn tableColumnIdProduct;
    @FXML
    public TableColumn tableColumnPrice;
    @FXML
    public TableColumn tableColumnAmount;
    @FXML
    public TableColumn tableColumnDate;
}
