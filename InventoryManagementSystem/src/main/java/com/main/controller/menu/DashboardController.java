package com.main.controller.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public Label labelTotalFactInventory;
    @FXML
    public Label labelTotalArrivalInventory;
    @FXML
    public Label labelTotalConsumptionInventory;
    @FXML
    public Label labelTotalSales;
    @FXML
    public Label labelTotalSalesPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



}
