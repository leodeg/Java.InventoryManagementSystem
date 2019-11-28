package com.main.controller;

import com.main.model.entity.ConsumptionEntity;
import com.main.model.entity.OrderEntity;
import com.main.model.jpa.JpaOrderDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {
    @FXML
    public Button buttonNewOrder;
    @FXML
    public Button buttonRefreshTable;

    @FXML
    public TableView<OrderEntity> tableView;
    @FXML
    public TableColumn<OrderEntity, Integer> tableColumnId;
    @FXML
    public TableColumn<OrderEntity, Integer> tableColumnProductId;
    @FXML
    public TableColumn<OrderEntity, Integer> tableColumnCustomerId;
    @FXML
    public TableColumn<OrderEntity, Double> tableColumnPrice;
    @FXML
    public TableColumn<OrderEntity, Integer> tableColumnAmount;
    @FXML
    public TableColumn<OrderEntity, Double> tableColumnTotalPrice;
    @FXML
    public TableColumn<OrderEntity, Date> tableColumnDate;

    private JpaOrderDao orderDao;

    public OrdersController () {
        orderDao = new JpaOrderDao();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNewOrder.setOnAction(this::OnPress_Button_NewOrder);
        buttonRefreshTable.setOnAction(this::OnPress_Button_RefreshTable);
    }

    @FXML
    public void OnPress_Button_NewOrder (ActionEvent event) {

    }

    @FXML
    public void OnPress_Button_RefreshTable (ActionEvent event) {
        displayInformationToTableView ();
    }

    private void displayInformationToTableView () {
        ObservableList<OrderEntity> data = FXCollections.observableArrayList(orderDao.getAll());
        if (data.size() > 0) {
            tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
            tableColumnProductId.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
            tableColumnCustomerId.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
            tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            tableColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableView.setItems(data);
        }
    }
}
