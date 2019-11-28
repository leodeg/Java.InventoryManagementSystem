package com.main.controller;

import com.main.model.entity.OrderEntity;
import com.main.model.entity.SaleEntity;
import com.main.model.jpa.JpaCustomerDao;
import com.main.model.jpa.JpaOrderDao;
import com.main.model.jpa.JpaProductDao;
import com.main.model.jpa.JpaSaleDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    public Button buttonToSales;

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
    private JpaProductDao productDao;
    private JpaCustomerDao customerDao;
    private JpaSaleDao saleDao;

    public OrdersController() {
        orderDao = new JpaOrderDao();
        productDao = new JpaProductDao();
        customerDao = new JpaCustomerDao();
        saleDao = new JpaSaleDao();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNewOrder.setOnAction(this::OnPress_Button_NewOrder);
        buttonRefreshTable.setOnAction(this::OnPress_Button_RefreshTable);
        buttonToSales.setOnAction(this::OnPress_Button_ToSales);
    }

    @FXML
    private void OnPress_Button_NewOrder(ActionEvent event) {

    }

    @FXML
    private void OnPress_Button_RefreshTable(ActionEvent event) {
        displayInformationToTableView();
    }

    @FXML
    private void OnPress_Button_ToSales(ActionEvent event) {
        OrderEntity selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "To Sale", "Please choose order from table below.");
            return;
        }

        SaleEntity saleEntity = getSaleEntity(selectedItem);
        try {
            saleDao.save(saleEntity);
            OrderEntity orderEntity = orderDao.get(selectedItem.getIdOrder()).get();
            orderDao.delete(orderEntity);
            MainController.showAlert(Alert.AlertType.CONFIRMATION, "To Sale", "Complete");
            displayInformationToTableView();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "To Sale", ex.getMessage());
        }
    }

    private void displayInformationToTableView() {
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

    private SaleEntity getSaleEntity(OrderEntity selectedItem) {
        String productName = productDao.get(selectedItem.getIdProduct()).get().getName();
        String customerName = customerDao.get(selectedItem.getIdCustomer()).get().getName();
        return new SaleEntity(productName, customerName, selectedItem.getPrice(), selectedItem.getAmount(), selectedItem.getDate());
    }
}
