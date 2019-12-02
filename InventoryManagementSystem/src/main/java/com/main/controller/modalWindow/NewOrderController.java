package com.main.controller.modalWindow;

import com.main.controller.menu.MainController;
import com.main.database.JpaConnector;
import com.main.model.entity.CustomerEntity;
import com.main.model.entity.FactEntity;
import com.main.model.entity.OrderEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class NewOrderController implements Initializable {
    @FXML
    public TextField textFieldAmount;
    @FXML
    public DatePicker datePicker;

    @FXML
    public Button buttonRefresh;
    @FXML
    public Button buttonNewOrder;

    @FXML
    public TableView<CustomerEntity> tableCustomerView;
    @FXML
    public TableColumn<CustomerEntity, Integer> tableCustomerColumnId;
    @FXML
    public TableColumn<CustomerEntity, Integer> tableCustomerColumnAddress;
    @FXML
    public TableColumn<CustomerEntity, String> tableCustomerColumnName;
    @FXML
    public TableColumn<CustomerEntity, String> tableCustomerColumnPhone;
    @FXML
    public TableColumn<CustomerEntity, String> tableCustomerColumnEmail;
    @FXML
    public TableColumn<CustomerEntity, String> tableCustomerColumnDescription;

    @FXML
    public TableView<FactEntity> tableFactView;
    @FXML
    public TableColumn<FactEntity, Integer> tableFactColumnId;
    @FXML
    public TableColumn<FactEntity, Integer> tableFactColumnIdProduct;
    @FXML
    public TableColumn<FactEntity, Integer> tableFactColumnAmount;
    @FXML
    public TableColumn<FactEntity, Double> tableFactColumnPrice;
    @FXML
    public TableColumn tableFactColumnTotalPrice;
    @FXML
    public TableColumn<FactEntity, Date> tableFactColumnDate;

    private OrderEntity entityToChange;

    public NewOrderController() {

    }

    public NewOrderController(OrderEntity entityToChange) {
        this.entityToChange = entityToChange;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonRefresh.setOnAction(this::OnPress_Button_RefreshTables);
        buttonNewOrder.setOnAction(this::OnPress_Button_NewOrder);
        displayCustomerInformationToTableView();
        displayFactInformationToFactTableView();

        if (entityToChange != null)
            displayOldEntityInfo();
    }

    private void displayOldEntityInfo() {
        textFieldAmount.setText(String.valueOf(entityToChange.getAmount()));
    }

    @FXML
    private void OnPress_Button_NewOrder(ActionEvent event) {
        if (entityToChange == null)
            createNewOrder();
        else changeOldOrder();
    }

    private void createNewOrder() {
        if (checkValidation()) {
            OrderEntity orderEntity = getOrderEntity();
            if (orderEntity != null) try {
                JpaConnector.getOrder().save(getOrderEntity());
                MainController.showAlert(Alert.AlertType.CONFIRMATION, "New Order", "Order was successfully added.");
                closeCurrentWindow();
            } catch (Exception ex) {
                MainController.showAlert(Alert.AlertType.ERROR, "New Order", ex.getMessage());
            }
        }
    }

    private void changeOldOrder() {
        if (checkValidation()) {
            try {
                OrderEntity changedEntity = getOrderEntity();
                assignChangedInformation(changedEntity);
                JpaConnector.getOrder().update(entityToChange);
                MainController.showAlert(Alert.AlertType.CONFIRMATION, "Change Order", "Order was successfully changed.");
                closeCurrentWindow();
            } catch (Exception ex) {
                MainController.showAlert(Alert.AlertType.ERROR, "Change Order", ex.getMessage());
            }
        }
    }

    private boolean checkValidation() {
        if (customerSelectedItemsIsEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Please select a customer from the table below.");
            return false;
        }
        if (factSelectedItemsIsEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Please select a product from inventory from the table below.");
            return false;
        }
        if (!MainController.hasOnlyNumbers(textFieldAmount.getText())) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Amount must has only numbers.");
            return false;
        }
        if (Integer.parseInt(textFieldAmount.getText()) > getFactSelectedEntity().getAmount()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Inventory has only: " + getFactSelectedEntity().getAmount());
            return false;
        }
        if (datePicker.getValue() == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Choose data of the order.");
            return false;
        }

        return true;
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) buttonNewOrder.getScene().getWindow();
        stage.close();
    }

    private OrderEntity getOrderEntity() {
        CustomerEntity customerEntity = getCustomerSelectedItem();
        return new OrderEntity(getFactSelectedEntity().getIdProduct(),
                customerEntity.getIdCustomer(),
                getFactSelectedEntity().getPrice(),
                Integer.parseInt(textFieldAmount.getText()),
                Date.valueOf(datePicker.getValue()));
    }

    private void assignChangedInformation(OrderEntity changedEntity) {
        entityToChange.setIdCustomer(changedEntity.getIdCustomer());
        entityToChange.setIdProduct(changedEntity.getIdProduct());
        entityToChange.setPrice(changedEntity.getPrice());
        entityToChange.setAmount(changedEntity.getAmount());
        entityToChange.setTotalPrice(entityToChange.getPrice() * entityToChange.getAmount());
    }

    private CustomerEntity getCustomerSelectedItem() {
        return tableCustomerView.getSelectionModel().getSelectedItem();
    }

    private boolean customerSelectedItemsIsEmpty() {
        return tableCustomerView.getSelectionModel().isEmpty();
    }

    private FactEntity getFactSelectedEntity() {
        return tableFactView.getSelectionModel().getSelectedItem();
    }

    private boolean factSelectedItemsIsEmpty() {
        return tableFactView.getSelectionModel().isEmpty();
    }

    @FXML
    public void OnPress_Button_RefreshTables(ActionEvent event) {
        displayCustomerInformationToTableView();
        displayFactInformationToFactTableView();
    }

    private void displayCustomerInformationToTableView() {
        clearCustomerTableView();
        ObservableList<CustomerEntity> data = FXCollections.observableArrayList(JpaConnector.getCustomer().getAll());
        if (data.size() < 1) {
            MainController.showAlert(Alert.AlertType.INFORMATION, "Table View", "Table is empty.");
            return;
        }
        assignInformationToCustomerTableView(data);
    }

    private void clearCustomerTableView() {
        if (tableCustomerView.getItems().size() > 0)
            tableCustomerView.getItems().clear();
    }

    private void assignInformationToCustomerTableView(ObservableList<CustomerEntity> data) {
        tableCustomerColumnId.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        tableCustomerColumnAddress.setCellValueFactory(new PropertyValueFactory<>("idAddress"));
        tableCustomerColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCustomerColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableCustomerColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableCustomerColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableCustomerView.setItems(data);
    }

    private void displayFactInformationToFactTableView() {
        clearFactTableView();
        ObservableList<FactEntity> list = FXCollections.observableArrayList(JpaConnector.getFact().getAll());
        if (list.size() < 1) {
            MainController.showAlert(Alert.AlertType.INFORMATION, "Table View", "Table is empty.");
            return;
        }
        assignInformationToFactTableView(list);
    }

    private void clearFactTableView() {
        if (tableFactView.getItems().size() > 0)
            tableFactView.getItems().clear();
    }

    private void assignInformationToFactTableView(ObservableList<FactEntity> list) {
        tableFactColumnId.setCellValueFactory(new PropertyValueFactory<>("idFact"));
        tableFactColumnIdProduct.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        tableFactColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableFactColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableFactColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tableFactColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableFactView.setItems(list);
    }
}
