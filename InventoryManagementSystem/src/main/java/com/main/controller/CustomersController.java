package com.main.controller;

import com.main.model.entity.AddressEntity;
import com.main.model.entity.CustomerEntity;
import com.main.model.jpa.JpaAddressDao;
import com.main.model.jpa.JpaCustomerDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jetbrains.annotations.Nullable;

public class CustomersController {
    @FXML
    public Button buttonRefreshChoices;
    @FXML
    public Button buttonNew;
    @FXML
    public Button buttonRefresh;

    @FXML
    public ChoiceBox<String> choiceBoxAddress;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldPhone;
    @FXML
    public TextField textFieldEmail;
    @FXML
    public TextField textFieldDescription;

    @FXML
    public TableView<CustomerEntity> tableView;
    @FXML
    public TableColumn<CustomerEntity, Integer> tableColumnId;
    @FXML
    public TableColumn<CustomerEntity, Integer> tableColumnAddress;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnName;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnPhone;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnEmail;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnDescription;
    @FXML
    public TableColumn tableColumnActions;

    private JpaCustomerDao customerDao;
    private JpaAddressDao addressDao;

    public CustomersController() {
        customerDao = new JpaCustomerDao();
        addressDao = new JpaAddressDao();
    }

    public void OnPress_Button_RefreshChoices() {
        populateChoiceBox();
    }

    private void populateChoiceBox() {
        ObservableList<String> data = FXCollections.observableArrayList();
        for (AddressEntity entity : addressDao.getAll())
            data.add(entity.getAddress());
        choiceBoxAddress.setItems(data);
    }

    public void OnPress_Button_New() {
        CustomerEntity customerEntity = getCustomerEntity();
        if (customerEntity != null) {
            try {
                customerDao.save(customerEntity);
                MainController.showAlert(Alert.AlertType.CONFIRMATION, "Customer added", "Customer was successfully added to database");
                displayInformationToTableView();
            } catch (Exception ex) {
                MainController.showAlert(Alert.AlertType.ERROR, "Customer Error", ex.getMessage());
            }
        } else {
            MainController.showAlert(Alert.AlertType.CONFIRMATION, "Customer is null", "Customer is null");
        }
    }

    @Nullable
    private CustomerEntity getCustomerEntity() {
        if (isInformationValid())
            return new CustomerEntity(addressDao.getFirst(choiceBoxAddress.getValue()).getIdAddress(), textFieldName.getText(), textFieldPhone.getText(), textFieldEmail.getText(), textFieldDescription.getText());
        return null;
    }

    private boolean isInformationValid() {
        if (textFieldName.getText().length() < 1 || choiceBoxAddress.getSelectionModel().getSelectedIndex() < 0) {
            MainController.showAlert(Alert.AlertType.ERROR, "Customer Error", "Name or address is empty.");
            return false;
        }
        return true;
    }

    public void OnPress_Button_Refresh() {
        displayInformationToTableView();
    }

    private void displayInformationToTableView() {
        ObservableList<CustomerEntity> data = FXCollections.observableArrayList(customerDao.getAll());
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("idAddress"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(data);
    }
}
