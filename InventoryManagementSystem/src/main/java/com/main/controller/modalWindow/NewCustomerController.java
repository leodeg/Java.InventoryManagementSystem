package com.main.controller.modalWindow;

import com.main.controller.menu.AddressController;
import com.main.controller.menu.MainController;
import com.main.database.JpaConnector;
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
import javafx.stage.Stage;
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

    private CustomerEntity customerToChange;

    public NewCustomerController() {

    }

    public NewCustomerController(CustomerEntity customerToChange) {
        this.customerToChange = customerToChange;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNew.setOnAction(this::OnPress_Button_New);
        buttonRefresh.setOnAction(this::OnPress_Button_RefreshTable);
        buttonNewAddress.setOnAction(this::OnPress_Button_NewAddress);
        displayInformationToTableView();

        if (customerToChange != null)
            displayInfoToTextFields();
    }

    private void displayInfoToTextFields() {
        textFieldName.setText(customerToChange.getName());
        textFieldPhone.setText(customerToChange.getPhone());
        textFieldEmail.setText(customerToChange.getEmail());
        textFieldDescription.setText(customerToChange.getDescription());
    }

    @FXML
    private void OnPress_Button_New(ActionEvent event) {
        if (customerToChange == null)
            createNewCustomer();
        else changeOldCustomer();
    }

    private void createNewCustomer() {
        if (isInformationValid()) {
            CustomerEntity customerEntity = getCustomerEntity();
            try {
                JpaConnector.getCustomer().save(customerEntity);
                MainController.showAlert(Alert.AlertType.CONFIRMATION, "Customer added", "Customer was successfully added to database.");
            } catch (Exception ex) {
                MainController.showAlert(Alert.AlertType.ERROR, "Customer Error", ex.getMessage());
            }
        }
    }

    private void changeOldCustomer() {
        if (isInformationValid()) {
            try {
                CustomerEntity changedInformation = getCustomerEntity();
                assignChangedInformation(changedInformation);
                JpaConnector.getCustomer().update(customerToChange);
                MainController.showAlert(Alert.AlertType.CONFIRMATION, "Customer added", "Customer was successfully changed.");
                closeWindow();
            } catch (Exception ex) {
                MainController.showAlert(Alert.AlertType.ERROR, "Customer Error", ex.getMessage());
            }
        }
    }

    private void assignChangedInformation(CustomerEntity changedEntity) {
        customerToChange.setIdAddress(changedEntity.getIdAddress());
        customerToChange.setName(changedEntity.getName());
        customerToChange.setPhone(changedEntity.getPhone());
        customerToChange.setEmail(changedEntity.getEmail());
        customerToChange.setDescription(changedEntity.getDescription());
    }

    private void closeWindow() {
        Stage stage = (Stage) buttonNew.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void OnPress_Button_RefreshTable(ActionEvent event) {
        displayInformationToTableView();
    }

    @FXML
    public void OnPress_Button_NewAddress(ActionEvent event) {
        try {
            openNewAddressWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openNewAddressWindow() throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/menu/address.fxml"));
        fxmlLoader.setController(new AddressController());
        root = fxmlLoader.load();
        MainController.showModalWindow("Addresses", root);
    }

    @Nullable
    private CustomerEntity getCustomerEntity() {
        return new CustomerEntity(getSelectedAddress().getIdAddress(), textFieldName.getText(), textFieldPhone.getText(), textFieldEmail.getText(), textFieldDescription.getText());
    }

    private boolean isInformationValid() {
        if (selectionIsEmpty()) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Customer Error", "Please select address from the table below.");
            return false;
        }
        if (textFieldName.getText().length() < 1) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Customer Error", "Name is empty.");
            return false;
        }
        return true;
    }

    private boolean selectionIsEmpty() {
        return tableView.getSelectionModel().isEmpty();
    }

    private AddressEntity getSelectedAddress() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void displayInformationToTableView() {
        clearTableView();
        ObservableList<AddressEntity> data = FXCollections.observableArrayList(JpaConnector.getAddress().getAll());
        if (data.isEmpty()) {
            MainController.showAlert(Alert.AlertType.WARNING, "Table View", "Table is empty.");
            return;
        }
        assignInformationToTableView(data);
    }

    private void clearTableView() {
        if (tableView.getItems().size() > 0)
            tableView.getItems().clear();
    }

    private void assignInformationToTableView(ObservableList<AddressEntity> data) {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idAddress"));
        tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableColumnAddress2.setCellValueFactory(new PropertyValueFactory<>("address2"));
        tableColumnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tableColumnRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        tableView.setItems(data);
    }

}
