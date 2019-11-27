package com.main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InventoryController {
    @FXML
    public Button buttonConsumptionRefreshTable;
    @FXML
    public TableView tableConsumptionView;
    @FXML
    public TableColumn tableConsumptionColumnId;
    @FXML
    public TableColumn tableConsumptionColumnIdProduct;
    @FXML
    public TableColumn tableConsumptionColumnAmount;
    @FXML
    public TableColumn tableConsumptionColumnPrice;
    @FXML
    public TableColumn tableConsumptionColumnDate;

    @FXML
    public Button buttonFactRefreshTable;
    @FXML
    public TableView tableFactView;
    @FXML
    public TableColumn tableFactColumnId;
    @FXML
    public TableColumn tableFactColumnIdProduct;
    @FXML
    public TableColumn tableFactColumnAmount;
    @FXML
    public TableColumn tableFactColumnPrice;
    @FXML
    public TableColumn tableFactColumnDate;

    @FXML
    public Button buttonArrivalRefreshTable;
    @FXML
    public TableView tableArrivalView;
    @FXML
    public TableColumn tableArrivalColumnId;
    @FXML
    public TableColumn tableArrivalColumnProductName;
    @FXML
    public TableColumn tableArrivalColumnPrice;
    @FXML
    public TableColumn tableArrivalColumnAmount;
    @FXML
    public TableColumn tableArrivalColumnTotalPrice;
    @FXML
    public TableColumn tableArrivalColumnDate;


}
