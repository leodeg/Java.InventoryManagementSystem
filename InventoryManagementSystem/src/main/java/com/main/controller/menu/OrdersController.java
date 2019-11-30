package com.main.controller.menu;

import com.main.controller.modalWindow.NewOrderController;
import com.main.database.JpaConnector;
import com.main.model.entity.OrderEntity;
import com.main.model.entity.SaleEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {
    @FXML
    public Button buttonNewOrder;
    @FXML
    public Button buttonRefreshTable;
    @FXML
    public Button buttonChangeOrder;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNewOrder.setOnAction(this::OnPress_Button_NewOrder);
        buttonRefreshTable.setOnAction(this::OnPress_Button_RefreshTable);
        buttonToSales.setOnAction(this::OnPress_Button_ToSales);
        buttonChangeOrder.setOnAction(this::OnPress_Button_ChangeOrder);
    }

    @FXML
    private void OnPress_Button_NewOrder(ActionEvent event) {
        createOrderModalWindow("New Order", new NewOrderController());
    }

    @FXML
    private void OnPress_Button_ChangeOrder(ActionEvent event) {
        if (!informationIsValid()) return;

        createOrderModalWindow("Change Order", new NewOrderController(getSelectedOrderEntity()));
    }

    private void createOrderModalWindow(String title, NewOrderController controller) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/modalWindow/new_order.fxml"));
            fxmlLoader.setController(controller);
            root = fxmlLoader.load();
            MainController.showModalWindow(title, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean informationIsValid() {
        if (selectedItemsIsEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "To Sale", "Please choose order from table below.");
            return false;
        }
        return true;
    }

    private OrderEntity getSelectedOrderEntity() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private boolean selectedItemsIsEmpty() {
        return tableView.getSelectionModel().isEmpty();
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
            JpaConnector.getSale().save(saleEntity);
            OrderEntity orderEntity = JpaConnector.getOrder().get(selectedItem.getIdOrder()).get();
            JpaConnector.getOrder().delete(orderEntity);
            MainController.showAlert(Alert.AlertType.CONFIRMATION, "To Sale", "Complete");
            displayInformationToTableView();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "To Sale", ex.getMessage());
        }
    }

    private void displayInformationToTableView() {
        if (tableView.getItems().size() > 0)
            tableView.getItems().clear();

        ObservableList<OrderEntity> data = FXCollections.observableArrayList(JpaConnector.getOrder().getAll());
        if (data.size() < 1) {
            MainController.showAlert(Alert.AlertType.INFORMATION, "Table View", "Table is empty.");
            return;
        }

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        tableColumnProductId.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        tableColumnCustomerId.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableView.setItems(data);
    }

    private SaleEntity getSaleEntity(OrderEntity selectedItem) {
        String productName = JpaConnector.getProduct().get(selectedItem.getIdProduct()).get().getName();
        String customerName = JpaConnector.getCustomer().get(selectedItem.getIdCustomer()).get().getName();
        return new SaleEntity(productName, customerName, selectedItem.getPrice(), selectedItem.getAmount(), selectedItem.getDate());
    }
}
