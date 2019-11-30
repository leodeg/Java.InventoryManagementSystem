package com.main.controller.menu;

import com.main.controller.modalWindow.NewCustomerController;
import com.main.database.JpaConnector;
import com.main.model.entity.AddressEntity;
import com.main.model.entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class CustomersController {
    @FXML
    public Button buttonRefreshChoices;
    @FXML
    public Button buttonNewCustomer;
    @FXML
    public Button buttonRefresh;
    @FXML
    public Button buttonNewAddress;

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


    public void OnPress_Button_RefreshChoices(ActionEvent event) {
        populateChoiceBox();
    }

    private void populateChoiceBox() {
        ObservableList<String> data = FXCollections.observableArrayList();
        for (AddressEntity entity : JpaConnector.getAddress().getAll())
            data.add(entity.getAddress());
        choiceBoxAddress.setItems(data);
    }

    public void OnPress_Button_NewCustomer(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/modalWindow/new_customer.fxml"));
            fxmlLoader.setController(new NewCustomerController());
            root = fxmlLoader.load();
            MainController.showModalWindow("New Customer", root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnPress_Button_Refresh(ActionEvent event) {
        displayInformationToTableView();
    }

    public void OnPress_Button_NewAddress(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/menu/address.fxml"));
            fxmlLoader.setController(new AddressController());
            root = fxmlLoader.load();
            MainController.showModalWindow("Addresses", root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayInformationToTableView() {
        if (tableView.getItems().size() > 0)
            tableView.getItems().clear();

        ObservableList<CustomerEntity> data = FXCollections.observableArrayList(JpaConnector.getCustomer().getAll());
        if (data.size() < 1) {
            MainController.showAlert(Alert.AlertType.INFORMATION, "Table View", "Table is empty.");
            return;
        }

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("idAddress"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(data);
    }
}
