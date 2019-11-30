package com.main.controller;

import com.main.database.jpa.JpaConnector;
import com.main.model.entity.AddressEntity;
import com.main.model.entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewCustomerController implements Initializable {
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldPhone;
    @FXML
    public TextField textFieldEmail;
    @FXML
    public TextField textFieldDescription;

    @FXML
    public Button buttonNew;
    @FXML
    public Button buttonRefresh;
    @FXML
    public Button buttonNewAddress;

    @FXML
    public TableView<AddressEntity> tableView;
    @FXML
    public TableColumn<AddressEntity, Integer> tableColumnId;
    @FXML
    public TableColumn<AddressEntity, String> tableColumnAddress;
    @FXML
    public TableColumn<AddressEntity, String> tableColumnAddress2;
    @FXML
    public TableColumn<AddressEntity, String> tableColumnCity;
    @FXML
    public TableColumn<AddressEntity, String> tableColumnRegion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNew.setOnAction(this::OnPress_Button_New);
        buttonRefresh.setOnAction(this::OnPress_Button_RefreshTable);
        buttonNewAddress.setOnAction(this::OnPress_Button_NewAddress);
        displayInformationToTableView();
    }

    @FXML
    private void OnPress_Button_New(ActionEvent event) {
        CustomerEntity customerEntity = getCustomerEntity();
        if (customerEntity != null) try {
            JpaConnector.getCustomer().save(customerEntity);
            MainController.showAlert(Alert.AlertType.CONFIRMATION, "Customer added", "Customer was successfully added to database");
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "Customer Error", ex.getMessage());
        }
    }

    @FXML
    private void OnPress_Button_RefreshTable(ActionEvent event) {
        displayInformationToTableView();
    }

    @FXML
    public void OnPress_Button_NewAddress(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/address.fxml"));
            fxmlLoader.setController(new AddressController());
            root = fxmlLoader.load();
            MainController.showModalWindow("Addresses", root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    private CustomerEntity getCustomerEntity() {
        if (isInformationValid())
            return new CustomerEntity(getSelectedAddress().getIdAddress(), textFieldName.getText(), textFieldPhone.getText(), textFieldEmail.getText(), textFieldDescription.getText());
        return null;
    }

    private boolean isInformationValid() {
        if (tableView.getSelectionModel().isEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Customer Error", "Please select address from the table below.");
            return false;
        }
        if (textFieldName.getText().length() < 1) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Customer Error", "Name is empty.");
            return false;
        }
        return true;
    }

    private AddressEntity getSelectedAddress() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void displayInformationToTableView() {
        if (tableView.getItems().size() > 0)
            tableView.getItems().clear();

        ObservableList<AddressEntity> data = FXCollections.observableArrayList(JpaConnector.getAddress().getAll());
        if (data.size() > 0) {
            tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idAddress"));
            tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            tableColumnAddress2.setCellValueFactory(new PropertyValueFactory<>("address2"));
            tableColumnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            tableColumnRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
            tableView.setItems(data);
        }
    }

}
